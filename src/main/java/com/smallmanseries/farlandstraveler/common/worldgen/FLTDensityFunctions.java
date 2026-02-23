package com.smallmanseries.farlandstraveler.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions.VirtualDensityFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FLTDensityFunctions {

    public static final DeferredRegister<MapCodec<? extends DensityFunction>> FUNCTIONS = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, FarLandsTraveler.MODID);

    public static final Supplier<MapCodec<VirtualDensityFunction>> VIRTUAL_DENSITY_FUNCTION = FUNCTIONS.register("virtual_density_function", () -> VirtualDensityFunction.DATA_CODEC);
}
