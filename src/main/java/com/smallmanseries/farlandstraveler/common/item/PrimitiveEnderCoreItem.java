package com.smallmanseries.farlandstraveler.common.item;

import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.effect.FLTMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PrimitiveEnderCoreItem extends Item {
    public PrimitiveEnderCoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (Math.max(player.position().x(), player.position().z()) > (Config.FAR_LANDS_DISTANCE.getAsInt() - 3) || Math.min(player.position().x(), player.position().z()) < -Config.FAR_LANDS_DISTANCE.getAsInt()) {
            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_USE_FAR,
                    SoundSource.NEUTRAL,
                    0.6F,
                    level.getRandom().nextFloat() * 0.2F + 0.9F
            );

            player.sendOverlayMessage(Component.translatable("message.farlandstraveler.wip"));
        } else {
            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_USE,
                    SoundSource.NEUTRAL,
                    0.6F,
                    level.getRandom().nextFloat() * 0.2F + 0.9F
            );

            if (player.hasEffect(FLTMobEffects.PRECISION_LOSS)) {
                MobEffectInstance effect = player.getEffect(FLTMobEffects.PRECISION_LOSS);
                if (effect != null) {
                    effect.update(new MobEffectInstance(FLTMobEffects.PRECISION_LOSS, effect.getDuration() + 200, 0, false, false, true));
                }
            } else {
                player.addEffect(new MobEffectInstance(FLTMobEffects.PRECISION_LOSS, 200, 0, false, false, true));
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);
        return InteractionResult.SUCCESS;
    }
}
