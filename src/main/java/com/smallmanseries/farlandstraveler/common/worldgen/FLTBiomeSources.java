package com.smallmanseries.farlandstraveler.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.biomesources.BoxSelectBiomeSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTBiomeSources {

    public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCES = DeferredRegister.create(Registries.BIOME_SOURCE, FarLandsTraveler.MODID);

    static {
        BIOME_SOURCES.register("box_select", () -> BoxSelectBiomeSource.CODEC);
    }
}
