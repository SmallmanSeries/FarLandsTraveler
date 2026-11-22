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
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<OffsetPlacement>> OFFSET =
            PLACEMENT_MODIFIER_TYPES.register("offset", () -> () -> OffsetPlacement.CODEC);

    // 旋涡：按世界坐标上的螺旋场筛选位置，用于「繁星」
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<SwirlPlacement>> SWIRL =
            PLACEMENT_MODIFIER_TYPES.register("swirl", () -> () -> SwirlPlacement.CODEC);

    // 每一列：把区块的 256 列全部按高度图取出来，用于「边境花田」的花毯
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<EveryColumnPlacement>> EVERY_COLUMN =
            PLACEMENT_MODIFIER_TYPES.register("every_column", () -> () -> EveryColumnPlacement.CODEC);
}
