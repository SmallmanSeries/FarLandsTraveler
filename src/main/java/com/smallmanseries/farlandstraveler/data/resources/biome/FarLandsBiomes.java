package com.smallmanseries.farlandstraveler.data.resources.biome;

import com.smallmanseries.farlandstraveler.client.sound.FLTMusics;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.data.resources.feature.FLTPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FarLandsBiomes {

    // 特殊生物群系
    public static Biome ootsLaboratory(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // 添加岩浆湖，但不添加雕刻器
        generation.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
        generation.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);

        // 不添加水晶洞、刷怪房、任何矿石和涌泉
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        BiomeDefaultFeatures.addSurfaceFreezing(generation);

        // 其余设置暂时与平原保持一致
        BiomeDefaultFeatures.plainsSpawns(mobs);
        BiomeDefaultFeatures.addPlainGrass(generation);
        BiomeDefaultFeatures.addBushes(generation);
        addFarPlainVegetation(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);

        return baseBiome(0.8F, 0.4F)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    // 边境之地边缘
    // 当前使用占位符生物群系
    public static Biome farLandsEdge(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        addFarCarversAndLakes(generation);

        return placeHolderBiome(generation).build();
    }

    public static Biome wallOfBloom(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();

        // 盛放之壁独有地物

        // 不添加岩浆涌泉，防止岩浆把花之瀑布烧掉
        generation.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);

        // 其余设置暂时与樱花树林保持一致
        mobs.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.PIG, 1, 2))
                .addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 6))
                .addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 4));
        BiomeDefaultFeatures.commonSpawns(mobs);
        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addPlainGrass(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        // 插入：wall of bloom vegetation
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_BLOOM_GRASS);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_BLOOM_BUSH);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_BLOOM_VINES);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.FLOWER_CHERRY_WALL_OF_BLOOM);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_BLOOM_CHERRY_CASCADE);

        BiomeDefaultFeatures.addExtraEmeralds(generation);
        BiomeDefaultFeatures.addInfestedStone(generation);
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .waterColor(6141935)
                .grassColorOverride(11983713)
                .foliageColorOverride(11983713);
        return baseBiome(0.5F, 0.8F)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, -10635281)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .specialEffects(effects.build())
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    public static Biome wallOfGreen(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // 不添加岩浆涌泉，防止岩浆把巨型藤蔓及其他植被烧掉
        generation.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);

        // 生物设置暂时与丛林保持一致
        mobs.addSpawn(MobCategory.CREATURE, 40, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 1, 2))
                .addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 1, 3))
                .addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 1, 2));

        BiomeDefaultFeatures.baseJungleSpawns(mobs);
        globalOverworldGeneration(generation);

        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FLTPlacedFeatures.WALL_OF_GREEN_MOSS);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addLightBambooVegetation(generation);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_GREEN_TREES);
        BiomeDefaultFeatures.addWarmFlowers(generation);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_GREEN_GRASS);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_GREEN_MEGA_VINE);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WALL_OF_GREEN_VINES);
        BiomeDefaultFeatures.addJungleMelons(generation);

        return baseBiome(0.95F, (float) 0.9)
                .generationSettings(generation.build())
                .mobSpawnSettings(mobs.build())
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .setAttribute(EnvironmentAttributes.INCREASED_FIRE_BURNOUT, true)
                .build();
    }

    public static Biome wallOfIce(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // 不添加涌泉，添加冰凌。设定上涌泉都被冻住了
        generation.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, FLTPlacedFeatures.WALL_OF_ICE_ICICLE);

        // 其余设置暂时与雪原保持一致
        globalOverworldGeneration(generation);

        mobs.creatureGenerationProbability(0.07F);
        BiomeDefaultFeatures.snowySpawns(mobs, true);

        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addSnowyTrees(generation);
        BiomeDefaultFeatures.addDefaultFlowers(generation);
        BiomeDefaultFeatures.addDefaultGrass(generation);

        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);
        return baseBiome(0.0F, 0.5F)
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .build();
    }

    public static Biome wallOfSand(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // 正常添加涌泉
        generation.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
        generation.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA);

        // 其余设置暂时与沙漠保持一致
        BiomeDefaultFeatures.desertSpawns(mobs);
        BiomeDefaultFeatures.addFossilDecoration(generation);
        globalOverworldGeneration(generation);

        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addDefaultFlowers(generation);
        BiomeDefaultFeatures.addDefaultGrass(generation);
        BiomeDefaultFeatures.addDesertVegetation(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDesertExtraVegetation(generation);
        BiomeDefaultFeatures.addDesertExtraDecoration(generation);
        return baseBiome(2.0F, 0.0F)
                .hasPrecipitation(false)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .setAttribute(EnvironmentAttributes.SNOW_GOLEM_MELTS, true)
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    public static Biome wallOfSunset(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // 正常添加涌泉
        generation.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
        generation.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA);

        // 其余设置暂时与恶地保持一致
        BiomeDefaultFeatures.farmAnimals(mobs);
        BiomeDefaultFeatures.commonSpawns(mobs);
        mobs.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.ARMADILLO, 1, 2));
        mobs.creatureGenerationProbability(0.03F);
        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addExtraGold(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        BiomeDefaultFeatures.addBadlandGrass(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addBadlandExtraVegetation(generation);
        return baseBiome(2.0F, 0.0F)
                .hasPrecipitation(false)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .setAttribute(EnvironmentAttributes.SNOW_GOLEM_MELTS, true)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4159204).foliageColorOverride(10387789).grassColorOverride(9470285).build())
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    // 边境之地
    // 当前使用占位符生物群系
    public static Biome farLands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        addFarCarversAndLakes(generation);

        return placeHolderBiome(generation).build();
    }

    // 遥远之地

    // 边缘之地
    // 当前使用占位符生物群系
    public static Biome fringeLands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        // 不添加雕刻器，防止天空之桥被凿得满是孔洞
        generation.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
        generation.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);

        return placeHolderBiome(generation).build();
    }


    private static Biome.BiomeBuilder placeHolderBiome(BiomeGenerationSettings.Builder generation) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.addDefaultCrystalFormations(generation);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);
        BiomeDefaultFeatures.addSurfaceFreezing(generation);

        BiomeDefaultFeatures.plainsSpawns(mobs);
        BiomeDefaultFeatures.addPlainGrass(generation);
        BiomeDefaultFeatures.addBushes(generation);

        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        addFarPlainVegetation(generation);

        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);
        return baseBiome(0.8F, 0.4F)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build());
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder generation) {
        addFarCarversAndLakes(generation);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generation);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
        BiomeDefaultFeatures.addSurfaceFreezing(generation);
    }

    private static void addFarCarversAndLakes(BiomeGenerationSettings.Builder builder) {
        // 默认雕刻器：边境之地独有雕刻器
        builder.addCarver(FLTCarvers.FAR_LANDS_CAVE);
        builder.addCarver(FLTCarvers.FAR_LANDS_CAVE_EXTRA);
        builder.addCarver(FLTCarvers.FAR_LANDS_CANYON);
        builder.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
        builder.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);
    }

    private static void addFarPlainVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.FLOWER_FAR_LANDS_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
    }

    private static Biome.BiomeBuilder baseBiome(float temperature, float downfall) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(temperature)
                .downfall(downfall)
                .setAttribute(
                        EnvironmentAttributes.SKY_COLOR,
                        OverworldBiomes.calculateSkyColor(temperature)
                ).specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(4159204)
                                .build()
                );
    }
}
