package com.smallmanseries.farlandstraveler.mixin.border;

import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.client.renderer.WorldBorderRenderer;
import net.minecraft.client.renderer.state.level.WorldBorderRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldBorderRenderer.class)
public abstract class WorldBorderRendererMixin {
    // 取消世界边界渲染
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(WorldBorderRenderState state, Vec3 cameraPos, double renderDistance, double depthFar, CallbackInfo ci) {
        if (Config.REMOVE_WORLD_BORDER.getAsBoolean()) {
            ci.cancel();
        }
    }
}
