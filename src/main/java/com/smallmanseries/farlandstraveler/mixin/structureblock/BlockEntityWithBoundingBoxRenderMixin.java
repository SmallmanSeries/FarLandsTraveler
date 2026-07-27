package com.smallmanseries.farlandstraveler.mixin.structureblock;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.blockentity.BlockEntityWithBoundingBoxRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityWithBoundingBoxRenderer.class)
public class BlockEntityWithBoundingBoxRenderMixin {
    @ModifyReturnValue(method = "getViewDistance", at = @At("RETURN"))
    private int viewDistance(int original){
        return 512;
    }
}
