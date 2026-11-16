package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.StackedTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

/**
 * “树上树”地物
 * <p>
 * 先正常生成一棵树，之后自动寻找这棵树的树顶，再在树顶上放置第二棵树
 */
public class StackedTreeFeature extends Feature<StackedTreeConfiguration> {
    public StackedTreeFeature(Codec<StackedTreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StackedTreeConfiguration> context) {
        StackedTreeConfiguration config = context.config();
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();

        // 放置底部的树
        if (!config.base().value().place(level, context.chunkGenerator(), context.random(), origin)) {
            return false;
        }

        // 从原点往上搜索最高的方块
        int topY = origin.getY();
        int limit = Math.min(origin.getY() + config.maxScanHeight(), level.getMaxY());
        for (int y = origin.getY() + 1; y <= limit; y++) {
            if (!level.getBlockState(new BlockPos(origin.getX(), y, origin.getZ())).isAir()) {
                topY = y;
            }
        }

        // 如果原点上方没有方块（例如底部的地物放置失败或者原点不在底部地物的中心），则直接结束生成
        if (topY == origin.getY()) {
            return true;
        }

        // 放置顶部的树
        config.top().value().place(level, context.chunkGenerator(), context.random(), new BlockPos(origin.getX(), topY + 1, origin.getZ()));
        return true;
    }
}
