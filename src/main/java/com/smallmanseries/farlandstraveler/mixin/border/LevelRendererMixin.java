package com.smallmanseries.farlandstraveler.mixin.border;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.WorldBorderRenderer;
import net.minecraft.client.renderer.state.level.WorldBorderRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    // 取消世界边界渲染
    @WrapWithCondition(method = "lambda$addWeatherPass$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldBorderRenderer;render(Lnet/minecraft/client/renderer/state/level/WorldBorderRenderState;Lnet/minecraft/world/phys/Vec3;DD)V"))
    private boolean disableWorldBorder(WorldBorderRenderer instance, WorldBorderRenderState state, Vec3 cameraPos, double renderDistance, double depthFar){
        return !Config.REMOVE_WORLD_BORDER.getAsBoolean();
    }
}
