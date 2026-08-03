package com.smallmanseries.farlandstraveler.mixin.phenomenon.fakechunk;

import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidState.class)
public abstract class FluidStateMixin {

    // 取消假区块中的流体对实体的效果
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void modifyEntityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, CallbackInfo ci) {
        if (Config.FC_DISABLE_FLUID_COLLISION.getAsBoolean() && FakeChunk.isInFakeChunk(level, pos) && FakeChunk.isEntityNotImmune(entity)) {
            ci.cancel();
        }
    }
}
