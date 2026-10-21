package com.smallmanseries.farlandstraveler.data.resources.biome;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class FLTCarvers {
    public static final ResourceKey<ConfiguredWorldCarver<?>> FAR_LANDS_CAVE = createKey("far_lands_cave");
    public static final ResourceKey<ConfiguredWorldCarver<?>> FAR_LANDS_CAVE_EXTRA = createKey("far_lands_cave_extra");
    public static final ResourceKey<ConfiguredWorldCarver<?>> FAR_LANDS_CANYON = createKey("far_lands_canyon");

    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        context.register(FAR_LANDS_CAVE,
                WorldCarver.CAVE
                        .configured(new CaveCarverConfiguration(
                                0.15F,
                                UniformHeight.of(
                                        VerticalAnchor.aboveBottom(8),
                                        VerticalAnchor.absolute(180)
                                ),
                                UniformFloat.of(0.1F, 0.9F),
                                VerticalAnchor.aboveBottom(-1),
                                CarverDebugSettings.of(
                                        false,
                                        Blocks.CRIMSON_BUTTON.defaultBlockState()
                                ),
                                blocks.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                                UniformFloat.of(0.7F, 1.4F),
                                UniformFloat.of(0.8F, 1.3F),
                                UniformFloat.of(-1.0F, -0.4F)
                        )));
        context.register(FAR_LANDS_CAVE_EXTRA,
                WorldCarver.CAVE
                        .configured(new CaveCarverConfiguration(
                                0.07F,
                                UniformHeight.of(
                                        VerticalAnchor.aboveBottom(8),
                                        VerticalAnchor.absolute(47)
                                ),
                                UniformFloat.of(0.1F, 0.9F),
                                VerticalAnchor.aboveBottom(-1),
                                CarverDebugSettings.of(
                                        false,
                                        Blocks.OAK_BUTTON.defaultBlockState()
                                ),
                                blocks.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                                UniformFloat.of(0.7F, 1.4F),
                                UniformFloat.of(0.8F, 1.3F),
                                UniformFloat.of(-1.0F, -0.4F)
                        )));
        context.register(FAR_LANDS_CANYON,
                WorldCarver.CANYON
                        .configured(new CanyonCarverConfiguration(
                                0.01F,
                                UniformHeight.of(
                                        VerticalAnchor.absolute(10),
                                        VerticalAnchor.absolute(67)
                                ), ConstantFloat.of(3.0F),
                                VerticalAnchor.aboveBottom(-1),
                                CarverDebugSettings.of(
                                        false,
                                        Blocks.WARPED_BUTTON.defaultBlockState()),
                                blocks.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                                UniformFloat.of(-0.125F, 0.125F),
                                new CanyonCarverConfiguration.CanyonShapeConfiguration(
                                        UniformFloat.of(0.75F, 1.0F),
                                        TrapezoidFloat.of(0.0F, 6.0F, 2.0F),
                                        3,
                                        UniformFloat.of(0.75F, 1.0F),
                                        1.0F,
                                        0.0F)
                        )));
    }
}
