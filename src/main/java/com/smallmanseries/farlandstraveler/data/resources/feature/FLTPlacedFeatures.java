package com.smallmanseries.farlandstraveler.data.resources.feature;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.features.placements.SwirlPlacement;
import com.smallmanseries.farlandstraveler.common.worldgen.features.placements.VerticalOffsetPlacement;
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
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
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

    // 边境森林
    public static final ResourceKey<PlacedFeature> WOOD_LANDS_TREES = createKey("wood_lands_trees");
    public static final ResourceKey<PlacedFeature> WOOD_LANDS_INVERTED_BOLDER = createKey("wood_lands_inverted_bolder");
    public static final ResourceKey<PlacedFeature> WOOD_LANDS_TALL_INVERTED_BOLDER = createKey("wood_lands_tall_inverted_bolder");
    public static final ResourceKey<PlacedFeature> WOOD_LANDS_TALL_BOLDER = createKey("wood_lands_tall_bolder");
    public static final ResourceKey<PlacedFeature> IMAGINARBOR = createKey("imaginarbor");
    public static final ResourceKey<PlacedFeature> VOIDVIOLET = createKey("voidviolet");
    public static final ResourceKey<PlacedFeature> WOOD_LANDS_TREE_ON_TREE = createKey("wood_lands_tree_on_tree");
    public static final ResourceKey<PlacedFeature> WOOD_LANDS_FLOWERS = createKey("wood_lands_flowers");
    public static final ResourceKey<PlacedFeature> WOOD_LANDS_NORMAL_TREES = createKey("wood_lands_normal_trees");
    public static final ResourceKey<PlacedFeature> BOLDER_TREE = createKey("bolder_tree");
    public static final ResourceKey<PlacedFeature> FANCY_BOLDER_TREE = createKey("fancy_bolder_tree");
    public static final ResourceKey<PlacedFeature> OPAL_TREE = createKey("opal_tree");
    public static final ResourceKey<PlacedFeature> FALLEN_BOLDER_TREE = createKey("fallen_bolder_tree");
    public static final ResourceKey<PlacedFeature> FALLEN_OPAL_TREE = createKey("fallen_opal_tree");
    public static final ResourceKey<PlacedFeature> BOLDER_TREE_BEES_LEAF_LITTER = createKey("bolder_tree_bees_leaf_litter");
    public static final ResourceKey<PlacedFeature> OPAL_TREE_BEES_LEAF_LITTER = createKey("opal_tree_bees_leaf_litter");
    public static final ResourceKey<PlacedFeature> FANCY_BOLDER_TREE_BEES_LEAF_LITTER = createKey("fancy_bolder_tree_bees_leaf_litter");

    // 飘浮草原 / 繁星草原
    public static final ResourceKey<PlacedFeature> FLOATING_PLAINS_GRASS = createKey("floating_plains_grass");
    public static final ResourceKey<PlacedFeature> FLOATING_PLAINS_TALL_GRASS = createKey("floating_plains_tall_grass");
    public static final ResourceKey<PlacedFeature> FLOATING_PLAINS_FLOWERS = createKey("floating_plains_flowers");
    public static final ResourceKey<PlacedFeature> FLOATING_PLAINS_FIREFLY_BUSH = createKey("floating_plains_firefly_bush");
    public static final ResourceKey<PlacedFeature> STARLIT_PLAINS_FIREFLY_BUSH = createKey("starlit_plains_firefly_bush");
    public static final ResourceKey<PlacedFeature> STARLIT_PLAINS_GLOWSTONE = createKey("starlit_plains_glowstone");


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

        // 边境森林
        Holder<ConfiguredFeature<?, ?>> woodLandsTrees = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WOOD_LANDS_TREES);
        Holder<ConfiguredFeature<?, ?>> woodLandsInvertedBolder = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WOOD_LANDS_INVERTED_BOLDER);
        Holder<ConfiguredFeature<?, ?>> woodLandsTallInvertedBolder = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WOOD_LANDS_TALL_INVERTED_BOLDER);
        Holder<ConfiguredFeature<?, ?>> woodLandsTallBolder = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WOOD_LANDS_TALL_BOLDER);
        Holder<ConfiguredFeature<?, ?>> imaginarbor = configuredFeatures.getOrThrow(FLTConfiguredFeatures.IMAGINARBOR);
        Holder<ConfiguredFeature<?, ?>> voidviolet = configuredFeatures.getOrThrow(FLTConfiguredFeatures.VOIDVIOLET);
        Holder<ConfiguredFeature<?, ?>> woodLandsTreeOnTree = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WOOD_LANDS_TREE_ON_TREE);
        Holder<ConfiguredFeature<?, ?>> woodLandsPaeonia = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WOOD_LANDS_PAEONIA);
        Holder<ConfiguredFeature<?, ?>> woodLandsNormalTrees = configuredFeatures.getOrThrow(FLTConfiguredFeatures.WOOD_LANDS_NORMAL_TREES);
        Holder<ConfiguredFeature<?, ?>> bolderTree = configuredFeatures.getOrThrow(FLTConfiguredFeatures.BOLDER_TREE);
        Holder<ConfiguredFeature<?, ?>> fancyBolderTree = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FANCY_BOLDER_TREE);
        Holder<ConfiguredFeature<?, ?>> opalTree = configuredFeatures.getOrThrow(FLTConfiguredFeatures.OPAL_TREE);
        Holder<ConfiguredFeature<?, ?>> fallenBolderTree = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FALLEN_BOLDER_TREE);
        Holder<ConfiguredFeature<?, ?>> fallenOpalTree = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FALLEN_OPAL_TREE);
        Holder<ConfiguredFeature<?, ?>> bolderTreeBeesLeafLitter = configuredFeatures.getOrThrow(FLTConfiguredFeatures.BOLDER_TREE_BEES_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> opalTreeBeesLeafLitter = configuredFeatures.getOrThrow(FLTConfiguredFeatures.OPAL_TREE_BEES_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> fancyBolderTreeBeesLeafLitter = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FANCY_BOLDER_TREE_BEES_LEAF_LITTER);

        // 飘浮草原/繁星草原
        Holder<ConfiguredFeature<?, ?>> floatingPlainsGrass = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FLOATING_PLAINS_GRASS);
        Holder<ConfiguredFeature<?, ?>> floatingPlainsTallGrass = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FLOATING_PLAINS_TALL_GRASS);
        Holder<ConfiguredFeature<?, ?>> floatingPlainsFlowers = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FLOATING_PLAINS_FLOWERS);
        Holder<ConfiguredFeature<?, ?>> floatingPlainsFireflyBush = configuredFeatures.getOrThrow(FLTConfiguredFeatures.FLOATING_PLAINS_FIREFLY_BUSH);
        Holder<ConfiguredFeature<?, ?>> starlitPlainsGlowstone = configuredFeatures.getOrThrow(FLTConfiguredFeatures.STARLIT_PLAINS_GLOWSTONE);


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
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE),
                BiomeFilter.biome()
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
                ),
                BiomeFilter.biome()
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

        // 边境森林 --------------------------
        // 特殊树种的“裸”放置：不附加任何放置修饰器，位置完全由外层的树种选择器决定
        PlacementUtils.register(context, WOOD_LANDS_TALL_BOLDER, woodLandsTallBolder, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(context, IMAGINARBOR, imaginarbor, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(context, VOIDVIOLET, voidviolet, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(context, WOOD_LANDS_TREE_ON_TREE, woodLandsTreeOnTree, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

        // 普通树种的“裸”放置：只带树苗存活判定，与原版 oak_checked 等保持一致
        PlacementUtils.register(context, BOLDER_TREE, bolderTree, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(context, FANCY_BOLDER_TREE, fancyBolderTree, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(context, OPAL_TREE, opalTree, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
        PlacementUtils.register(context, WOOD_LANDS_NORMAL_TREES, woodLandsNormalTrees);


        // 边境森林的树：密度与原版森林一致（每区块约 10 次尝试），且不长在水里
        PlacementUtils.register(
                context,
                WOOD_LANDS_TREES,
                woodLandsTrees,
                PlacementUtils.countExtra(10, 0.1F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        // 倒树：先取地面高度，再抬高到地面 30~50 格之上生成。
        // 镜像后树干朝上、树冠朝下，所以它是垂在地面之上的一棵倒挂的树。
        PlacementUtils.register(
                context,
                WOOD_LANDS_INVERTED_BOLDER,
                woodLandsInvertedBolder,
                PlacementUtils.HEIGHTMAP,
                VerticalOffsetPlacement.of(UniformInt.of(30, 50))
        );

        // 高的倒树变种
        PlacementUtils.register(
                context,
                WOOD_LANDS_TALL_INVERTED_BOLDER,
                woodLandsTallInvertedBolder,
                PlacementUtils.HEIGHTMAP,
                VerticalOffsetPlacement.of(UniformInt.of(40, 60))
        );

        // 边境芍药：放置方式与原版森林的“大型花”地物保持一致，只把花换成边境芍药
        PlacementUtils.register(
                context,
                WOOD_LANDS_FLOWERS,
                woodLandsPaeonia,
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );

        // 倒下的苍境树/白玉树
        PlacementUtils.register(context, FALLEN_BOLDER_TREE, fallenBolderTree, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(context, FALLEN_OPAL_TREE, fallenOpalTree, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

        // 带枯叶堆的苍境树/珍异苍境树/白玉树
        PlacementUtils.register(context, BOLDER_TREE_BEES_LEAF_LITTER, bolderTreeBeesLeafLitter, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(
                context, FANCY_BOLDER_TREE_BEES_LEAF_LITTER, fancyBolderTreeBeesLeafLitter, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
        );
        PlacementUtils.register(context, OPAL_TREE_BEES_LEAF_LITTER, opalTreeBeesLeafLitter, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

        // 飘浮草原 / 繁星草原 --------------------------
        // 草：层叠效果
        PlacementUtils.register(
                context,
                FLOATING_PLAINS_GRASS,
                floatingPlainsGrass,
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                VerticalOffsetPlacement.of(UniformInt.of(0, 80)),
                CountPlacement.of(14),
                RandomOffsetPlacement.of(UniformInt.of(-3, 3), ConstantInt.of(0)),
                BiomeFilter.biome()
        );

        // 高草丛：比草稀疏，丛也小一些
        PlacementUtils.register(
                context,
                FLOATING_PLAINS_TALL_GRASS,
                floatingPlainsTallGrass,
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                VerticalOffsetPlacement.of(UniformInt.of(0, 80)),
                CountPlacement.of(4),
                RandomOffsetPlacement.of(UniformInt.of(-4, 4), ConstantInt.of(0)),
                BiomeFilter.biome()
        );

        // 花：参考占位符花地物（FLOWERS_FAR_LANDS_PLAINS）。一簇成火焰状下多上少的花簇
        PlacementUtils.register(
                context,
                FLOATING_PLAINS_FLOWERS,
                floatingPlainsFlowers,
                NoiseThresholdCountPlacement.of(-0.8, 15, 4),
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                CountPlacement.of(16),
                PlacementUtils.HEIGHTMAP,
                VerticalOffsetPlacement.of(BiasedToBottomInt.of(0, 72)),
                BiomeFilter.biome(),
                RandomOffsetPlacement.ofTriangle(6, 4),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );

        // 萤火虫灌木丛：飘浮草原里很稀有，且一次只生成一格
        PlacementUtils.register(
                context,
                FLOATING_PLAINS_FIREFLY_BUSH,
                floatingPlainsFireflyBush,
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                VerticalOffsetPlacement.of(UniformInt.of(0, 80)),
                BiomeFilter.biome()
        );

        // 繁星草原的萤火虫灌木丛：生成概率明显提高
        PlacementUtils.register(
                context,
                STARLIT_PLAINS_FIREFLY_BUSH,
                floatingPlainsFireflyBush,
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                VerticalOffsetPlacement.of(UniformInt.of(0, 80)),
                BiomeFilter.biome()
        );

        // 繁星草原的荧石：沿「旋涡」场分布。
        // 图案完全由世界坐标决定，所以螺旋臂能跨区块连续，形成流动的大漩涡（梵高《星空》那种感觉）。
        // 荧石按高斯散布在螺旋臂周围、并随机上下散布，加上低频噪声调制疏密，得到「星星点点」的星云感。
        PlacementUtils.register(
                context,
                STARLIT_PLAINS_GLOWSTONE,
                starlitPlainsGlowstone,
                CountPlacement.of(32),
                InSquarePlacement.spread(),
                new SwirlPlacement(
                        120,    // cell_size：旋涡中心间距（小于 2×max_radius，旋涡才会互相咬合）
                        2,      // arms：螺旋臂数
                        7.0,    // twist：每弧度半径增量，越小缠绕越紧
                        0.25,   // twist_jitter：每个旋涡缠绕紧度的抖动，破除规整感
                        96.0,   // max_radius
                        8.0,    // min_radius
                        1.7,    // arm_falloff：星带的高斯宽度，越大星点散得越开
                        0.9,    // fade_start：渐隐起点，太小时旋涡会彼此孤立
                        7.0,    // star_radius：旋涡中心的星核
                        VerticalAnchor.belowTop(10),   // min_height：世界顶以下 10 格
                        VerticalAnchor.belowTop(2),    // max_height：世界顶以下 2 格
                        2.0,    // wave_frequency：高度起伏
                        4,      // y_spread：垂直随机散布
                        260.0,  // density_scale：疏密噪声的波长
                        0.12,   // density_min：疏处保留的密度下限
                        1346237208L
                ),
                BiomeFilter.biome()
        );

    }
}
