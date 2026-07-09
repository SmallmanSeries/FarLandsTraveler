package com.smallmanseries.farlandstraveler.mixin.fakechunk;

import com.llamalad7.mixinextras.sugar.Local;
import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityFluidInteraction.class)
public abstract class EntityFluidInteractionMixin {

    @ModifyVariable(method = "update", at = @At("STORE"), name = "fluidState")
    private FluidState modifyFluid(FluidState fluidState, @Local(argsOnly = true, name = "entity") Entity entity, @Local(name = "level") BlockGetter level, @Local(name = "mutablePos") BlockPos.MutableBlockPos mutablePos) {
        if (level instanceof Level && FakeChunk.isInFakeChunk((Level) level, mutablePos) && FakeChunk.isEntityNotImmune(entity)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        return fluidState;
    }
}
