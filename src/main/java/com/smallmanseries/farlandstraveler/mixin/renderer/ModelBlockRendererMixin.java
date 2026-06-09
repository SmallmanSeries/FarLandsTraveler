package com.smallmanseries.farlandstraveler.mixin.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;

@Mixin(ModelBlockRenderer.class)
public abstract class ModelBlockRendererMixin {
    @Inject(method = "tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Ljava/util/List;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/util/function/Function;ZI)V", at = @At("HEAD"), cancellable = true)
    private void modifyTesselating(BlockAndTintGetter p_234380_, List<BlockModelPart> p_410025_, BlockState p_234382_, BlockPos p_234383_, PoseStack p_234384_, Function<ChunkSectionLayer, VertexConsumer> bufferLookup, boolean p_234386_, int p_234389_, CallbackInfo ci) {
        int stripeLandsDistance = Config.STRIPE_LANDS_DISTANCE.getAsInt();
        if (stripeLandsDistance == -1) return;
        if (Math.abs(p_234383_.getX()) >= stripeLandsDistance && (p_234383_.getX() & 1) == 1 || Math.abs(p_234383_.getZ()) >= stripeLandsDistance && (p_234383_.getZ() & 1) == 1) ci.cancel();
    }
}
