package com.smallmanseries.farlandstraveler.common.entity;

import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.network.PopUpPacket;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.Nullable;

public class TestEntity extends Monster {
    protected TestEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.setCustomName(Component.translatable("entity.farlandstraveler.test_entity"));
        this.setCustomNameVisible(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes();
    }

    @Override
    public void load(ValueInput input) {
        super.load(input);
        this.setCustomName(Component.translatable("entity.farlandstraveler.test_entity"));
        this.setCustomNameVisible(true);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FLTSoundEvents.ENTITY_GENERIC_WU.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FLTSoundEvents.ENTITY_GENERIC_WU.get();
    }

    @Override
    public float getHealth() {
        return Float.NaN;
    }

    @Override
    public double getAttributeValue(Holder<Attribute> attribute) {
        if (attribute == Attributes.MAX_HEALTH) {
            return 3007192634048887081.1726;
        }
        return this.getAttributes().getValue(attribute);
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean isDeadOrDying() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distSqr) {
        return false;
    }

    @Override
    public void checkDespawn() {
    }

    @Override
    public @Nullable Component getCustomName() {
        return Component.translatable("entity.farlandstraveler.test_entity");
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (source.getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(FLTItems.SPAWN_TEST_ENTITY)) {
                this.remove(RemovalReason.DISCARDED);
            } else {
                player.sendOverlayMessage(Component.literal(String.valueOf(damage)));
                if (player instanceof ServerPlayer && player.getMainHandItem().is(FLTItems.FAKE_CHUNK_MARKER)) {
                    PacketDistributor.sendToPlayer((ServerPlayer) player,
                            new PopUpPacket(
                                    Component.translatable("metagame.farlandstraveler.title").getString(),
                                    Component.translatable("metagame.farlandstraveler.content").getString()
                            ));
                }
            }
        }

        this.dead = false;
        boolean result = super.hurtServer(level, source, 0);
        this.invulnerableTime = 0;
        this.hurtTime = 0;
        return result;
    }
}
