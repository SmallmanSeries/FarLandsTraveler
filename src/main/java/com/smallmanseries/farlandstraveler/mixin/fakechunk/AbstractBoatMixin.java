package com.smallmanseries.farlandstraveler.mixin.fakechunk;

import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractBoat.class)
public class AbstractBoatMixin {
    @Redirect(method = {"getWaterLevelAbove", "checkInWater", "isUnderWater", "checkFallDamage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"))
    private FluidState modifyFluid(Level level, BlockPos pos) {
        if (FakeChunk.isInFakeChunk(level, pos)) { // Todo 模组新增免疫去固体效应的船？
            return Fluids.EMPTY.defaultFluidState();
        }
        return level.getFluidState(pos);
    }
}
