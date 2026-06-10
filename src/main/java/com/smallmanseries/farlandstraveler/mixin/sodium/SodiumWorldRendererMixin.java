package com.smallmanseries.farlandstraveler.mixin.sodium;

import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.smallmanseries.farlandstraveler.Config;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.SortedSet;

@Mixin(SodiumWorldRenderer.class)
public abstract class SodiumWorldRendererMixin {
    @Inject(method = "renderBlockEntity", at = @At("HEAD"), cancellable = true)
    private static void modifyRenderer(PoseStack matrices, RenderBuffers bufferBuilders, Long2ObjectMap<SortedSet<BlockDestructionProgress>> blockBreakingProgressions, float tickDelta, MultiBufferSource.BufferSource immediate, double x, double y, double z, BlockEntityRenderDispatcher dispatcher, BlockEntity entity, LocalPlayer player, LocalBooleanRef isGlowing, CallbackInfo ci) {
        int stripeLandsDistance = Config.STRIPE_LANDS_DISTANCE.getAsInt();
        if (stripeLandsDistance == -1) return;
        BlockPos pos = entity.getBlockPos();
        if (Math.abs(pos.getX()) >= stripeLandsDistance && (pos.getX() & 2) == 2 || Math.abs(pos.getZ()) >= stripeLandsDistance && (pos.getZ() & 2) == 2)
            ci.cancel();
    }
}
