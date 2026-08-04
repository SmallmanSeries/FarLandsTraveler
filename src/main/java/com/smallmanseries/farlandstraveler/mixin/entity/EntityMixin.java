package com.smallmanseries.farlandstraveler.mixin.entity;

import com.llamalad7.mixinextras.sugar.Cancellable;
import com.smallmanseries.farlandstraveler.common.MathUtil;
import com.smallmanseries.farlandstraveler.common.effect.FLTMobEffects;
import com.smallmanseries.farlandstraveler.common.effect.PrecisionLossEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    private Vec3 position;

    @Shadow
    public abstract Vec3 getDeltaMovement();

    /**
     * 当生物的身上有{@link PrecisionLossEffect}（【精度丢失】状态效果）时，根据效果等级降低生物的坐标精度
     */
    @ModifyArgs(method = "setPosRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V"))
    private void handleLosePrecision(Args args, @Cancellable CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof LivingEntity living && living.hasEffect(FLTMobEffects.PRECISION_LOSS)) {
            MobEffectInstance effect = living.getEffect(FLTMobEffects.PRECISION_LOSS);
            if (effect != null) {

                if (effect.getAmplifier() >= 255) {
                    // 精度丢失255级：检测实体的速度，速度够大则触发传送，否则取消移动


                    ci.cancel();
                } else {
                    // 非255级：丢失坐标参数的精度
                    int lose = 5 - effect.getAmplifier();
                    args.set(0, MathUtil.losePrecision(args.get(0), lose));
                    args.set(1, MathUtil.losePrecision(args.get(1), lose));
                    args.set(2, MathUtil.losePrecision(args.get(2), lose));
                }
            }
        }
    }
}
