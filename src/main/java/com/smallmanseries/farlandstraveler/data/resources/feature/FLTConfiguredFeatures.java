package com.smallmanseries.farlandstraveler.data.resources.feature;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.worldgen.features.FLTFeatures;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.CascadeConfiguration;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.MegaVineConfiguration;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

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
    }
}
