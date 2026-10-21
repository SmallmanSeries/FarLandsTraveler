package com.smallmanseries.farlandstraveler.common.worldgen;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class FLTBiomes {

    // 特殊生物群系
    public static final ResourceKey<Biome> OOTS_LABORATORY = register("oots_laboratory");
    // 边境之地边缘
    public static final ResourceKey<Biome> FAR_LANDS_EDGE = register("far_lands_edge");
    public static final ResourceKey<Biome> WALL_OF_BLOOM = register("wall_of_bloom");
    public static final ResourceKey<Biome> WALL_OF_GREEN = register("wall_of_green");
    public static final ResourceKey<Biome> WALL_OF_ICE = register("wall_of_ice");
    public static final ResourceKey<Biome> WALL_OF_SAND = register("wall_of_sand");
    public static final ResourceKey<Biome> WALL_OF_SUNSET = register("wall_of_sunset");
    // 边境之地
    public static final ResourceKey<Biome> FAR_LANDS = register("far_lands");
    // 遥远之地
    // 边缘之地
    public static final ResourceKey<Biome> FRINGE_LANDS = register("fringe_lands");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
    }
}
