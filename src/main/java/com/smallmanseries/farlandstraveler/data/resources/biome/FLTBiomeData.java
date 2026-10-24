package com.smallmanseries.farlandstraveler.data.resources.biome;

import com.smallmanseries.farlandstraveler.common.worldgen.FLTBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FLTBiomeData {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(FLTBiomes.OOTS_LABORATORY, FarLandsBiomes.ootsLaboratory(placedFeatures, carvers));

        context.register(FLTBiomes.FAR_LANDS_EDGE, FarLandsBiomes.farLandsEdge(placedFeatures, carvers));
        context.register(FLTBiomes.WALL_OF_BLOOM, FarLandsBiomes.wallOfBloom(placedFeatures, carvers));
        context.register(FLTBiomes.WALL_OF_GREEN, FarLandsBiomes.wallOfGreen(placedFeatures, carvers));
        context.register(FLTBiomes.WALL_OF_ICE, FarLandsBiomes.wallOfIce(placedFeatures, carvers));
        context.register(FLTBiomes.WALL_OF_SAND, FarLandsBiomes.wallOfSand(placedFeatures, carvers));
        context.register(FLTBiomes.WALL_OF_SUNSET, FarLandsBiomes.wallOfSunset(placedFeatures, carvers));

        context.register(FLTBiomes.FAR_LANDS, FarLandsBiomes.farLands(placedFeatures, carvers));

        context.register(FLTBiomes.FRINGE_LANDS, FarLandsBiomes.fringeLands(placedFeatures, carvers));
    }
}
