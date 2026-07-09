package com.smallmanseries.farlandstraveler.mixin.sodium;
/*
import com.smallmanseries.farlandstraveler.Config;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.caffeinemc.mods.sodium.neoforge.render.FluidRendererImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidRendererImpl.class)
public abstract class FluidRendererImplMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void modifyRenderer(LevelSlice level, BlockState blockState, FluidState fluidState, BlockPos blockPos, BlockPos offset, TranslucentGeometryCollector collector, ChunkBuildBuffers buffers, CallbackInfo ci) {
        int stripeLandsDistance = Config.STRIPE_LANDS_DISTANCE.getAsInt();
        if (stripeLandsDistance == -1) return;
        if (Math.abs(blockPos.getX()) >= stripeLandsDistance && (blockPos.getX() & 2) == 2 || Math.abs(blockPos.getZ()) >= stripeLandsDistance && (blockPos.getZ() & 2) == 2)
            ci.cancel();
    }
}


 */