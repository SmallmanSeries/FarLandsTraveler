package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.CascadeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class CascadeFeature extends Feature<CascadeConfiguration> {
    public CascadeFeature(Codec<CascadeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CascadeConfiguration> context) {
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        CascadeConfiguration config = context.config();

        this.setBlock(level, origin, Blocks.GLOWSTONE.defaultBlockState());

        return false;
    }
}
