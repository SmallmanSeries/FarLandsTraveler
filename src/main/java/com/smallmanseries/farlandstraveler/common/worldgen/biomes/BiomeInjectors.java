package com.smallmanseries.farlandstraveler.common.worldgen.biomes;

import com.smallmanseries.farlandstraveler.common.DataRegister;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.Map;


public class BiomeInjectors {

    public static BiomeSource FAR_BIOMES;

    public static void injectFarBiomes(MinecraftServer server) {
        RegistryAccess registryAccess = server.registryAccess();
        BiomeSource farBiomes = registryAccess.lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS).biomeSource().value();

        FAR_BIOMES = farBiomes;

        Registry<LevelStem> levelStems = registryAccess.lookupOrThrow(Registries.LEVEL_STEM);

        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : levelStems.entrySet()) {
            if (entry.getKey() == LevelStem.OVERWORLD) {
                ChunkGenerator generator = entry.getValue().generator();
                BiomeSource biomeSource = generator.getBiomeSource();
                if (biomeSource instanceof MultiNoiseBiomeSource mBiomeSource) {
                    IBiomeSourceExtension extension = (IBiomeSourceExtension) mBiomeSource;
                    extension.FLT$injectBiomes(farBiomes);
                }
            }
        }
    }
}
