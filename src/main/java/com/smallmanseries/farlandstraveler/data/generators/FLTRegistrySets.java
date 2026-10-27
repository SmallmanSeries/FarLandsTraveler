package com.smallmanseries.farlandstraveler.data.generators;

import com.smallmanseries.farlandstraveler.data.resources.biome.FLTBiomeData;
import com.smallmanseries.farlandstraveler.data.resources.biome.FLTCarvers;
import com.smallmanseries.farlandstraveler.data.resources.feature.FLTConfiguredFeatures;
import com.smallmanseries.farlandstraveler.data.resources.feature.FLTPlacedFeatures;
import com.smallmanseries.farlandstraveler.data.resources.structure.FLTStructureData;
import com.smallmanseries.farlandstraveler.data.resources.structure.FLTStructureSets;
import com.smallmanseries.farlandstraveler.data.resources.structure.FLTTemplatePools;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class FLTRegistrySets {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, FLTBiomeData::bootstrap)
            .add(Registries.CONFIGURED_CARVER, FLTCarvers::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, FLTConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FLTPlacedFeatures::bootstrap)
            .add(Registries.STRUCTURE, FLTStructureData::bootstrap)
            .add(Registries.STRUCTURE_SET, FLTStructureSets::bootstrap)
            .add(Registries.TEMPLATE_POOL, FLTTemplatePools::bootstrap);

}
