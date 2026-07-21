package com.smallmanseries.farlandstraveler.common.worldgen.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CascadeConfiguration(
        BlockState rootBlock,
        BlockState cascadeBlock,
        FloatProvider radius,
        FloatProvider yFactor,
        BlockPredicate canPlaceOn
) implements FeatureConfiguration {
    public static final Codec<CascadeConfiguration> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    BlockState.CODEC.fieldOf("root_block").forGetter(CascadeConfiguration::rootBlock),
                    BlockState.CODEC.fieldOf("cascade_block").forGetter(CascadeConfiguration::cascadeBlock),
                    FloatProviders.codec(1, 128).fieldOf("radius").forGetter(CascadeConfiguration::radius),
                    FloatProviders.codec(0, 128).fieldOf("y_factor").forGetter(CascadeConfiguration::yFactor),
                    BlockPredicate.CODEC.fieldOf("can_place_on").forGetter(CascadeConfiguration::canPlaceOn)
            ).apply(inst, CascadeConfiguration::new)
    );
}
