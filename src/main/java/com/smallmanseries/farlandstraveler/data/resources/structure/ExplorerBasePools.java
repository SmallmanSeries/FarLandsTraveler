package com.smallmanseries.farlandstraveler.data.resources.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ExplorerBasePools {

    public static final ResourceKey<StructureTemplatePool> INDEV_COBBLESTONE = FLTTemplatePools.createKey("explorer_base/indev_cobblestone");
    public static final ResourceKey<StructureTemplatePool> INDEV_WOOD = FLTTemplatePools.createKey("explorer_base/indev_wood");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = templatePools.getOrThrow(Pools.EMPTY);

        context.register(INDEV_COBBLESTONE, new StructureTemplatePool(
                empty,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("farlandstraveler:explorer_base/indev_cobblestone"), 8),
                        Pair.of(StructurePoolElement.single("farlandstraveler:explorer_base/indev_cobblestone_3chests_empty"), 4),
                        Pair.of(StructurePoolElement.single("farlandstraveler:explorer_base/indev_cobblestone_3chests"), 4),
                        Pair.of(StructurePoolElement.single("farlandstraveler:explorer_base/indev_cobblestone_5chests"), 2),
                        Pair.of(StructurePoolElement.single("farlandstraveler:explorer_base/indev_cobblestone_6chests"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));

        context.register(INDEV_WOOD, new StructureTemplatePool(
                empty,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("farlandstraveler:explorer_base/indev_wood"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));
    }
}
