package com.smallmanseries.farlandstraveler.common.effect;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTMobEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, FarLandsTraveler.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> PRECISION_LOSS = EFFECTS.register("precision_loss", () -> new PrecisionLossEffect(MobEffectCategory.NEUTRAL, 6713855));
}
