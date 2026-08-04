package com.smallmanseries.farlandstraveler.mixin.entity;

import com.llamalad7.mixinextras.sugar.Cancellable;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.MathUtil;
import com.smallmanseries.farlandstraveler.common.effect.FLTMobEffects;
import com.smallmanseries.farlandstraveler.common.effect.PrecisionLossEffect;
import com.smallmanseries.farlandstraveler.common.particle.PEShockwaveParticleOptions;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.portal.TeleportTransition;
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

    /**
     * 当生物的身上有{@link PrecisionLossEffect}（【精度丢失】状态效果）时，根据效果等级降低生物的坐标精度
     */
    @ModifyArgs(method = "setPosRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V"))
    private void handleLosePrecision(Args args, @Cancellable CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof LivingEntity living && living.hasEffect(FLTMobEffects.PRECISION_LOSS)) {
            MobEffectInstance effect = living.getEffect(FLTMobEffects.PRECISION_LOSS);
            if (effect != null) {

                if (effect.getAmplifier() >= 255) {
                    // 精度丢失255级：检测实体的水平速度，速度够大则触发传送，否则定在原地不动
                    if (living.getDeltaMovement().horizontalDistanceSqr() > Config.PE_TELEPORT_THRESHOLD.getAsDouble()) {
                        // 先去除精度丢失效果。下面的传送会递归调用本函数，防止无限递归
                        living.removeEffect(FLTMobEffects.PRECISION_LOSS);
                        if (living.level() instanceof ServerLevel level) {
                            level.sendParticles(
                                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BEDROCK.defaultBlockState()),
                                    living.position().x,
                                    living.position().y,
                                    living.position().z,
                                    32,
                                    living.getBoundingBox().getXsize(),
                                    living.getBoundingBox().getYsize(),
                                    living.getBoundingBox().getZsize(),
                                    0
                            );
                            level.sendParticles(
                                    new PEShockwaveParticleOptions(Direction.UP, 10, 10),
                                    living.position().x,
                                    living.position().y,
                                    living.position().z,
                                    1,
                                    0,
                                    0,
                                    0,
                                    0
                            );
                            level.sendParticles(
                                    new PEShockwaveParticleOptions(Direction.DOWN, 10, 10),
                                    living.position().x,
                                    living.position().y,
                                    living.position().z,
                                    1,
                                    0,
                                    0,
                                    0,
                                    0
                            );

                            // 计算目的地
                            Vec3 dest = new Vec3(0, -50, 0);

                            // 传送
                            LivingEntity newEntity = (LivingEntity) living.teleport(
                                    new TeleportTransition(level, dest, Vec3.ZERO, living.getYRot(), living.getXRot(), TeleportTransition.PLACE_PORTAL_TICKET)
                            );
                            if (newEntity != null) {
                                newEntity.resetFallDistance();
                                newEntity.resetCurrentImpulseContext();
                            }
                        }
                    }

                    // 使实体定在原地
                    args.set(0, this.position.x);
                    args.set(1, this.position.y);
                    args.set(2, this.position.z);
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
