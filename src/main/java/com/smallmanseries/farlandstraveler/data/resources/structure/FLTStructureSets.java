package com.smallmanseries.farlandstraveler.data.resources.structure;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.placement.FixedStructurePlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

public class FLTStructureSets {

    // 岩石之令实验室
    public static final ResourceKey<StructureSet> OOTSLAB_EXTERIOR = createKey("oots_laboratory/exterior");
    public static final ResourceKey<StructureSet> OOTSLAB_GATE = createKey("oots_laboratory/gate");
    public static final ResourceKey<StructureSet> OOTSLAB_MAZE = createKey("oots_laboratory/maze");
    public static final ResourceKey<StructureSet> OOTSLAB_COTTAGE = createKey("oots_laboratory/cottage");
    public static final ResourceKey<StructureSet> OOTSLAB_BASEMENT = createKey("oots_laboratory/basement");

    // 边境之地常规结构
    public static final ResourceKey<StructureSet> ABANDONED_VILLAGES = createKey("abandoned_villages");
    public static final ResourceKey<StructureSet> EXPLORER_BASES_SURFACE = createKey("explorer_bases_surface");
    public static final ResourceKey<StructureSet> EXPLORER_BASES_UNDERGROUND = createKey("explorer_bases_underground");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
    }

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        context.register(OOTSLAB_EXTERIOR, new StructureSet(
                List.of(StructureSet.entry(structures.getOrThrow(FLTStructureData.OOTSLAB_EXTERIOR))),
                new FixedStructurePlacement(1346237208, -12550829, -12550788)
        ));

        context.register(OOTSLAB_GATE, new StructureSet(
                List.of(StructureSet.entry(structures.getOrThrow(FLTStructureData.OOTSLAB_GATE))),
                new FixedStructurePlacement(1346237208, -12550838, -12550788)
        ));

        context.register(OOTSLAB_MAZE, new StructureSet(
                List.of(StructureSet.entry(structures.getOrThrow(FLTStructureData.OOTSLAB_MAZE))),
                new FixedStructurePlacement(1346237208, -12550974, -12550764)
        ));

        context.register(OOTSLAB_COTTAGE, new StructureSet(
                List.of(StructureSet.entry(structures.getOrThrow(FLTStructureData.OOTSLAB_COTTAGE))),
                new FixedStructurePlacement(1346237208, -12551187, -12550664)
        ));

        context.register(OOTSLAB_BASEMENT, new StructureSet(
                List.of(StructureSet.entry(structures.getOrThrow(FLTStructureData.OOTSLAB_BASEMENT))),
                new FixedStructurePlacement(1346237208, -12551320, -12550737)
        ));

        context.register(ABANDONED_VILLAGES, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(FLTStructureData.AV_NORMAL))
                ),
                new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 75368186)
        ));

        context.register(EXPLORER_BASES_SURFACE, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(FLTStructureData.EB_INDEV_WOOD))
                ),
                new RandomSpreadStructurePlacement(40, 10, RandomSpreadType.LINEAR, 12550820)
        ));

        context.register(EXPLORER_BASES_UNDERGROUND, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(FLTStructureData.EB_INDEV_COBBLESTONE))
                ),
                new RandomSpreadStructurePlacement(40, 10, RandomSpreadType.LINEAR, 12550824)
        ));
    }
}
