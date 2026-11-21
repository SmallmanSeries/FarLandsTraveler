package com.smallmanseries.farlandstraveler.common.worldgen.features.trunkplacers;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTTrunkPlacers {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, FarLandsTraveler.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<AtlasTrunkPlacer>> ATLAS_TRUNK_PLACER =
            TRUNK_PLACER_TYPES.register("atlas_trunk_placer", () -> new TrunkPlacerType<>(AtlasTrunkPlacer.CODEC));
}
