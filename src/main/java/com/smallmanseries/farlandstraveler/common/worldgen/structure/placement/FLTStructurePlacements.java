package com.smallmanseries.farlandstraveler.common.worldgen.structure.placement;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTStructurePlacements {
    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENTS = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, FarLandsTraveler.MODID);

    public static final DeferredHolder<StructurePlacementType<?>, StructurePlacementType<FixedStructurePlacement>> FIXED = STRUCTURE_PLACEMENTS.register("fixed", () -> () -> FixedStructurePlacement.CODEC);
}
