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

    public static Biome woodLands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers,
                                  boolean opal, // 是否为白玉森林
                                  boolean oldGrowth // 是否为原始变种（边境森林和白玉森林都有原始变种）
    ) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);
        // todo 根据边境森林和白玉森林选择音乐，该功能暂时搁置
        BackgroundMusic music = new BackgroundMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL);
        // 边境芍药等小型花（边境森林暂定不生成大型花）
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WOOD_LANDS_FLOWERS);

        // 矿物设置暂时与森林保持一致
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        // 根据变种添加树类地物
        if (opal) {
            // 这里添加白玉森林专属的花。注：白玉花是跟着白玉树生成的，不应在这里添加。
            if (oldGrowth) {
                // 这里添加高白玉树
            } else {
                // 这里添加矮白玉树
            }
        } else {
            if (oldGrowth) {
                // 这里添加高密度的大型苍境树、大型苍境树上面叠一个小型苍境树、高苍境树、倒置的大苍境树（一个地物包办所有）
            } else {
                // 普通树种（苍境树、白玉树）为主，其中混杂小概率的异常树种（高树、虚树、玄紫树、树上树）
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.WOOD_LANDS_TREES);
            }
        }

        // 其他设置暂时与森林保持一致
        BiomeDefaultFeatures.addBushes(generation);
        BiomeDefaultFeatures.addDefaultFlowers(generation);
        BiomeDefaultFeatures.addForestGrass(generation);

        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(mobs);
        BiomeDefaultFeatures.commonSpawns(mobs);
        if (!opal) {
            mobs.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4));
        }

        // 温度与湿度与主世界的森林保持一致（0.7 / 0.8），覆盖色调，使其更接近边境芍药的茎
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .waterColor(4159204)
                .grassColorOverride(0x5DCA5D)
                .foliageColorOverride(0x62D22E)
                .build();

        return baseBiome(opal ? 0.6F : 0.7F, opal ? 0.6F : 0.8F)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, music)
                .specialEffects(effects)
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    // 飘浮草原 / 繁星草原（依靠starlit参数区分）
    public static Biome floatingPlains(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers,
                                       boolean starlit // 是否为繁星草原
    ) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);

        // 其他设置暂时与原版平原一致
        BiomeDefaultFeatures.plainsSpawns(mobs);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addBushes(generation);
        addFarPlainVegetation(generation);

        // 浮空层叠草：成丛地摊在各自的高度平面上
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.FLOATING_PLAINS_GRASS);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.FLOATING_PLAINS_TALL_GRASS);
        // 浮空花：随机散布
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.FLOATING_PLAINS_FLOWERS);

        // 浮空萤火虫灌木丛：繁星草原生成得更多
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                starlit ? FLTPlacedFeatures.STARLIT_PLAINS_FIREFLY_BUSH : FLTPlacedFeatures.FLOATING_PLAINS_FIREFLY_BUSH);

        if (starlit) {
            // 繁星：散布在天空中的荧石
            generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.STARLIT_PLAINS_GLOWSTONE);
        }

        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);

        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .waterColor(0x8FD8EA)
                .grassColorOverride(0x93E3B7)
                .foliageColorOverride(0x74D9C4)
                .build();

        return baseBiome(0.8F, 0.4F)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xBFE6F5)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL))
                .specialEffects(effects)
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    // 无叶树林
    // 灵感来自经典 Herobrine 都市传说：只剩树干的无叶树林、偏暗的色调、浓厚的大雾、没有音乐、没有生物。
    public static Biome leaflessForest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        // 不生成任何生物
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);

        // 只生成树干的无叶树
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.LEAFLESS_FOREST_TREES);

        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        // 普通森林的草和少量枯萎的灌木
        BiomeDefaultFeatures.addForestGrass(generation);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.LEAFLESS_FOREST_DEAD_BUSH);

        // 偏暗的配色
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .waterColor(0x2E4148)
                .grassColorOverride(0x46503A)
                .foliageColorOverride(0x3A4232)
                .dryFoliageColorOverride(0x5A4A33)
                .build();

        return baseBiome(0.7F, 0.8F)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0xFF46505A)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0xFF3A4147)
                // 低能见度，还原都市传说
                .setAttribute(EnvironmentAttributes.FOG_START_DISTANCE, 8.0F)
                .setAttribute(EnvironmentAttributes.FOG_END_DISTANCE, 80.0F)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.EMPTY)
                .setAttribute(EnvironmentAttributes.MUSIC_VOLUME, 0.0F)
                .specialEffects(effects)
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    // 擎天森林
    // 以夸张的尺寸为主题的生物群系，充满树干3×3、高约 64 格的擎天树，树顶上托着小型空岛。
    // 除树种以外，其余设置与原版巨型针叶林（old growth spruce taiga）保持一致。
    public static Biome skyReachingForest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);

        // 生物设置与暂时保持与原版巨型针叶林一致
        BiomeDefaultFeatures.farmAnimals(mobs);
        mobs.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4))
                .addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3))
                .addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 4));
        BiomeDefaultFeatures.commonSpawns(mobs);

        BiomeDefaultFeatures.addMossyStoneBlock(generation);
        BiomeDefaultFeatures.addFerns(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        // 灰化土斑块（参考原版巨型针叶林）
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.SKY_REACHING_FOREST_PODZOL);
        // 擎天树
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.SKY_REACHING_FOREST_TREES);
        // 空岛必须排在擎天树之后，否则读不到本区块刚生成的树冠
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.SKY_REACHING_FOREST_ISLANDS);

        BiomeDefaultFeatures.addDefaultFlowers(generation);
        BiomeDefaultFeatures.addGiantTaigaVegetation(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);
        BiomeDefaultFeatures.addCommonBerryBushes(generation);

        return baseBiome(0.5F, 0.8F)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    // 边境花田
    // 地表每一列都长花，花的种类直接沿用原版繁花森林那套按噪声渐变的花配置（也就是骨粉在繁花森林里长出来的分布）。
    // 树是繁花苍境树，其余设置与原版繁花森林一致。
    public static Biome flowerLands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);

        // 生物设置与原版繁花森林一致
        BiomeDefaultFeatures.farmAnimals(mobs);
        BiomeDefaultFeatures.commonSpawns(mobs);
        mobs.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));

        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);

        // 树先长、花后铺：花按地表高度图放置，树冠底下的那一列会被花自己的存活判定挡掉
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.FLOWER_LANDS_TREES);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FLTPlacedFeatures.FLOWER_LANDS_FLOWERS);

        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);

        // 温湿度与原版繁花森林一致（0.7 / 0.8）
        return baseBiome(0.7F, 0.8F)
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(FLTMusics.createMusic(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL)))
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
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
