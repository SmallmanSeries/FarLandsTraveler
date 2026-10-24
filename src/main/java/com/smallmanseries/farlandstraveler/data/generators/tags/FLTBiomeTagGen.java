package com.smallmanseries.farlandstraveler.data.generators.tags;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import com.smallmanseries.farlandstraveler.common.worldgen.FLTBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class FLTBiomeTagGen extends BiomeTagsProvider {
    public FLTBiomeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FarLandsTraveler.MODID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider registries) {

        // Far Lands Traveler
        this.tag(FLTTags.Biomes.HAS_STRUCTURE_ABANDONED_VILLAGE_NORMAL).add(
                FLTBiomes.FAR_LANDS
        );
        this.tag(FLTTags.Biomes.HAS_STRUCTURE_EXPLORER_BASE_INDEV_COBBLESTONE).add(
                FLTBiomes.FAR_LANDS
        );
        this.tag(FLTTags.Biomes.HAS_STRUCTURE_EXPLORER_BASE_INDEV_WOOD).add(
                FLTBiomes.FAR_LANDS
        );
        this.tag(FLTTags.Biomes.IS_FAR_LANDS).add(
                FLTBiomes.FAR_LANDS
        );
        this.tag(FLTTags.Biomes.IS_FAR_LANDS_EDGE).add(
                FLTBiomes.FAR_LANDS_EDGE,
                FLTBiomes.WALL_OF_BLOOM,
                FLTBiomes.WALL_OF_GREEN,
                FLTBiomes.WALL_OF_ICE,
                FLTBiomes.WALL_OF_SAND,
                FLTBiomes.WALL_OF_SUNSET
        );
        this.tag(FLTTags.Biomes.SPAWNS_PRIMITIVE_ENDERMAN)
                .addTags(
                        FLTTags.Biomes.IS_FAR_LANDS_EDGE,
                        BiomeTags.IS_OVERWORLD
                ).remove(
                        Biomes.MUSHROOM_FIELDS,
                        Biomes.DEEP_DARK
                );
    }
}
