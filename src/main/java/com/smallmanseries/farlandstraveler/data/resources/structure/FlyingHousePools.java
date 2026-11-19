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

public class FlyingHousePools {
    public static final ResourceKey<StructureTemplatePool> FLYING_HOUSE = FLTTemplatePools.createKey("flying_house");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = templatePools.getOrThrow(Pools.EMPTY);

        context.register(FLYING_HOUSE, new StructureTemplatePool(
                empty,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("farlandstraveler:flying_house"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));
    }
}
