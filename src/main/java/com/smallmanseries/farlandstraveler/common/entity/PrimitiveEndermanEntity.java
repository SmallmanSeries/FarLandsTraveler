package com.smallmanseries.farlandstraveler.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;

public class PrimitiveEndermanEntity extends EnderMan {
    public PrimitiveEndermanEntity(EntityType<? extends EnderMan> type, Level level) {
        super(type, level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeGoal(EnderMan.EndermanLookForPlayerGoal);
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide()) {
            this.level().addParticle(
                    ParticleTypes.LARGE_SMOKE,
                    this.getRandomX(0.5),
                    this.getRandomY() - 0.25,
                    this.getRandomZ(0.5),
                    0,
                    0,
                    0
            );
        }
        super.aiStep();
    }

    @Override
    protected boolean teleport() {
        return false;
    }
}
