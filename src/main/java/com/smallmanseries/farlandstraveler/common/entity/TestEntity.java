package com.smallmanseries.farlandstraveler.common.entity;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import org.jspecify.annotations.Nullable;

public class TestEntity extends Monster {
    protected TestEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
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
    public float getHealth() {
        return Float.NaN;
    }

    @Override
    public double getAttributeValue(Holder<Attribute> attribute) {
        if (attribute == Attributes.MAX_HEALTH) {
            return Double.NaN;
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
    public @Nullable Component getCustomName() {
        return Component.translatable("entity.farlandstraveler.test_entity");
    }
}
