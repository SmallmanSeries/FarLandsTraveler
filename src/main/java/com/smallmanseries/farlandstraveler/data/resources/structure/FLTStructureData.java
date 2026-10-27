package com.smallmanseries.farlandstraveler.data.resources.structure;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import com.smallmanseries.farlandstraveler.common.worldgen.FLTBiomes;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.OOTSLaboratoryStructure;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.abandonedvillage.AbandonedVillagePreset;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.abandonedvillage.AbandonedVillageStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.List;
import java.util.Optional;

public class FLTStructureData {

    // 探境者基地
    public static final ResourceKey<Structure> EB_INDEV_COBBLESTONE = createKey("explorer_base/indev_cobblestone");
    public static final ResourceKey<Structure> EB_INDEV_WOOD = createKey("explorer_base/indev_wood");

    // 岩石之令实验室部件
    public static final ResourceKey<Structure> OOTSLAB_EXTERIOR = createKey("oots_laboratory/exterior");
    public static final ResourceKey<Structure> OOTSLAB_GATE = createKey("oots_laboratory/gate");
    public static final ResourceKey<Structure> OOTSLAB_MAZE = createKey("oots_laboratory/maze");
    public static final ResourceKey<Structure> OOTSLAB_COTTAGE = createKey("oots_laboratory/cottage");
    public static final ResourceKey<Structure> OOTSLAB_BASEMENT = createKey("oots_laboratory/basement");

    // 废弃村庄
    public static final ResourceKey<Structure> AV_NORMAL = createKey("abandoned_village/normal");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
    }

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templates = context.lookup(Registries.TEMPLATE_POOL);

        context.register(
                EB_INDEV_COBBLESTONE,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(biomes.getOrThrow(FLTTags.Biomes.HAS_STRUCTURE_EB_INDEV_COBBLESTONE))
                                .generationStep(GenerationStep.Decoration.UNDERGROUND_STRUCTURES)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        templates.getOrThrow(ExplorerBasePools.INDEV_COBBLESTONE),
                        Optional.of(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "base_anchor")),
                        1,
                        BiasedToBottomHeight.of(VerticalAnchor.absolute(64), VerticalAnchor.absolute(192), 1),
                        true,
                        Optional.empty(),
                        new JigsawStructure.MaxDistance(6),
                        List.of(),
                        JigsawStructure.DEFAULT_DIMENSION_PADDING,
                        JigsawStructure.DEFAULT_LIQUID_SETTINGS
                )
        );

        context.register(
                EB_INDEV_WOOD,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(biomes.getOrThrow(FLTTags.Biomes.HAS_STRUCTURE_EB_INDEV_WOOD))
                                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        templates.getOrThrow(ExplorerBasePools.INDEV_WOOD),
                        Optional.of(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "base_anchor")),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        true,
                        Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                        new JigsawStructure.MaxDistance(6),
                        List.of(),
                        JigsawStructure.DEFAULT_DIMENSION_PADDING,
                        JigsawStructure.DEFAULT_LIQUID_SETTINGS
                )
        );

        context.register(
                OOTSLAB_EXTERIOR,
                new OOTSLaboratoryStructure(
                        new Structure.StructureSettings.Builder(HolderSet.direct(biomes.getOrThrow(FLTBiomes.OOTS_LABORATORY)))
                                .generationStep(GenerationStep.Decoration.VEGETAL_DECORATION)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        new BlockPos(-12550829, 62, -12550788),
                        "exterior"
                )
        );

        context.register(
                OOTSLAB_GATE,
                new OOTSLaboratoryStructure(
                        new Structure.StructureSettings.Builder(HolderSet.direct(biomes.getOrThrow(FLTBiomes.OOTS_LABORATORY)))
                                .generationStep(GenerationStep.Decoration.VEGETAL_DECORATION)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        new BlockPos(-12550838, 62, -12550788),
                        "gate"
                )
        );

        context.register(
                OOTSLAB_MAZE,
                new OOTSLaboratoryStructure(
                        new Structure.StructureSettings.Builder(HolderSet.direct(biomes.getOrThrow(FLTBiomes.OOTS_LABORATORY)))
                                .generationStep(GenerationStep.Decoration.VEGETAL_DECORATION)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        new BlockPos(-12550974, 51, -12550764),
                        "maze"
                )
        );

        context.register(
                OOTSLAB_COTTAGE,
                new OOTSLaboratoryStructure(
                        new Structure.StructureSettings.Builder(HolderSet.direct(biomes.getOrThrow(FLTBiomes.OOTS_LABORATORY)))
                                .generationStep(GenerationStep.Decoration.VEGETAL_DECORATION)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        new BlockPos(-12551187, 43, -12550664),
                        "cottage"
                )
        );

        context.register(
                OOTSLAB_BASEMENT,
                new OOTSLaboratoryStructure(
                        new Structure.StructureSettings.Builder(HolderSet.direct(biomes.getOrThrow(FLTBiomes.OOTS_LABORATORY)))
                                .generationStep(GenerationStep.Decoration.VEGETAL_DECORATION)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        new BlockPos(-12551320, 33, -12550737),
                        "basement"
                )
        );

        context.register(
                AV_NORMAL,
                new AbandonedVillageStructure(
                        new Structure.StructureSettings.Builder(biomes.getOrThrow(FLTTags.Biomes.HAS_STRUCTURE_AV_NORMAL))
                                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                                .terrainAdapation(TerrainAdjustment.NONE)
                                .build(),
                        6,
                        AbandonedVillagePreset.createDefault()
                )
        );
    }
}
