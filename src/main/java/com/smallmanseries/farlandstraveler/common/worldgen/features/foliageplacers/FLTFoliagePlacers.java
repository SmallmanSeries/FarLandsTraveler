package com.smallmanseries.farlandstraveler.common.worldgen.features.foliageplacers;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTFoliagePlacers {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, FarLandsTraveler.MODID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<AtlasFoliagePlacer>> ATLAS_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("atlas_foliage_placer", () -> new FoliagePlacerType<>(AtlasFoliagePlacer.CODEC));
}
