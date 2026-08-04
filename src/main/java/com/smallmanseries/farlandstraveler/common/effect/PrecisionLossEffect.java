package com.smallmanseries.farlandstraveler.common.effect;

import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class PrecisionLossEffect extends MobEffect {
    private boolean max;

    protected PrecisionLossEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        // 检测效果是否为255级，如果是则将max设为true，否则设为false
        if (amplification == 255) {
            if (!this.max) {
                this.max = true;
            }
        } else if (this.max) {
            this.max = false;
        }
        // 当实体进入边境之地，由于物理规则发生改变，精度丢失效果在一声噪音后消失
        if (FarLands.isInFarLands(mob.getX(), mob.getY(), mob.getZ(), 3)) {
            serverLevel.playSound(
                    null,
                    mob.getX(),
                    mob.getY(),
                    mob.getZ(),
                    FLTSoundEvents.EFFECT_PRECISION_LOSS_FAILURE,
                    SoundSource.WEATHER,
                    1.0F,
                    1.0F
            );

            // Todo 切换成穿墙效果
            mob.removeEffect(FLTMobEffects.PRECISION_LOSS);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }

    @Override
    public void onEffectStarted(LivingEntity mob, int amplifier) {
        // 检测效果是否为255级，如果是则将max设为true，否则设为false（初始化）
        this.max = amplifier == 255;
    }

    @Override
    public Component getDisplayName() {
        // 实现255级的特殊描述
        if (this.max) {
            return Component.translatable(this.getDescriptionId() + ".255");
        }
        return Component.translatable(this.getDescriptionId());
    }

    /**
     * 计算精度丢失255级触发传送的目的地
     *
     * @param living 实体
     * @param x      实体的x坐标
     * @param z      实体的z坐标
     * @return 目的地位置
     */
    public static @NonNull Vec3 calculateDest(LivingEntity living, double x, double z) {
        int dest = Config.PE_TELEPORT_DEST.getAsInt();
        double dx = living.getDeltaMovement().x();
        double dz = living.getDeltaMovement().z();
        double t = Math.min(((dx > 0 ? dest : -dest) - x) / dx, ((dz > 0 ? dest : -dest) - z) / dz);

        return new Vec3(x + t * dx, living.getY(), z + t * dz);
    }
}
