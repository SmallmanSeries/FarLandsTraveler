package com.smallmanseries.farlandstraveler.mixin.renderer;

import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRendererDispatcherMixin<E extends BlockEntity, S extends BlockEntityRenderState> {
    @Inject(method = "tryExtractRenderState(Lnet/minecraft/world/level/block/entity/BlockEntity;FLnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;Lnet/minecraft/client/renderer/culling/Frustum;)Lnet/minecraft/client/renderer/blockentity/state/BlockEntityRenderState;", at = @At("HEAD"), cancellable = true)
    private void modifyRenderer(E blockEntity, float partialTicks, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress, @Nullable Frustum frustum, CallbackInfoReturnable<S> cir) {
        int stripeLandsDistance = Config.STRIPE_LANDS_DISTANCE.getAsInt();
        if (stripeLandsDistance == -1) return;
        BlockPos pos = blockEntity.getBlockPos();
        if (Math.abs(pos.getX()) >= stripeLandsDistance && (pos.getX() & 2) == 2 || Math.abs(pos.getZ()) >= stripeLandsDistance && (pos.getZ() & 2) == 2)
            cir.setReturnValue(null);
    }
}
