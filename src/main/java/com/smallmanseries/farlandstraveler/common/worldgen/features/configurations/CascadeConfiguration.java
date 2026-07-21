package com.smallmanseries.farlandstraveler.common.worldgen.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.BlockBlobConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CascadeConfiguration(
      //  BlockState rootBlock,
        BlockState cascadeBlock
       // IntProvider height,
       // IntProvider radius,
        //BlockPredicate canPlaceOn
) implements FeatureConfiguration {
    public static final Codec<CascadeConfiguration> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    //BlockState.CODEC.fieldOf("root_block").forGetter(CascadeConfiguration::rootBlock),
                    BlockState.CODEC.fieldOf("cascade_block").forGetter(CascadeConfiguration::cascadeBlock)
                    //IntProviders.codec(1, 128).fieldOf("height").forGetter(CascadeConfiguration::height),
                    //IntProviders.codec(1, 128).fieldOf("radius").forGetter(CascadeConfiguration::radius),
                    //BlockPredicate.CODEC.fieldOf("can_place_on").forGetter(CascadeConfiguration::canPlaceOn)
            ).apply(inst, CascadeConfiguration::new)
    );
}
