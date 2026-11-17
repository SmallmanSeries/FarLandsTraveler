package com.smallmanseries.farlandstraveler.data.resources.feature;

import com.google.common.collect.ImmutableList;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.worldgen.features.FLTFeatures;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.CascadeConfiguration;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.MegaVineConfiguration;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.StackedTreeConfiguration;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.TransformedConfiguration;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.OptionalInt;

public class FLTConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_FAR_LANDS_PLAINS = createKey("flower_far_lands_plains");

    // 边境之地边缘装饰
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_OF_BLOOM_BUSH = createKey("wall_of_bloom_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_OF_BLOOM_CHERRY_CASCADE = createKey("wall_of_bloom_cherry_cascade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_OF_GREEN_BUSH = createKey("wall_of_green_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_OF_GREEN_MEGA_VINE = createKey("wall_of_green_mega_vine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_OF_GREEN_MOSS = createKey("wall_of_green_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_OF_GREEN_TREES = createKey("wall_of_green_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_OF_ICE_ICICLE = createKey("wall_of_ice_icicle");

    // 边境森林
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOOD_LANDS_PAEONIA = createKey("wood_lands_paeonia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOOD_LANDS_TALL_BOLDER = createKey("wood_lands_tall_bolder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IMAGINARBOR = createKey("imaginarbor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOIDVIOLET = createKey("voidviolet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOOD_LANDS_INVERTED_BOLDER = createKey("wood_lands_inverted_bolder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOOD_LANDS_TALL_INVERTED_BOLDER = createKey("wood_lands_tall_inverted_bolder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOOD_LANDS_TREE_ON_TREE = createKey("wood_lands_tree_on_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOOD_LANDS_TREES = createKey("wood_lands_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOOD_LANDS_NORMAL_TREES = createKey("wood_lands_normal_trees");
    public static final ResourceKey<ConfiguredFeature<?,?>> FALLEN_BOLDER_TREE = createKey("fallen_bolder_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> FALLEN_OPAL_TREE = createKey("fallen_opal_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> BOLDER_TREE_BEES_LEAF_LITTER = createKey("bolder_tree_bees_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?,?>> OPAL_TREE_BEES_LEAF_LITTER = createKey("opal_tree_bees_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?,?>> FANCY_BOLDER_TREE_BEES_LEAF_LITTER = createKey("fancy_bolder_tree_bees_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?,?>> BOLDER_TREE = createKey("bolder_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> FANCY_BOLDER_TREE = createKey("fancy_bolder_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> OPAL_TREE = createKey("opal_tree");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest naturalStone = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        Holder<PlacedFeature> fancyOakChecked = placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_CHECKED);
        Holder<PlacedFeature> wallOfGreenBush = placedFeatures.getOrThrow(FLTPlacedFeatures.WALL_OF_GREEN_BUSH);
        Holder<PlacedFeature> fallenJungle = placedFeatures.getOrThrow(TreePlacements.FALLEN_JUNGLE_TREE);
        Holder<PlacedFeature> jungleTreeChecked = placedFeatures.getOrThrow(TreePlacements.JUNGLE_TREE_CHECKED);

        // 边境森林
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> oak = configuredFeatures.getOrThrow(BOLDER_TREE);
        Holder<ConfiguredFeature<?, ?>> tallOak = configuredFeatures.getOrThrow(WOOD_LANDS_TALL_BOLDER);
        Holder<PlacedFeature> normalWoodLandsTrees = placedFeatures.getOrThrow(FLTPlacedFeatures.WOOD_LANDS_NORMAL_TREES);
        Holder<PlacedFeature> tallBolder = placedFeatures.getOrThrow(FLTPlacedFeatures.WOOD_LANDS_TALL_BOLDER);
        Holder<PlacedFeature> invertedBolder = placedFeatures.getOrThrow(FLTPlacedFeatures.WOOD_LANDS_INVERTED_BOLDER);
        Holder<PlacedFeature> tallInvertedBolder = placedFeatures.getOrThrow(FLTPlacedFeatures.WOOD_LANDS_TALL_INVERTED_BOLDER);
        Holder<PlacedFeature> imaginarbor = placedFeatures.getOrThrow(FLTPlacedFeatures.IMAGINARBOR);
        Holder<PlacedFeature> voidviolet = placedFeatures.getOrThrow(FLTPlacedFeatures.VOIDVIOLET);
        Holder<PlacedFeature> treeOnTree = placedFeatures.getOrThrow(FLTPlacedFeatures.WOOD_LANDS_TREE_ON_TREE);
        Holder<PlacedFeature> fallenBolderTree = placedFeatures.getOrThrow(FLTPlacedFeatures.FALLEN_BOLDER_TREE);
        Holder<PlacedFeature> fallenOpalTree = placedFeatures.getOrThrow(FLTPlacedFeatures.FALLEN_OPAL_TREE);
        Holder<PlacedFeature> bolderTreeBeesLeafLitter = placedFeatures.getOrThrow(FLTPlacedFeatures.BOLDER_TREE_BEES_LEAF_LITTER);
        Holder<PlacedFeature> opalTreeBeesLeafLitter = placedFeatures.getOrThrow(FLTPlacedFeatures.OPAL_TREE_BEES_LEAF_LITTER);
        Holder<PlacedFeature> fancyBolderTreeBeesLeafLitter = placedFeatures.getOrThrow(FLTPlacedFeatures.FANCY_BOLDER_TREE_BEES_LEAF_LITTER);

        // 装饰器
        BeehiveDecorator beehive0002 = new BeehiveDecorator(0.002F);
        PlaceOnGroundDecorator sparseLeafLitter = new PlaceOnGroundDecorator(
                96, 4, 2, new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 3))
        );
        PlaceOnGroundDecorator thickLeafLitter = new PlaceOnGroundDecorator(
                150, 2, 2, new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 4))
        );

        FeatureUtils.register(
                context,
                FLOWER_FAR_LANDS_PLAINS,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        new NoiseThresholdProvider(
                                1346237208L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.005F,
                                -0.8F,
                                0.33333334F,
                                FLTBlocks.FAR_LANDS_ROSE.get().defaultBlockState(),
                                List.of(
                                        Blocks.ORANGE_TULIP.defaultBlockState(),
                                        Blocks.RED_TULIP.defaultBlockState(),
                                        Blocks.PINK_TULIP.defaultBlockState(),
                                        Blocks.WHITE_TULIP.defaultBlockState()
                                ),
                                List.of(
                                        FLTBlocks.FAR_LANDS_ROSE.get().defaultBlockState(),
                                        FLTBlocks.CYAN_FAR_LANDS_ROSE.get().defaultBlockState(),
                                        Blocks.OXEYE_DAISY.defaultBlockState(), // 百变菊
                                        Blocks.CORNFLOWER.defaultBlockState()
                                )
                        )
                )
        );

        FeatureUtils.register(
                context,
                WALL_OF_BLOOM_BUSH,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.OAK_LOG),
                        new StraightTrunkPlacer(1, 0, 0),
                        BlockStateProvider.simple(Blocks.OAK_LEAVES),
                        new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                        new TwoLayersFeatureSize(0, 0, 0)
                ).build()
        );

        FeatureUtils.register(
                context,
                WALL_OF_BLOOM_CHERRY_CASCADE,
                FLTFeatures.CASCADE.get(),
                new CascadeConfiguration(
                        Blocks.CHERRY_LOG.defaultBlockState(),
                        Blocks.CHERRY_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true).setValue(LeavesBlock.WATERLOGGED, false),
                        UniformFloat.of(2.5F, 3.2F),
                        UniformFloat.of(0.14F, 0.17F),
                        BlockPredicate.matchesTag(BlockTags.SUBSTRATE_OVERWORLD)
                )
        );

        FeatureUtils.register(
                context,
                WALL_OF_GREEN_BUSH,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.JUNGLE_LOG),
                        new StraightTrunkPlacer(1, 0, 0),
                        BlockStateProvider.simple(Blocks.OAK_LEAVES),
                        new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                        new TwoLayersFeatureSize(0, 0, 0)
                ).decorators(List.of(
                        new LeaveVineDecorator(0.25F)
                )).build()
        );

        FeatureUtils.register(
                context,
                WALL_OF_GREEN_MEGA_VINE,
                FLTFeatures.MEGA_VINE.get(),
                new MegaVineConfiguration(
                        Blocks.JUNGLE_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true).setValue(LeavesBlock.WATERLOGGED, false),
                        BlockPredicate.matchesTag(BlockTags.SUBSTRATE_OVERWORLD)
                )
        );

        FeatureUtils.register(
                context,
                WALL_OF_GREEN_MOSS,
                Feature.ORE,
                new OreConfiguration(
                        naturalStone,
                        Blocks.MOSSY_COBBLESTONE.defaultBlockState(),
                        64
                )
        );

        FeatureUtils.register(
                context,
                WALL_OF_GREEN_TREES,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(fancyOakChecked, 0.1F),
                                new WeightedPlacedFeature(wallOfGreenBush, 0.64F),
                                new WeightedPlacedFeature(fallenJungle, 0.0125F)
                        ),
                        jungleTreeChecked
                )
        );

        FeatureUtils.register(
                context,
                WALL_OF_ICE_ICICLE,
                Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(
                        List.of(
                                BlockColumnConfiguration.layer(
                                        new WeightedListInt(
                                                WeightedList.<IntProvider>builder()
                                                        .add(UniformInt.of(1, 4), 8)
                                                        .add(UniformInt.of(4, 6), 2)
                                                        .build()
                                        ),
                                        SimpleStateProvider.simple(Blocks.ICE)
                                )
                        ),
                        Direction.DOWN,
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        true
                )
        );

        // 高树
        FeatureUtils.register(
                context,
                WOOD_LANDS_TALL_BOLDER,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.OAK_LOG),
                        new StraightTrunkPlacer(17, 5, 0),
                        BlockStateProvider.simple(Blocks.OAK_LEAVES),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build()
        );

        // 虚树
        FeatureUtils.register(
                context,
                IMAGINARBOR,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.BROWN_STAINED_GLASS),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(Blocks.GREEN_STAINED_GLASS),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build()
        );

        // 玄紫树
        FeatureUtils.register(
                context,
                VOIDVIOLET,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(FLTBlocks.INVALID_BLOCK.get()),
                        new StraightTrunkPlacer(5, 2, 1),
                        BlockStateProvider.simple(FLTBlocks.INVALID_BLOCK.get()),
                        new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)),
                        new TwoLayersFeatureSize(2, 0, 2)
                ).build()
        );

        // 倒树
        FeatureUtils.register(
                context,
                WOOD_LANDS_INVERTED_BOLDER,
                FLTFeatures.TRANSFORMED.get(),
                new TransformedConfiguration(oak, false, true, false, 0)
        );

        // 高倒树
        FeatureUtils.register(
                context,
                WOOD_LANDS_TALL_INVERTED_BOLDER,
                FLTFeatures.TRANSFORMED.get(),
                new TransformedConfiguration(tallOak, false, true, false, 0)
        );

        // 树上树
        FeatureUtils.register(
                context,
                WOOD_LANDS_TREE_ON_TREE,
                FLTFeatures.STACKED_TREE.get(),
                new StackedTreeConfiguration(oak, oak, 48)
        );

        // 边境森林小型花（边境芍药等）
        FeatureUtils.register(
                context,
                WOOD_LANDS_PAEONIA,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(FLTBlocks.FAR_LANDS_PAEONIA.get()))
        );

        // 边境森林的普通树种：苍境树、珍异苍境树、白玉树，生成参考原版森林。
        FeatureUtils.register(
                context,
                WOOD_LANDS_NORMAL_TREES,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(fallenOpalTree, 0.0025F),
                                new WeightedPlacedFeature(opalTreeBeesLeafLitter, 0.2F),
                                new WeightedPlacedFeature(fancyBolderTreeBeesLeafLitter, 0.1F),
                                new WeightedPlacedFeature(fallenBolderTree, 0.0125F)
                        ),
                        bolderTreeBeesLeafLitter
                )
        );

        // 边境森林的树种选择：默认是普通树种，另外有小概率生成专属树种。
        // 权重按顺序依次判定，所以最终出现概率约为各项之和；玄紫树的权重最小。
        FeatureUtils.register(
                context,
                WOOD_LANDS_TREES,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(tallBolder, 0.01F),
                                new WeightedPlacedFeature(imaginarbor, 0.01F),
                                new WeightedPlacedFeature(treeOnTree, 0.04F),
                                new WeightedPlacedFeature(voidviolet, 0.005F),
                                new WeightedPlacedFeature(invertedBolder, 0.004F),
                                new WeightedPlacedFeature(tallInvertedBolder, 0.001F)
                        ),
                        normalWoodLandsTrees
                )
        );

        FeatureUtils.register(context, FALLEN_BOLDER_TREE, Feature.FALLEN_TREE,
                createFallenTrees(
                        Blocks.OAK_LOG,
                        4,
                        7)
                        .stumpDecorators(
                                ImmutableList.of(
                                        TrunkVineDecorator.INSTANCE)
                        ).build()
        );
        FeatureUtils.register(context, FALLEN_OPAL_TREE, Feature.FALLEN_TREE, createFallenTrees(
                Blocks.BIRCH_LOG,
                5,
                8)
                .build()
        );

        // 未装饰的苍境树/珍异苍境树/白玉树
        FeatureUtils.register(
                context, BOLDER_TREE, Feature.TREE, createStraightBlobTree(
                        Blocks.OAK_LOG,
                        Blocks.OAK_LEAVES,
                        4,
                        2,
                        0,
                        2
                ).ignoreVines().build()
        );
        FeatureUtils.register(
                context,
                FANCY_BOLDER_TREE,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.OAK_LOG),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(Blocks.OAK_LEAVES),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                )
                        .ignoreVines().build()
        );
        FeatureUtils.register(
                context, OPAL_TREE, Feature.TREE, createStraightBlobTree(
                        Blocks.BIRCH_LOG,
                        Blocks.BIRCH_LEAVES,
                        5,
                        2,
                        0,
                        2
                ).ignoreVines().build()
        );

        // 已装饰的苍境树/珍异苍境树/白玉树
        FeatureUtils.register(
                context, BOLDER_TREE_BEES_LEAF_LITTER, Feature.TREE, createStraightBlobTree(
                        Blocks.OAK_LOG,
                        Blocks.OAK_LEAVES,
                        4,
                        2,
                        0,
                        2
                ).ignoreVines().decorators(List.of(beehive0002, sparseLeafLitter, thickLeafLitter)).build()
        );
        FeatureUtils.register(
                context,
                FANCY_BOLDER_TREE_BEES_LEAF_LITTER,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                                BlockStateProvider.simple(Blocks.OAK_LOG),
                                new FancyTrunkPlacer(3, 11, 0),
                                BlockStateProvider.simple(Blocks.OAK_LEAVES),
                                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                        )
                        .ignoreVines().decorators(List.of(beehive0002, sparseLeafLitter, thickLeafLitter)).build()
        );
        FeatureUtils.register(
                context, OPAL_TREE_BEES_LEAF_LITTER, Feature.TREE, createStraightBlobTree(
                        Blocks.BIRCH_LOG,
                        Blocks.BIRCH_LEAVES,
                        5,
                        2,
                        0,
                        2
                ).ignoreVines().decorators(List.of(beehive0002, sparseLeafLitter, thickLeafLitter)).build()
        );

    }

    private static FallenTreeConfiguration.FallenTreeConfigurationBuilder createFallenTrees(Block logBlock, int minLength, int maxLength) {
        return new FallenTreeConfiguration.FallenTreeConfigurationBuilder(BlockStateProvider.simple(logBlock), UniformInt.of(minLength, maxLength))
                .logDecorators(
                        ImmutableList.of(
                                new AttachedToLogsDecorator(
                                        0.1F,
                                        new WeightedStateProvider(
                                                WeightedList.<BlockState>builder()
                                                        .add(Blocks.RED_MUSHROOM.defaultBlockState(), 2)
                                                        .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 1)
                                        ),
                                        List.of(Direction.UP)
                                )
                        )
                );
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(
            Block oakLog, Block oakLeaves, int baseHeight, int heightRandA, int heightRandB, int blobRadius
    ) {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(oakLog),
                new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB),
                BlockStateProvider.simple(oakLeaves),
                new BlobFoliagePlacer(ConstantInt.of(blobRadius), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        );
    }
}
