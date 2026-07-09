package com.smallmanseries.farlandstraveler.mixin.renderer;

import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {
    @Inject(method = "tesselate", at = @At("HEAD"), cancellable = true)
    private void modifyTesselating(BlockAndTintGetter level, BlockPos pos, FluidRenderer.Output output, BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        int stripeLandsDistance = Config.STRIPE_LANDS_DISTANCE.getAsInt();
        if (stripeLandsDistance == -1) return;
        if (Math.abs(pos.getX()) >= stripeLandsDistance && (pos.getX() & 2) == 2 || Math.abs(pos.getZ()) >= stripeLandsDistance && (pos.getZ() & 2) == 2)
            ci.cancel();
    }
}
