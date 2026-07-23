package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.CascadeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

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

        // 检查生成高度，如果高于254就生成失败
        if (origin.getY() > 254) {
            return false;
        }

        // 偏移原点至边境之地边缘
        int farLandsDist = Config.FAR_LANDS_DISTANCE.get();
        int clampedX = Math.clamp(origin.getX(), -farLandsDist, farLandsDist - 4);
        int clampedZ = Math.clamp(origin.getZ(), -farLandsDist, farLandsDist - 4);
        if (clampedX != origin.getX() || clampedZ != origin.getZ()) {
            origin = new BlockPos(clampedX, origin.getY(), clampedZ);
        }

        // 检查本格是不是没有流体
        if (!level.getFluidState(origin).isEmpty()) {
            return false;
        }

        // 检查下方的方块
        boolean flag = false;
        for (int i = -1; i <= 1 && !flag; i++) {
            for (int j = -1; j <= 1; j++) {
                if (config.canPlaceOn().test(level, new BlockPos(origin.getX() + i, origin.getY() - 1, origin.getZ() + j))) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }

        // 初始化放置
        float radius = config.radius().sample(random);
        float yFactor = config.yFactor().sample(random);
        int size = ((int) radius) + 1;

        float height = 1 / yFactor * radius;
        float radiusPow = radius * radius;
        float distancePow;
        double density;

        // 放置
        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                distancePow = i * i + j * j;
                for (int k = size; k >= -height; k--) {
                    density = baseProb((distancePow + k * k * (k >= 0 ? 1 : (yFactor * yFactor))), radiusPow) - random.nextDouble() * 0.6;

                    if (density > 0.5 || (i == 0 && j == 0 && k >= radiusPow - height)) {
                        this.safeSetBlock(level, origin.offset(new BlockPos(i, k, j)), config.cascadeBlock(), BlockBehaviour.BlockStateBase::canBeReplaced);
                    }
                }
            }
        }

        this.setBlock(level, origin, config.rootBlock());
        return true;
    }
}
