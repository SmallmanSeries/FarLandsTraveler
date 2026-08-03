package com.smallmanseries.farlandstraveler.common.effect;

import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PrecisionLossEffect extends MobEffect {
    private boolean max;

    protected PrecisionLossEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        if (amplification == 255) {
            if (!this.max) {
                this.max = true;
            }
        } else if (this.max) {
            this.max = false;
        }

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
        this.max = amplifier == 255;
    }

    @Override
    public Component getDisplayName() {
        if (this.max) {
            return Component.translatable(this.getDescriptionId() + ".255");
        }
        return Component.translatable(this.getDescriptionId());
    }
}
