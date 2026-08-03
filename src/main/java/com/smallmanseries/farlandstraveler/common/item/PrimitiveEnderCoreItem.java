package com.smallmanseries.farlandstraveler.common.item;

import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.effect.FLTMobEffects;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.particles.ParticleTypes;
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
        if (FarLands.isInFarLands(player.getX(), player.getY(), player.getZ(), 3)) {
            // 在边境之地中使用原始末影核心
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
            // 在正常世界使用原始末影核心：先播放音效，然后生成黑烟，然后给予使用者精度丢失效果
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

            if (level.isClientSide()) {
                for (int i = 0; i < 16; i++) {
                    level.addParticle(
                            ParticleTypes.LARGE_SMOKE,
                            player.getRandomX(0.5),
                            player.getRandomY(),
                            player.getRandomZ(0.5),
                            0,
                            0,
                            0
                    );
                }
            }

            // 如果玩家身上没有精度丢失效果，就为玩家添加1级、10秒的精度丢失效果；否则给玩家的精度丢失效果增加一级，时长增加10秒，最高到10级。
            if (player.hasEffect(FLTMobEffects.PRECISION_LOSS)) {
                MobEffectInstance effect = player.getEffect(FLTMobEffects.PRECISION_LOSS);
                player.addEffect(new MobEffectInstance(FLTMobEffects.PRECISION_LOSS, effect.getDuration() + 200, Math.min(effect.getAmplifier() + 1, 9), false, false, true));
            } else {
                player.addEffect(new MobEffectInstance(FLTMobEffects.PRECISION_LOSS, 200, 0, false, false, true));
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);
        return InteractionResult.SUCCESS;
    }
}
