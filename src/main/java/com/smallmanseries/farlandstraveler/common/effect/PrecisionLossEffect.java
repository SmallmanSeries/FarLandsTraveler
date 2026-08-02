package com.smallmanseries.farlandstraveler.common.effect;

import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PrecisionLossEffect extends MobEffect {
    protected PrecisionLossEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
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
}
