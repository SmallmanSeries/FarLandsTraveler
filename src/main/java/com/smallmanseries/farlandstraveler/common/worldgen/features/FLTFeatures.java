package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.CascadeConfiguration;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.MegaVineConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTFeatures {
    public static final DeferredRegister<Feature<? extends FeatureConfiguration>> FEATURES = DeferredRegister.create(Registries.FEATURE, FarLandsTraveler.MODID);

    public static final DeferredHolder<Feature<? extends FeatureConfiguration>, Feature<CascadeConfiguration>> CASCADE = FEATURES.register("cascade", () -> new CascadeFeature(CascadeConfiguration.CODEC));
    public static final DeferredHolder<Feature<? extends FeatureConfiguration>, Feature<MegaVineConfiguration>> MEGA_VINE = FEATURES.register("mega_vine", () -> new MegaVineFeature(MegaVineConfiguration.CODEC));
}
