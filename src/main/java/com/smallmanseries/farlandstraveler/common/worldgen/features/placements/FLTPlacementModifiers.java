package com.smallmanseries.farlandstraveler.common.worldgen.features.placements;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, FarLandsTraveler.MODID);

    // 垂直偏移
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<VerticalOffsetPlacement>> VERTICAL_OFFSET =
            PLACEMENT_MODIFIER_TYPES.register("vertical_offset", () -> () -> VerticalOffsetPlacement.CODEC);
}
