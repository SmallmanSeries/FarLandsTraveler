package com.smallmanseries.farlandstraveler.common.entity;

import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import com.smallmanseries.farlandstraveler.mixin.entity.EnderManMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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
        // 原始末影人不会瞬移，这算是一大削弱，所以提升了它们的移动速度和游泳速度
        return EnderMan.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.4).add(NeoForgeMod.SWIM_SPEED, 2.5);
    }

    /**
     * 检查原始末影人能否生成。当然也可以是其他生物用原始末影人的生成规则。
     * <p>原始末影人只能生成在正常世界，且从世界中心开始，越靠近边境之地生成概率越大，具体由配置文件决定
     * @param type 实体种类
     * @param level 存档
     * @param spawnReason 实体是如何生成的
     * @param pos 生成位置
     * @param random 随机数源
     * @return 布尔值，生物能否生成
     */
    public static boolean checkMonsterSpawnRules(EntityType<? extends Mob> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        int far = Config.FAR_LANDS_DISTANCE.getAsInt();
        return Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random)
                && !FarLands.isInFarLands(pos.getX(), pos.getY(), pos.getZ(), 0)
                && random.nextInt(Math.ceilDiv(far - Math.max(Math.abs(pos.getX()), Math.abs(pos.getZ())), far / Config.PEMAN_SPAWN_WEIGHT.getAsInt())) == 0;
    }

    /**
     * 为原始末影人添加黑烟效果。
     * 超类（普通末影人）的传送门粒子效果已通过{@link EnderManMixin}禁用
     */
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
        return false; // 禁用原始末影人的瞬移能力
    }

    @Override
    protected boolean teleportTowards(Entity entity) {
        return false; // 禁用原始末影人的瞬移能力
    }

    private static class EscapeWaterGoal extends Goal {
        private final PrimitiveEndermanEntity priman;
        private final Level level;
        private Vec3 safePlace;
        private boolean escaping;
        private boolean dangerous; // 危险标记，当原始末影人周围16格没有陆地时启用

        public EscapeWaterGoal(PrimitiveEndermanEntity priman) {
            this.priman = priman;
            this.level = priman.level();
            this.safePlace = null;
            this.escaping = false;
            this.dangerous = false;
        }

        @Override
        public boolean canUse() {
            // 该目标当且仅当原始末影人浮在水面上，且不危险（周围16格内能找到陆地），以及当前没有在追玩家时可用
            if (!this.priman.isInWater()) {
                if (this.dangerous && this.priman.onGround()) {
                    this.dangerous = false; // 如果原始末影人成功上岸了，顺便解除危险标记
                }
                return false;
            }
            if (this.priman.isUnderWater()) {
                return false;
            }
            // 危险标记用于防止原始末影人反复检测16格内有没有陆地，减小性能消耗
            if (this.dangerous) {
                return false;
            }
            // 原始末影人追逐玩家的优先级高于逃离水域
            if (this.priman.getTarget() != null) {
                return false;
            }
            return findSafePlace();
        }

        private boolean findSafePlace() {
            // 检测周围16格内有没有陆地
            BlockPos pos = this.priman.blockPosition();
            for (BlockPos checkPos : BlockPos.withinManhattan(pos, 16, 0, 16)) {
                if (this.level.getBlockState(checkPos).getFluidState().isEmpty()) {
                    this.safePlace = Vec3.atBottomCenterOf(checkPos);
                    return true;
                }
            }
            // 周围16格内没有陆地，危险。
            this.dangerous = true;
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            // 寻路结束后才能继续使用本目标
            return !this.priman.getNavigation().isDone();
        }

        @Override
        public void start() {
            // 开始逃离，寻路到findSafePlace()找到的陆地安全位置
            this.escaping = true; // “正在逃跑”标记，用于tick()检测是否正在逃跑
            this.priman.getNavigation().moveTo(this.safePlace.x(), this.safePlace.y(), this.safePlace.z(), 1.0);
        }

        @Override
        public void tick() {
            // 一旦成功上岸，就不继续寻路了，停在原地
            if (this.escaping && this.priman.onGround() && !this.priman.isInWater()) {
                if (!this.priman.getNavigation().isDone()) {
                    this.priman.getNavigation().stop();
                }
                this.escaping = false;
            }
        }
    }
}
