package com.smallmanseries.farlandstraveler.common.worldgen.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record MegaVineConfiguration(
        BlockState vineBlock,
        BlockPredicate canPlaceOn
) implements FeatureConfiguration {
    public static final Codec<MegaVineConfiguration> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    BlockState.CODEC.fieldOf("vine_block").forGetter(MegaVineConfiguration::vineBlock),
                    BlockPredicate.CODEC.fieldOf("can_place_on").forGetter(MegaVineConfiguration::canPlaceOn)
            ).apply(inst, MegaVineConfiguration::new)
    );
}
