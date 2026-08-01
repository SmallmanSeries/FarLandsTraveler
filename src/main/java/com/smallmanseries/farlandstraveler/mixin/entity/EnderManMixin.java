package com.smallmanseries.farlandstraveler.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.smallmanseries.farlandstraveler.common.entity.PrimitiveEndermanEntity;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderMan.class)
public class EnderManMixin {
    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z", ordinal = 0))
    private boolean endermanParticles(boolean original) {
        EnderMan enderman = ((EnderMan) (Object) this);
        return original && !(enderman instanceof PrimitiveEndermanEntity);
    }
}
