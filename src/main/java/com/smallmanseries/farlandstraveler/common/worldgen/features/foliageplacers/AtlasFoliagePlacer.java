package com.smallmanseries.farlandstraveler.common.worldgen.features.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

/**
 * “擎天树”的树冠放置器：下面是参考巨型云杉的锥形段，上面是“伞”状结构。
 * <p>
 * 参考点（{@link FoliageAttachment#pos()}）是<b>树冠顶</b>，由 {@code atlas_trunk_placer} 给出，
 * 所以整个树冠的绝对高度是固定的，与长在什么地方无关。
 * <p>
 * 锥形段（{@code cone_height} 层，从上往下数）：
 * <ul>
 *     <li>包络半径按<b>二次函数</b>从顶部的 {@code cone_top_radius} 过渡到底部的 {@code cone_bottom_radius}；</li>
 *     <li>再按 {@code tier_height} 分层做台阶：每一组里最底下那层取包络值，往上每层收窄一格，
 *     形成类似松树的层层叠叠的锯齿冠层；</li>
 *     <li>另有 1/5 的概率（由{@code JITTER_ONE_IN}控制）某一层更宽一格，让轮廓不那么规整。</li>
 * </ul>
 * 伞（{@code umbrella_height} 层）：
 * <ul>
 *     <li>最上面 {@code umbrella_height - 2} 层是实心的浅锥顶盖，半径按 {@code √i} 展开；
 *     即“下面的结构稍微变宽一点，然后伸展出去”的过渡；</li>
 *     <li>再往下是向下延伸的伞骨圆环，最后一行只留最外一圈，并按 {@code fringe_chance} 长出随机毛边。</li>
 * </ul>
 * {@code cone_height = 0} 时只剩伞，用于“光杆”变种。
 */
public class AtlasFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<AtlasFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    Codec.intRange(0, 80).fieldOf("cone_height").forGetter(placer -> placer.coneHeight),
                    Codec.intRange(0, 16).fieldOf("cone_bottom_radius").forGetter(placer -> placer.coneBottomRadius),
                    Codec.intRange(0, 16).fieldOf("cone_top_radius").forGetter(placer -> placer.coneTopRadius),
                    Codec.intRange(1, 8).fieldOf("tier_height").forGetter(placer -> placer.tierHeight),
                    Codec.intRange(1, 16).fieldOf("umbrella_height").forGetter(placer -> placer.umbrellaHeight),
                    Codec.intRange(0, 16).fieldOf("umbrella_radius").forGetter(placer -> placer.umbrellaRadius),
                    Codec.intRange(0, 16).fieldOf("umbrella_top_radius").forGetter(placer -> placer.umbrellaTopRadius),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("fringe_chance").forGetter(placer -> placer.fringeChance)
            ).apply(inst, AtlasFoliagePlacer::new)
    );

    // 锥形段里每隔多少层出现一次随机凸起
    private static final int JITTER_ONE_IN = 5;

    // 伞骨圆环的厚度上限
    private static final int RING_THICKNESS = 2;

    private final int coneHeight;
    private final int coneBottomRadius;
    private final int coneTopRadius;
    private final int tierHeight;
    private final int umbrellaHeight;
    private final int umbrellaRadius;
    private final int umbrellaTopRadius;
    private final float fringeChance;

    /**
     * 创建擎天树树叶放置器
     *
     * @param coneHeight        锥形段层数
     * @param coneBottomRadius  锥形段最宽半径
     * @param coneTopRadius     锥形段最窄半径
     * @param tierHeight        锥形段锯齿台阶的层高
     * @param umbrellaHeight    伞的层数
     * @param umbrellaRadius    伞最宽半径
     * @param umbrellaTopRadius 伞顶中心半径
     * @param fringeChance      伞边垂藤（毛边）的概率
     */
    public AtlasFoliagePlacer(int coneHeight, int coneBottomRadius, int coneTopRadius, int tierHeight,
                              int umbrellaHeight, int umbrellaRadius, int umbrellaTopRadius, float fringeChance) {
        // 半径与偏移量在本放置器里由伞与锥形段自己决定，不用 FoliagePlacer 的那两个参数
        super(ConstantInt.of(0), ConstantInt.of(0));
        this.coneHeight = coneHeight;
        this.coneBottomRadius = coneBottomRadius;
        this.coneTopRadius = coneTopRadius;
        this.tierHeight = tierHeight;
        this.umbrellaHeight = umbrellaHeight;
        this.umbrellaRadius = umbrellaRadius;
        this.umbrellaTopRadius = umbrellaTopRadius;
        this.fringeChance = fringeChance;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return FLTFoliagePlacers.ATLAS_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(WorldGenLevel level, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random,
                                 TreeConfiguration config, int treeHeight, FoliagePlacer.FoliageAttachment attachment,
                                 int foliageHeight, int leafRadius, int offset) {
        BlockPos canopyTop = attachment.pos();

        // 锥形段：从伞的下方开始，一层一层往下铺
        for (int layer = 0; layer < this.coneHeight; layer++) {
            int y = canopyTop.getY() - this.umbrellaHeight - layer;
            this.placeLayer(level, foliageSetter, random, config, canopyTop, y, this.coneRadius(random, layer), 0);
        }

        // 伞的结构
        int capLayers = Math.max(1, this.umbrellaHeight - 2);
        for (int i = 0; i < this.umbrellaHeight; i++) {
            int y = canopyTop.getY() - i;
            int radius = this.umbrellaRadiusAt(i);
            if (i < capLayers) {
                // 顶盖：实心浅锥
                this.placeLayer(level, foliageSetter, random, config, canopyTop, y, radius, 0);
            } else {
                // 伞骨：只留外圈，中间由伞柄连着
                boolean outermost = i == this.umbrellaHeight - 1;
                int inner = outermost ? radius : Math.max(0, radius - RING_THICKNESS);
                this.placeLayer(level, foliageSetter, random, config, canopyTop, y, radius, inner);
                this.placeLayer(level, foliageSetter, random, config, canopyTop, y,
                        Math.max(1, this.umbrellaTopRadius - (i - capLayers + 1)), 0);
                if (outermost) {
                    this.placeFringe(level, foliageSetter, random, config, canopyTop, y, radius);
                }
            }
        }
    }

    /**
     * 锥形段某一层的半径
     *
     * @param random 随机源
     * @param layer  相对锥形段顶部的层号，0 为最上面一层
     * @return 该层半径
     */
    private int coneRadius(RandomSource random, int layer) {
        if (this.coneHeight <= 0) {
            return 0;
        }
        double t = (double) layer / Math.max(1, this.coneHeight - 1);
        double smooth = this.coneTopRadius + (this.coneBottomRadius - this.coneTopRadius) * t * t;

        // 分层台阶：每组最底下的一层取包络值，往上每层收窄一格
        int tier = (this.coneHeight - 1 - layer) % this.tierHeight;
        int radius = (int) Math.round(smooth) - tier;
        if (random.nextInt(JITTER_ONE_IN) == 0) {
            radius++;
        }
        return Math.max(0, radius);
    }

    /**
     * 伞第 i 层的半径：从顶部的 {@code umbrella_top_radius} 按 √ 曲线展开到 {@code umbrella_radius}
     *
     * @param i 层号，0 为伞的最上面一层
     * @return 该层半径
     */
    private int umbrellaRadiusAt(int i) {
        if (this.umbrellaHeight <= 1) {
            return this.umbrellaRadius;
        }
        double t = Math.min(1.0, (double) i / Math.max(1, this.umbrellaHeight - 2));
        double radius = this.umbrellaTopRadius + (this.umbrellaRadius - this.umbrellaTopRadius) * Math.sqrt(t);
        return (int) Math.round(radius);
    }

    /**
     * 在某一层铺一圈树叶
     *
     * @param level         世界
     * @param foliageSetter 树叶写入器
     * @param random        随机源
     * @param config        树的配置
     * @param center        树冠顶的水平坐标（只取 x/z）
     * @param y             该层的 y高度
     * @param outerRadius   外半径
     * @param innerRadius   内半径，0 表示实心圆盘
     */
    private void placeLayer(WorldGenLevel level, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random,
                            TreeConfiguration config, BlockPos center, int y, int outerRadius, int innerRadius) {
        if (outerRadius <= 0) {
            if (innerRadius <= 0) {
                tryPlaceLeaf(level, foliageSetter, random, config, new BlockPos(center.getX(), y, center.getZ()));
            }
            return;
        }

        int innerSquare = innerRadius * innerRadius;
        int outerSquare = outerRadius * outerRadius;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int dx = -outerRadius; dx <= outerRadius; dx++) {
            for (int dz = -outerRadius; dz <= outerRadius; dz++) {
                int distanceSquare = dx * dx + dz * dz;
                if (distanceSquare > outerSquare || distanceSquare < innerSquare) {
                    continue;
                }
                pos.set(center.getX() + dx, y, center.getZ() + dz);
                tryPlaceLeaf(level, foliageSetter, random, config, pos);
            }
        }
    }

    /**
     * 伞边缘的毛边：沿最外一圈按概率挂下 1~2 格树叶
     *
     * @param level         世界
     * @param foliageSetter 树叶写入器
     * @param random        随机源
     * @param config        树的配置
     * @param center        树冠顶水平坐标（只取 x/z）
     * @param y             该层的 y
     * @param radius        该层半径
     */
    private void placeFringe(WorldGenLevel level, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random,
                             TreeConfiguration config, BlockPos center, int y, int radius) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int distanceSquare = dx * dx + dz * dz;
                if (distanceSquare > radius * radius || distanceSquare <= (radius - 1) * (radius - 1)) {
                    continue;
                }
                if (random.nextFloat() >= this.fringeChance) {
                    continue;
                }
                pos.set(center.getX() + dx, y, center.getZ() + dz);
                tryPlaceLeaf(level, foliageSetter, random, config, pos);
                if (random.nextBoolean()) {
                    pos.set(center.getX() + dx, y - 1, center.getZ() + dz);
                    tryPlaceLeaf(level, foliageSetter, random, config, pos);
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int treeHeight, TreeConfiguration config) {
        return this.coneHeight + this.umbrellaHeight;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int currentRadius, boolean doubleTrunk) {
        return dx * dx + dz * dz > currentRadius * currentRadius;
    }
}
