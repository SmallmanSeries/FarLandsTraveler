package com.smallmanseries.farlandstraveler.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions.BlendedNoiseOverflowable;
import com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions.BoxSelect;
import com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions.VirtualDensityFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTDensityFunctions {

    public static final DeferredRegister<MapCodec<? extends DensityFunction>> FUNCTIONS = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, FarLandsTraveler.MODID);

    static {
        FUNCTIONS.register("virtual_density_function", () -> VirtualDensityFunction.DATA_CODEC);
        FUNCTIONS.register("old_blended_noise_overflowable", () -> BlendedNoiseOverflowable.DATA_CODEC);
        FUNCTIONS.register("box_select", () -> BoxSelect.DATA_CODEC);

    }
}
