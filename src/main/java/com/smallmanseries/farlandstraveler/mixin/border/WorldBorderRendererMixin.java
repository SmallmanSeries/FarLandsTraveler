package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.client.renderer.WorldBorderRenderer;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldBorderRenderer.class)
public abstract class WorldBorderRendererMixin {
    // 取消世界边界渲染
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(WorldBorder worldBorder, Vec3 cameraPosition, double renderDistance, double farPlaneDepth, CallbackInfo ci) {
        ci.cancel();
    }
}
