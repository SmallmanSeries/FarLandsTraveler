package com.smallmanseries.farlandstraveler.mixin.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRendererDispatcherMixin<E extends BlockEntity> {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void modifyRenderer(E blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        int stripeLandsDistance = Config.STRIPE_LANDS_DISTANCE.getAsInt();
        if (stripeLandsDistance == -1) return;
        BlockPos pos = blockEntity.getBlockPos();
        if (Math.abs(pos.getX()) >= stripeLandsDistance && (pos.getX() & 2) == 2 || Math.abs(pos.getZ()) >= stripeLandsDistance && (pos.getZ() & 2) == 2)
            ci.cancel();
    }
}
