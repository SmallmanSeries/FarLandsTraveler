package com.smallmanseries.farlandstraveler.mixin.structureblock;

import net.minecraft.client.renderer.blockentity.BlockEntityWithBoundingBoxRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityWithBoundingBoxRenderer.class)
public class BlockEntityWithBoundingBoxRenderMixin {
    @Inject(method = "getViewDistance", at = @At("HEAD"), cancellable = true)
    private void viewDistance(CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(512);
    }
}
