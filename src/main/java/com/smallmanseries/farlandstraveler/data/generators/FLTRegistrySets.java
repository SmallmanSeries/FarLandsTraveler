package com.smallmanseries.farlandstraveler.data.generators;

import com.smallmanseries.farlandstraveler.data.resources.biome.FLTBiomeData;
import com.smallmanseries.farlandstraveler.data.resources.biome.FLTCarvers;
import com.smallmanseries.farlandstraveler.data.resources.feature.FLTConfiguredFeatures;
import com.smallmanseries.farlandstraveler.data.resources.feature.FLTPlacedFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class FLTRegistrySets {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, FLTBiomeData::bootstrap)
            .add(Registries.CONFIGURED_CARVER, FLTCarvers::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, FLTConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FLTPlacedFeatures::bootstrap);

}
