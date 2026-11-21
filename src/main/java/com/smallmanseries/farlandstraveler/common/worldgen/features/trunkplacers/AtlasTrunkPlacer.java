package com.smallmanseries.farlandstraveler.common.worldgen.features.trunkplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * “擎天树”树干放置器
 * <p>
 * 原版所有树干放置器都是从原点往上放置到指定高度，而擎天树要反过来：<b>树顶固定在同一个高度</b>，
 * 树干长度由地面到树顶的距离决定。以使整片森林的顶落在同一水平面上。
 * <p>
 * 做法是：
 * <ol>
 *     <li>{@link #getTreeHeight} 只报一个很小的值（{@code free_check_height}），
 *     让 {@code TreeFeature} 的空位检查只检查树干底部，不会因为“256 格高”去做整段检查；</li>
 *     <li>{@link #placeTrunk} 自己抽样树冠顶的 y，从原点一路铺到 {@code 树冠顶 - leaf_gap}，
 *     并把<b>树冠顶</b>作为 {@link FoliagePlacer.FoliageAttachment} 交给树冠放置器。</li>
 * </ol>
 * 因此 {@code leaf_gap} 之内没有原木，树干顶端藏在树冠里。
 */
public class AtlasTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<AtlasTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    IntProviders.codec(0, 4096).optionalFieldOf("canopy_top_y").forGetter(placer -> placer.canopyTopY),
                    Codec.intRange(1, 512).fieldOf("min_trunk_height").forGetter(placer -> placer.minTrunkHeight),
                    Codec.intRange(0, 4).fieldOf("trunk_radius").forGetter(placer -> placer.trunkRadius),
                    Codec.intRange(1, 64).fieldOf("leaf_gap").forGetter(placer -> placer.leafGap),
                    Codec.intRange(1, 64).fieldOf("free_check_height").forGetter(placer -> placer.freeCheckHeight)
            ).apply(inst, AtlasTrunkPlacer::new)
    );

    // 树干底部 3×3 可支持方块数，不满足则认为长在悬崖边上，放弃生成。
    private static final int MIN_GROUND_COUNT = 5;

    private final Optional<IntProvider> canopyTopY;
    private final int minTrunkHeight;
    private final int trunkRadius;
    private final int leafGap;
    private final int freeCheckHeight;

    /**
     * 创建擎天树树干放置器（从生成点一直到世界顶部）
     *
     * @param canopyTopY      树干顶端的y坐标，留空则默认取世界顶部
     * @param minTrunkHeight  树干短于这个格数就放弃生成
     * @param trunkRadius     树干半径，1 = 3×3
     * @param leafGap         树干顶端藏在树冠顶下方几格
     * @param freeCheckHeight 底部空位检查的高度
     */
    public AtlasTrunkPlacer(Optional<IntProvider> canopyTopY, int minTrunkHeight, int trunkRadius, int leafGap, int freeCheckHeight) {
        // 原版的高度三元组用不上：真正的树干高度直接由世界高度决定，见placeTrunk
        super(0, 0, 0);
        this.canopyTopY = canopyTopY;
        this.minTrunkHeight = minTrunkHeight;
        this.trunkRadius = trunkRadius;
        this.leafGap = leafGap;
        this.freeCheckHeight = freeCheckHeight;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return FLTTrunkPlacers.ATLAS_TRUNK_PLACER.get();
    }

    /**
     * 只用于 {@code TreeFeature} 底部空位检查的高度，不是真正的树干高度
     *
     * @param random 随机源
     * @return 底部检查高度
     */
    @Override
    public int getTreeHeight(RandomSource random) {
        return Math.max(1, this.freeCheckHeight);
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter,
                                                            RandomSource random, int treeHeight, BlockPos origin, TreeConfiguration config) {
        int canopyTopY;
        if (this.canopyTopY.isPresent()) {
            canopyTopY = this.canopyTopY.get().sample(random);
        } else {
            int worldHeight = level.getMaxY();
            canopyTopY = Mth.randomBetweenInclusive(random, worldHeight - 5, worldHeight);
        }

        int trunkTopY = canopyTopY - this.leafGap;

        // 地面已经接近树顶高度时，这棵树没有意义（例如长在边境之地的独石柱顶上）
        if (trunkTopY - origin.getY() < this.minTrunkHeight) {
            return List.of();
        }
        if (!hasGroundBelow(level, origin)) {
            return List.of();
        }

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int radius = this.trunkRadius;
        for (int y = origin.getY(); y <= trunkTopY; y++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    pos.set(origin.getX() + dx, y, origin.getZ() + dz);
                    this.placeLog(level, trunkSetter, random, pos, config);
                }
            }
        }

        // 树冠顶交给树冠放置器作为参考点：树干顶端再往上 leaf_gap 格
        BlockPos canopyTop = new BlockPos(origin.getX(), canopyTopY, origin.getZ());
        return List.of(new FoliagePlacer.FoliageAttachment(canopyTop, 0, false));
    }

    /**
     * 检查树干底部的地面是否撑得住一根 3×3 的巨柱
     *
     * @param level  世界
     * @param origin 树干原点
     * @return 下方可支持的方块足够多（达到 {@code MIN_GROUND_COUNT}）则为 true
     */
    private boolean hasGroundBelow(WorldGenLevel level, BlockPos origin) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int groundCount = 0;
        int radius = this.trunkRadius;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                pos.set(origin.getX() + dx, origin.getY() - 1, origin.getZ() + dz);
                BlockState below = level.getBlockState(pos);
                if (!below.isAir() && below.getFluidState().isEmpty()) {
                    groundCount++;
                }
            }
        }
        return groundCount >= MIN_GROUND_COUNT;
    }
}
