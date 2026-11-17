package com.smallmanseries.farlandstraveler.common.worldgen.features.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

/**
 * 「旋涡」放置修饰器
 * <p>
 * 把一个候选位置按<b>世界坐标</b>上的旋涡场筛选：只有落在螺旋臂附近的候选才会被保留，
 * 并由这个修饰器决定它的 Y 高度。因为整个图案是 (x, z) 的纯函数，所以螺旋臂能跨区块
 * 连续地接起来，形成大尺度的漩涡，而不是每个区块各随机一堆点。
 * <p>
 * 图案的构成：
 * <ul>
 *   <li>世界按 {@code cell_size} 划分成格，每格中心放一个旋涡中心（位置与缠绕紧度都带确定性抖动）；</li>
 *   <li>每个旋涡由若干阿基米德螺旋臂 {@code r = twist * (θ + 2πm / arms)} 组成，
 *       相邻格的旋向按哈希交错，模拟梵高《星空》里互相咬合的正反漩涡；</li>
 *   <li>荧石不是压在臂的脊线上，而是按<b>高斯分布</b>散在臂的周围 ——
 *       离脊线越近越密、远了仍有概率，于是是一条「星云」而不是一条线；</li>
 *   <li>Y 高度沿角度做正弦起伏，再加上随机上下散布，整体是一团有厚度的星带；</li>
 *   <li>另有一层低频 value noise 调制整体密度，让天空有疏有密，不至于处处一样；</li>
 *   <li>旋涡中心另有一小团「星核」。</li>
 * </ul>
 *
 * @param cellSize      旋涡中心的网格间距（格）。必须小于 2 × maxRadius，相邻旋涡才会咬合
 * @param arms          螺旋臂数量
 * @param twist         每弧度的半径增量，越小缠绕越紧、圈数越多
 * @param twistJitter   每个旋涡缠绕紧度的相对抖动（0.25 表示 ±25%），用来破除格子感
 * @param maxRadius     旋涡的最大半径
 * @param minRadius     螺旋臂开始出现的半径（以内由星核接管）
 * @param armFalloff    星带的高斯宽度：离臂脊线距离为它时密度降到 37%
 * @param fadeStart     半径渐隐的起点（占 maxRadius 的比例），越小旋涡越孤立
 * @param starRadius    星核半径
 * @param minHeight     星带高度中线的下界（垂直锚点，建议用世界顶的相对量，这样改世界高度的数据包也能贴顶）
 * @param maxHeight     星带高度中线的上界
 * @param waveFrequency 每弧度的高度起伏次数
 * @param ySpread       垂直随机散布的半幅（三角形分布，正中密、两端稀）
 * @param densityScale  疏密调制的噪声波长（格），越大疏密斑块越大
 * @param densityMin    噪声低谷处的密度下限（占满密度的比例），0 表示可以完全空出来
 * @param seed          随机种子，改变它会得到完全不同的旋涡布局
 */
public class SwirlPlacement extends PlacementModifier {
    public static final MapCodec<SwirlPlacement> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    Codec.intRange(16, 1024).fieldOf("cell_size").forGetter(SwirlPlacement::cellSize),
                    Codec.intRange(1, 16).fieldOf("arms").forGetter(SwirlPlacement::arms),
                    Codec.DOUBLE.fieldOf("twist").forGetter(SwirlPlacement::twist),
                    Codec.DOUBLE.fieldOf("twist_jitter").forGetter(SwirlPlacement::twistJitter),
                    Codec.DOUBLE.fieldOf("max_radius").forGetter(SwirlPlacement::maxRadius),
                    Codec.DOUBLE.fieldOf("min_radius").forGetter(SwirlPlacement::minRadius),
                    Codec.DOUBLE.fieldOf("arm_falloff").forGetter(SwirlPlacement::armFalloff),
                    Codec.DOUBLE.fieldOf("fade_start").forGetter(SwirlPlacement::fadeStart),
                    Codec.DOUBLE.fieldOf("star_radius").forGetter(SwirlPlacement::starRadius),
                    VerticalAnchor.CODEC.fieldOf("min_height").forGetter(SwirlPlacement::minHeight),
                    VerticalAnchor.CODEC.fieldOf("max_height").forGetter(SwirlPlacement::maxHeight),
                    Codec.DOUBLE.fieldOf("wave_frequency").forGetter(SwirlPlacement::waveFrequency),
                    Codec.intRange(0, 64).fieldOf("y_spread").forGetter(SwirlPlacement::ySpread),
                    Codec.DOUBLE.fieldOf("density_scale").forGetter(SwirlPlacement::densityScale),
                    Codec.DOUBLE.fieldOf("density_min").forGetter(SwirlPlacement::densityMin),
                    Codec.LONG.fieldOf("seed").forGetter(SwirlPlacement::seed)
            ).apply(inst, SwirlPlacement::new)
    );

    // 表示「这个候选不在任何旋涡臂上」
    private static final int MISS = Integer.MIN_VALUE;
    private static final double TWO_PI = Math.PI * 2.0;
    // 离脊线超过这个倍数的高斯宽度就当作完全落空，省掉无谓的计算
    private static final double CUTOFF = 3.0;

    private final int cellSize;
    private final int arms;
    private final double twist;
    private final double twistJitter;
    private final double maxRadius;
    private final double minRadius;
    private final double armFalloff;
    private final double fadeStart;
    private final double starRadius;
    private final VerticalAnchor minHeight;
    private final VerticalAnchor maxHeight;
    private final double waveFrequency;
    private final int ySpread;
    private final double densityScale;
    private final double densityMin;
    private final long seed;

    public SwirlPlacement(int cellSize, int arms, double twist, double twistJitter, double maxRadius, double minRadius,
                          double armFalloff, double fadeStart, double starRadius, VerticalAnchor minHeight,
                          VerticalAnchor maxHeight, double waveFrequency, int ySpread, double densityScale,
                          double densityMin, long seed) {
        this.cellSize = cellSize;
        this.arms = arms;
        this.twist = Math.max(twist, 1.0);
        this.twistJitter = Math.max(twistJitter, 0.0);
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        this.armFalloff = Math.max(armFalloff, 0.1);
        this.fadeStart = Math.clamp(fadeStart, 0.0, 1.0);
        this.starRadius = starRadius;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.waveFrequency = waveFrequency;
        this.ySpread = ySpread;
        this.densityScale = Math.max(densityScale, 1.0);
        this.densityMin = Math.clamp(densityMin, 0.0, 1.0);
        this.seed = seed;
    }

    public int cellSize() {
        return this.cellSize;
    }

    public int arms() {
        return this.arms;
    }

    public double twist() {
        return this.twist;
    }

    public double twistJitter() {
        return this.twistJitter;
    }

    public double maxRadius() {
        return this.maxRadius;
    }

    public double minRadius() {
        return this.minRadius;
    }

    public double armFalloff() {
        return this.armFalloff;
    }

    public double fadeStart() {
        return this.fadeStart;
    }

    public double starRadius() {
        return this.starRadius;
    }

    public VerticalAnchor minHeight() {
        return this.minHeight;
    }

    public VerticalAnchor maxHeight() {
        return this.maxHeight;
    }

    public double waveFrequency() {
        return this.waveFrequency;
    }

    public int ySpread() {
        return this.ySpread;
    }

    public double densityScale() {
        return this.densityScale;
    }

    public double densityMin() {
        return this.densityMin;
    }

    public long seed() {
        return this.seed;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        int x = origin.getX();
        int z = origin.getZ();
        int cellX = Math.floorDiv(x, this.cellSize);
        int cellZ = Math.floorDiv(z, this.cellSize);

        // 相邻格子的旋涡可能覆盖到本点，所以检查 3x3 格，命中即止
        for (int dz = -1; dz <= 1; dz++) {
            for (int dx = -1; dx <= 1; dx++) {
                int y = this.sample(context, cellX + dx, cellZ + dz, x, z, random);
                if (y != MISS) {
                    return Stream.of(new BlockPos(x, y, z));
                }
            }
        }

        return Stream.empty();
    }

    /**
     * 判断某个位置是否落在旋涡的星带里；是则返回它的高度，否则返回 {@link #MISS}
     */
    private int sample(PlacementContext context, int cellX, int cellZ, int x, int z, RandomSource random) {
        long hash = this.hash(cellX, cellZ);

        // 格心 + 抖动，避免严格的网格感
        double centerX = cellX * (double) this.cellSize + this.cellSize * 0.5 + this.jitter(hash, 0) * this.cellSize * 0.6;
        double centerZ = cellZ * (double) this.cellSize + this.cellSize * 0.5 + this.jitter(hash, 1) * this.cellSize * 0.6;

        double dx = x + 0.5 - centerX;
        double dz = z + 0.5 - centerZ;
        double radius = Math.sqrt(dx * dx + dz * dz);

        if (radius > this.maxRadius) {
            return MISS;
        }

        double theta = Math.atan2(dz, dx);
        // 相邻的旋涡旋向相反，形成互相咬合的观感
        if ((hash & 1L) == 0L) {
            theta = -theta;
        }

        // 星核：中心一小团，越靠外越稀
        if (radius < this.starRadius) {
            double chance = 0.55 * (1.0 - radius / this.starRadius);
            return random.nextFloat() < chance ? this.heightAt(context, theta, radius, hash, random) : MISS;
        }

        if (radius < this.minRadius) {
            return MISS;
        }

        // 每个旋涡的缠绕紧度略有不同，破除处处一致的规整感
        double cellTwist = this.twist * (1.0 + this.twistJitter * this.jitter(hash, 2) * 2.0);

        // 到最近螺旋臂脊线的径向距离
        double period = TWO_PI / this.arms;
        double turns = radius / cellTwist - theta;
        double offset = turns % period;
        if (offset < 0.0) {
            offset += period;
        }
        offset -= period * 0.5;
        double armDistance = Math.abs(offset) * cellTwist;

        double cutoff = this.armFalloff * CUTOFF;
        if (armDistance > cutoff) {
            return MISS;
        }

        // 高斯散布：脊线上最密，向两侧平滑变稀，于是形成星云状的带而不是一条线
        double concentration = Math.exp(-(armDistance * armDistance) / (this.armFalloff * this.armFalloff));

        // 半径外侧渐隐，避免每个旋涡出现硬边圆盘
        double fadeFrom = this.maxRadius * this.fadeStart;
        if (radius > fadeFrom) {
            double t = (radius - fadeFrom) / (this.maxRadius - fadeFrom);
            concentration *= 1.0 - t * t * (3.0 - 2.0 * t);
        }

        // 低频噪声调制疏密，让天空有疏有密
        concentration *= this.densityAt(x, z);

        return random.nextFloat() < concentration ? this.heightAt(context, theta, radius, hash, random) : MISS;
    }

    /**
     * 沿角度做正弦起伏，再叠加随机的上下散布，得到一团有厚度的星带
     * <p>
     * 高度用 {@link VerticalAnchor} 解析，所以「固定在世界的顶部」这类需求
     * 在修改过世界高度的数据包里依然成立。
     */
    private int heightAt(PlacementContext context, double theta, double radius, long hash, RandomSource random) {
        int lowestY = context.getMinGenY() + 1;
        int highestY = context.getMinGenY() + context.getGenDepth() - 2;

        int lower = this.minHeight.resolveY(context);
        int upper = this.maxHeight.resolveY(context);
        if (lower > upper) {
            int swap = lower;
            lower = upper;
            upper = swap;
        }

        double phase = ((hash >>> 21) & 0xFFFFL) / 65536.0 * TWO_PI;
        double middle = (lower + upper) * 0.5;
        double amplitude = (upper - lower) * 0.5;
        double base = middle + amplitude * Math.sin(theta * this.waveFrequency + phase + radius * 0.03);

        // 三角形分布：正中密、两端稀
        int offset = Math.round((random.nextFloat() - random.nextFloat()) * this.ySpread);
        double y = base + offset;

        return (int) Math.round(Math.min(Math.max(y, lowestY), highestY));
    }

    /**
     * 低频 value noise 给出的密度系数，落在 [densityMin, 1]
     */
    private double densityAt(int x, int z) {
        double noise = 0.62 * this.valueNoise(x, z, this.densityScale, 11L)
                + 0.38 * this.valueNoise(x, z, this.densityScale * 0.45, 23L);
        return this.densityMin + (1.0 - this.densityMin) * noise;
    }

    /**
     * 双线性插值的 value noise，格点上取哈希值，插值用 smoothstep
     */
    private double valueNoise(double x, double z, double scale, long salt) {
        double fx = x / scale;
        double fz = z / scale;
        int x0 = (int) Math.floor(fx);
        int z0 = (int) Math.floor(fz);

        double tx = fx - x0;
        double tz = fz - z0;
        tx = tx * tx * (3.0 - 2.0 * tx);
        tz = tz * tz * (3.0 - 2.0 * tz);

        double v00 = this.lattice01(x0, z0, salt);
        double v10 = this.lattice01(x0 + 1, z0, salt);
        double v01 = this.lattice01(x0, z0 + 1, salt);
        double v11 = this.lattice01(x0 + 1, z0 + 1, salt);

        double lower = v00 + (v10 - v00) * tx;
        double upper = v01 + (v11 - v01) * tx;
        return lower + (upper - lower) * tz;
    }

    private double lattice01(int latticeX, int latticeZ, long salt) {
        long v = this.hash(latticeX, latticeZ) ^ (salt * 0x9E3779B97F4A7C15L);
        v ^= v >>> 29;
        v *= 0xff51afd7ed558ccdL;
        v ^= v >>> 32;
        return (v >>> 11) / (double) (1L << 53);
    }

    private long hash(int cellX, int cellZ) {
        long h = this.seed;
        h = h * 6364136223846793005L + cellX * 0x9E3779B97F4A7C15L;
        h = h * 6364136223846793005L + cellZ * 0xC2B2AE3D27D4EB4FL;
        h ^= h >>> 33;
        h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33;
        h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return h;
    }

    // 由哈希取出 [ -0.5, 0.5 ) 的确定性抖动
    private double jitter(long hash, int index) {
        int shift = 8 + index * 16;
        return ((hash >>> shift) & 0xFFFFL) / 65535.0 - 0.5;
    }

    @Override
    public PlacementModifierType<?> type() {
        return FLTPlacementModifiers.SWIRL.get();
    }
}
