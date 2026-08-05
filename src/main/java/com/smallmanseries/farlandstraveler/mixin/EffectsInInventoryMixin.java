package com.smallmanseries.farlandstraveler.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.smallmanseries.farlandstraveler.common.effect.FLTMobEffects;
import com.smallmanseries.farlandstraveler.common.effect.PrecisionLossEffect;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EffectsInInventory.class)
public class EffectsInInventoryMixin {

    /**
     * {@link PrecisionLossEffect}（【精度丢失】状态效果）255级的特殊显示
     */
    @ModifyReturnValue(method = "getEffectName", at = @At("RETURN"))
    private Component getLv255Description(Component original, @Local(argsOnly = true, name = "effect") MobEffectInstance effect) {
        if (effect.is(FLTMobEffects.PRECISION_LOSS) && effect.getAmplifier() >= 255) {
            return Component.translatable("effect.farlandstraveler.precision_loss.255");
        }
        return original;
    }
}
