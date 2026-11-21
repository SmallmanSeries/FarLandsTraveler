package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.SkyIslandConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

/**
 * “空岛”地物
 * <p>
 * 通用的空岛地物，生成一个类似小型末地岛的不规则空岛，中间厚四周薄。
 * <p>
 * 当然也可以是不浮空的，比如被擎天树托举着。通过测试中点下方的方块是否满足特定的谓词来决定是否放置，这样就能做到“有东西托着才生成”的效果
 */
public class SkyIslandFeature extends Feature<SkyIslandConfiguration> {

    // 顶面半径的边缘抖动：三条不同频率的正弦叠加，让轮廓成为不规则的圆而不是正圆。
    private static final double EDGE_BASE = 0.86;
    private static final double EDGE_WAVE_1 = 0.10;
    private static final double EDGE_WAVE_2 = 0.06;

    public SkyIslandFeature(Codec<SkyIslandConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SkyIslandConfiguration> context) {
        SkyIslandConfiguration config = context.config();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        int depth = config.depth().sample(random);

        // 测试下方是否有指定方块托举
        boolean cannotSpawn = true;
        for (int i = 0; i <= depth; i++) {
            if (config.canPlaceOn().test(level, origin.below(i))) {
                cannotSpawn = false;
            }
        }
        if (cannotSpawn) {
            return false;
        }

        int radius = config.radius().sample(random);
        int topY = origin.getY();
        double phase = random.nextDouble() * 2.0 * Math.PI;
        BlockState surfaceState = config.surfaceProvider().getState(level, random, origin);
        BlockState fillerState = config.fillerProvider().getState(level, random, origin);

        int placed = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double distance = Math.sqrt(dx * dx + dz * dz);
                double angle = Math.atan2(dz, dx);
                double edge = radius * (EDGE_BASE + EDGE_WAVE_1 * Math.sin(3.0 * angle + phase)
                        + EDGE_WAVE_2 * Math.sin(5.0 * angle + phase * 1.7));
                if (distance > edge) {
                    continue;
                }

                // 厚度按二次曲线从岛心向外收薄，于是岛底自然成为一个锥形
                double normalized = Math.min(1.0, distance / Math.max(edge, 1.0E-4));
                int thickness = 1 + (int) Math.round(depth * (1.0 - normalized * normalized));

                for (int y = topY; y > topY - thickness; y--) {
                    BlockState state = y == topY ? surfaceState : fillerState;
                    if (placeIslandBlock(level, new BlockPos(origin.getX() + dx, y, origin.getZ() + dz), state)) {
                        placed++;
                    }
                }
            }
        }
        return placed > 0;
    }

    /**
     * 放置一格岛体，只覆盖空气、树叶和可被树替换的方块
     *
     * @param level 世界
     * @param pos   目标位置
     * @param state 要放置的方块状态
     * @return 放置是否成功
     */
    private boolean placeIslandBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
        BlockState existing = level.getBlockState(pos);
        if (!existing.isAir() && !existing.is(BlockTags.LEAVES) && !existing.is(BlockTags.REPLACEABLE_BY_TREES)) {
            return false;
        }
        level.setBlock(pos, state, 2);
        return true;
    }
}
