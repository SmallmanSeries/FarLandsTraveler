package com.smallmanseries.farlandstraveler.data.resources.feature;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;

public class FLTPlacedFeatures {

    public static final ResourceKey<PlacedFeature> FLOWER_FAR_LANDS_PLAINS = createKey("flower_far_lands_plains");

    // 边境之地边缘装饰
    public static final ResourceKey<PlacedFeature> FLOWER_CHERRY_WALL_OF_BLOOM = createKey("flower_cherry_wall_of_bloom");
    public static final ResourceKey<PlacedFeature> WALL_OF_BLOOM_BUSH = createKey("wall_of_bloom_bush");
    public static final ResourceKey<PlacedFeature> WALL_OF_BLOOM_CHERRY_CASCADE = createKey("wall_of_bloom_cherry_cascade");
    public static final ResourceKey<PlacedFeature> WALL_OF_BLOOM_GRASS = createKey("wall_of_bloom_grass");
    public static final ResourceKey<PlacedFeature> WALL_OF_BLOOM_VINES = createKey("wall_of_bloom_vines");
    public static final ResourceKey<PlacedFeature> WALL_OF_GREEN_BUSH = createKey("wall_of_green_bush");
    public static final ResourceKey<PlacedFeature> WALL_OF_GREEN_GRASS = createKey("wall_of_green_grass");
    public static final ResourceKey<PlacedFeature> WALL_OF_GREEN_MEGA_VINE = createKey("wall_of_green_mega_vine");
    public static final ResourceKey<PlacedFeature> WALL_OF_GREEN_MOSS = createKey("wall_of_green_moss");
    public static final ResourceKey<PlacedFeature> WALL_OF_GREEN_TREES = createKey("wall_of_green_trees");
    public static final ResourceKey<PlacedFeature> WALL_OF_GREEN_VINES = createKey("wall_of_green_vines");
    public static final ResourceKey<PlacedFeature> WALL_OF_ICE_ICICLE = createKey("wall_of_ice_icicle");

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> flowerFarLandsPlains = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FLOWER_FAR_LANDS_PLAINS);
        Holder<ConfiguredFeature<?, ?>> flowerCherry = configuredFeatures.getOrThrow(VegetationFeatures.FLOWER_CHERRY);
        Holder<ConfiguredFeature<?, ?>> wallOfBloomBush = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WALL_OF_BLOOM_BUSH);
        Holder<ConfiguredFeature<?, ?>> wallOfBloomCherryCascade = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WALL_OF_BLOOM_CHERRY_CASCADE);
        Holder<ConfiguredFeature<?, ?>> grass = configuredFeatures.getOrThrow(VegetationFeatures.GRASS);
        Holder<ConfiguredFeature<?, ?>> grassJungle = configuredFeatures.getOrThrow(VegetationFeatures.GRASS_JUNGLE);
        Holder<ConfiguredFeature<?, ?>> vines = configuredFeatures.getOrThrow(VegetationFeatures.VINES);
        Holder<ConfiguredFeature<?, ?>> wallOfGreenBush = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WALL_OF_GREEN_BUSH);
        Holder<ConfiguredFeature<?, ?>> wallOfGreenMegaVine = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WALL_OF_GREEN_MEGA_VINE);
        Holder<ConfiguredFeature<?, ?>> wallOfGreenMoss = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WALL_OF_GREEN_MOSS);
        Holder<ConfiguredFeature<?, ?>> wallOfGreenTrees = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WALL_OF_GREEN_TREES);
        Holder<ConfiguredFeature<?, ?>> wallOfIceIcicle = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WALL_OF_ICE_ICICLE);

        PlacementUtils.register(
                context,
                FLOWER_FAR_LANDS_PLAINS,
                flowerFarLandsPlains,
                NoiseThresholdCountPlacement.of(-0.8, 15, 4),
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );

        PlacementUtils.register(
                context,
                FLOWER_CHERRY_WALL_OF_BLOOM,
                flowerCherry,
                CountOnEveryLayerPlacement.of(26),
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );

        PlacementUtils.register(
                context,
                WALL_OF_BLOOM_BUSH,
                wallOfBloomBush,
                CountOnEveryLayerPlacement.of(1),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO))
        );

        PlacementUtils.register(
                context,
                WALL_OF_BLOOM_CHERRY_CASCADE,
                wallOfBloomCherryCascade,
                CountOnEveryLayerPlacement.of(2),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                WALL_OF_BLOOM_GRASS,
                grass,
                CountOnEveryLayerPlacement.of(32),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );

        PlacementUtils.register(
                context,
                WALL_OF_BLOOM_VINES,
                vines,
                CountPlacement.of(127),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(250)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                WALL_OF_GREEN_BUSH,
                wallOfGreenBush,
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO))
        );

        PlacementUtils.register(
                context,
                WALL_OF_GREEN_GRASS,
                grassJungle,
                CountOnEveryLayerPlacement.of(25),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(
                                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                BlockPredicate.not(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.PODZOL))
                        )
                )
        );

        PlacementUtils.register(
                context,
                WALL_OF_GREEN_MEGA_VINE,
                wallOfGreenMegaVine,
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(250)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                WALL_OF_GREEN_MOSS,
                wallOfGreenMoss,
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.absolute(60), VerticalAnchor.absolute(250), 1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                WALL_OF_GREEN_TREES,
                wallOfGreenTrees,
                CountOnEveryLayerPlacement.of(20),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                WALL_OF_GREEN_VINES,
                vines,
                CountPlacement.of(255),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.absolute(63), VerticalAnchor.absolute(250), 1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                WALL_OF_ICE_ICICLE,
                wallOfIceIcicle,
                CountPlacement.of(160),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(72), VerticalAnchor.absolute(250)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );
    }
}
