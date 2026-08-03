package com.smallmanseries.farlandstraveler.mixin.entity;

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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow
    private Vec3 position;

    /**
     * 当生物的身上有{@link PrecisionLossEffect}（【精度丢失】状态效果）时，根据效果等级降低生物的坐标精度
     */
    @Inject(method = "setPosRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/entity/EntityInLevelCallback;onMove()V"))
    private void losePrecision(double x, double y, double z, CallbackInfo ci) {
        if ((Entity) (Object) this instanceof LivingEntity living && living.hasEffect(FLTMobEffects.PRECISION_LOSS)) {
            MobEffectInstance effect = living.getEffect(FLTMobEffects.PRECISION_LOSS);
            // 精度丢失255级的效果需要特殊处理
            if (effect == null || effect.getAmplifier() >= 255) {
                return;
            }
            this.position = MathUtil.losePrecision(this.position, Math.min(effect.getAmplifier(), 9));
        }
    }
}
