package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.CascadeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class CascadeFeature extends Feature<CascadeConfiguration> {
    public CascadeFeature(Codec<CascadeConfiguration> codec) {
        super(codec);
    }

    private double baseProb(float distance, float radius) {
        return 1.0f / (1.0f + Math.exp((distance - radius) / 3.0f));
    }

    @Override
    public boolean place(FeaturePlaceContext<CascadeConfiguration> context) {
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        CascadeConfiguration config = context.config();

        float radius = config.radius().sample(random);
        float yFactor = config.yFactor().sample(random);
        int size = ((int) radius) + 1;

        float height = 1 / yFactor * radius;
        float radiusPow = radius * radius;
        float distancePow;
        double density;

        // 检查生成高度，如果高于254就生成失败
        if (origin.getY() > 254) {
            return false;
        }

        // 偏移原点至边境之地边缘
        int farLandsDist = Config.FAR_LANDS_DISTANCE.get();
        if (origin.getX() > farLandsDist - 3) {
            origin = origin.offset(-(origin.getX() - farLandsDist + 3), 0, 0);
        }
        if (origin.getX() < -farLandsDist) {
            origin = origin.offset(-(origin.getX() + farLandsDist), 0, 0);
        }
        if (origin.getZ() > farLandsDist - 3) {
            origin = origin.offset(0, 0, -(origin.getZ() - farLandsDist + 3));
        }
        if (origin.getZ() < -farLandsDist) {
            origin = origin.offset(0, 0, -(origin.getZ() + farLandsDist));
        }

        // 放置
        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                distancePow = i * i + j * j;
                for (int k = size; k >= -height; k--) {
                    if (k >= 0) {
                        density = baseProb((distancePow + k * k), radiusPow) - random.nextDouble() * 0.6;

                    } else {
                        density = baseProb((distancePow + k * k * yFactor * yFactor), radiusPow) - random.nextDouble() * 0.6;
                    }


                    if (density > 0.5 || (i == 0 && j == 0 && k >= radius - height)) {
                        this.safeSetBlock(level, origin.offset(new BlockPos(i, k, j)), config.cascadeBlock(), BlockBehaviour.BlockStateBase::canBeReplaced);
                    }
                }
            }
        }

        this.setBlock(level, origin, config.rootBlock());
        return true;
    }
}
