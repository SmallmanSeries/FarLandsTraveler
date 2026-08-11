package com.smallmanseries.farlandstraveler.mixin.entity;

import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.MathUtil;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.effect.FLTMobEffects;
import com.smallmanseries.farlandstraveler.common.effect.PrecisionLossEffect;
import com.smallmanseries.farlandstraveler.common.particle.PEShockwaveParticleOptions;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    private Vec3 position;

    @Shadow
    public abstract Vec3 getDeltaMovement();

    @Shadow
    public abstract void setDeltaMovement(Vec3 deltaMovement);

    /**
     * 当生物的身上有{@link PrecisionLossEffect}（【精度丢失】状态效果）时，根据效果等级降低生物的坐标精度
     */
    @ModifyArgs(method = "setPosRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V"))
    private void handleLosePrecision(Args args) {
        if (((Entity) (Object) this) instanceof LivingEntity living && living.hasEffect(FLTMobEffects.PRECISION_LOSS)) {
            MobEffectInstance effect = living.getEffect(FLTMobEffects.PRECISION_LOSS);
            if (effect != null) {
                if (effect.getAmplifier() >= 255) {
                    // 精度丢失256级：检测实体的水平速度，速度够大则触发传送，否则定在原地不动。只有在离边境之地足够远的位置（具体由配置文件定义）才能触发传送
                    if (living.level() instanceof ServerLevel level) { // 副作用：实体无法靠自己（客户端速度）触发传送，只能依靠外力（服务端速度）
                        // 当生物骑乘载具时，改为判定载具的速度
                        if ((living.getVehicle() != null ? living.getVehicle().getKnownSpeed() : living.getDeltaMovement()).horizontalDistanceSqr() > Config.PE_TELEPORT_THRESHOLD.getAsDouble() && Math.max(Math.abs(living.getX()), Math.abs(living.getZ())) < Config.PE_TELEPORT_DEST.getAsInt()) {
                            // 先去除【精度丢失256】效果。下面的传送会递归调用本函数，防止无限递归
                            // 精度丢失退化到1级，持续时间不变
                            int duration = effect.getDuration();
                            living.removeEffect(FLTMobEffects.PRECISION_LOSS);
                            double x = living.getX();
                            double y = living.getY();
                            double z = living.getZ();

                            // 音效、粒子特效
                            level.playSound(
                                    null,
                                    x,
                                    y,
                                    z,
                                    SoundEvents.GLASS_BREAK,
                                    SoundSource.WEATHER,
                                    4.0F,
                                    0.5F
                            );
                            level.sendParticles(
                                    new BlockParticleOption(ParticleTypes.BLOCK, FLTBlocks.INVALID_BLOCK.get().defaultBlockState()),
                                    x,
                                    y,
                                    z,
                                    32,
                                    living.getBoundingBox().getXsize(),
                                    living.getBoundingBox().getYsize(),
                                    living.getBoundingBox().getZsize(),
                                    0
                            );
                            level.sendParticles(
                                    new PEShockwaveParticleOptions(Direction.UP, 12, 20),
                                    true,
                                    true,
                                    x,
                                    y,
                                    z,
                                    1,
                                    0,
                                    0,
                                    0,
                                    0
                            );
                            level.sendParticles(
                                    new PEShockwaveParticleOptions(Direction.DOWN, 12, 20),
                                    true,
                                    true,
                                    x,
                                    y,
                                    z,
                                    1,
                                    0,
                                    0,
                                    0,
                                    0
                            );

                            // 传送
                            living.stopRiding();
                            LivingEntity newEntity = (LivingEntity) living.teleport(
                                    new TeleportTransition(
                                            level,
                                            PrecisionLossEffect.calculateDest(living, x, y, z, level),
                                            Vec3.ZERO,
                                            living.getYRot(),
                                            living.getXRot(),
                                            TeleportTransition.PLACE_PORTAL_TICKET
                                    )
                            );
                            if (newEntity != null) {
                                newEntity.resetFallDistance();
                                newEntity.resetCurrentImpulseContext();
                            }

                            // 再次给予精度丢失1级
                            living.addEffect(new MobEffectInstance(FLTMobEffects.PRECISION_LOSS, duration, 0, false, false, true));
                        }
                    }

                    // 使实体定在原地，并失去所有垂直速度
                    args.set(0, this.position.x);
                    args.set(1, this.position.y);
                    args.set(2, this.position.z);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1, 0, 1));
                } else {
                    // 非256级：丢失坐标参数的精度
                    int lose = 5 - effect.getAmplifier();
                    args.set(0, MathUtil.losePrecision(args.get(0), lose));
                    args.set(1, MathUtil.losePrecision(args.get(1), lose));
                    args.set(2, MathUtil.losePrecision(args.get(2), lose));
                }
            }
        }
    }
}
