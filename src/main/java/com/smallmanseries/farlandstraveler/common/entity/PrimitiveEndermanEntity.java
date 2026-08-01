package com.smallmanseries.farlandstraveler.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;

public class PrimitiveEndermanEntity extends EnderMan {
    public PrimitiveEndermanEntity(EntityType<? extends EnderMan> type, Level level) {
        super(type, level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new EscapeWaterGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return EnderMan.createAttributes().add(NeoForgeMod.SWIM_SPEED, 2.5);
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

    @Override
    protected boolean teleportTowards(Entity entity) {
        return false;
    }

    private static class EscapeWaterGoal extends Goal {
        private final PrimitiveEndermanEntity priman;
        private final Level level;
        private Vec3 safePlace;
        private boolean escaping;

        public EscapeWaterGoal(PrimitiveEndermanEntity priman) {
            this.priman = priman;
            this.level = priman.level();
            this.safePlace = null;
            this.escaping = false;
        }

        @Override
        public boolean canUse() {
            if (!this.priman.isInWater()) {
                return false;
            }
            if (this.priman.getTarget() != null) {
                return false;
            }
            return findSafePlace();
        }

        private boolean findSafePlace() {
            if (this.safePlace != null) {
                return true;
            }

            RandomSource random = this.priman.getRandom();
            BlockPos pos = this.priman.blockPosition();
            BlockPos randomPos;

            for (int i = 0; i < 10; i++) {
                randomPos = pos.offset(random.nextInt(32) - 16, 0, random.nextInt(32) - 16);
                if (this.level.getBlockState(randomPos).getFluidState().isEmpty()) {
                    this.safePlace = Vec3.atBottomCenterOf(randomPos);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return !this.priman.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.escaping = true;
            this.priman.getNavigation().moveTo(this.safePlace.x(), this.safePlace.y(), this.safePlace.z(), 1.0);
        }

        @Override
        public void tick() {
            if (this.escaping && this.priman.onGround() && !this.priman.isInWater()) {
                if (!this.priman.getNavigation().isDone()) {
                    this.priman.getNavigation().stop();
                }
                this.escaping = false;
            }
        }
    }
}
