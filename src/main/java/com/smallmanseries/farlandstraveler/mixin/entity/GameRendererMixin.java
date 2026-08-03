package com.smallmanseries.farlandstraveler.mixin.entity;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.smallmanseries.farlandstraveler.common.entity.PrimitiveEndermanEntity;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    // 设定上末影人的负片视觉是在末地的黑暗环境中进化出来的，所以需要取消原始末影人的后处理管线（不过貌似有色盲悖论？）
    @WrapWithCondition(method = "checkEntityPostEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;setPostEffect(Lnet/minecraft/resources/Identifier;)V", ordinal = 2))
    private boolean disablePrimitiveEndermanPostEffect(GameRenderer instance, Identifier id, @Local(name = "ignoredxx") EnderMan ignoredxx) {
        return !(ignoredxx instanceof PrimitiveEndermanEntity);
    }
}
