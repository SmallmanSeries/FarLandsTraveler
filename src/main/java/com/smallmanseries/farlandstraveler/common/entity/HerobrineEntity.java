package com.smallmanseries.farlandstraveler.common.entity;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Herobrine
 * <p>
 * 只在无叶树林里出现、远远看着玩家的环境渲染生物，不会移动，不会攻击，
 * 不会触发机关，也不会被命名持久化。受到任何伤害，或者玩家靠近、注视都立刻消失并播放环境音效。
 * <p>
 * 生成由 {@link HerobrineSpawner} 负责
 */
public class HerobrineEntity extends Mob {
    /** 存在时长：半个游戏日 */
    public static final int LIFETIME_TICKS = 6400;
    /** 玩家靠近到这个距离以内就消失 */
    public static final double VANISH_DISTANCE = 16.0;
    /** 所有玩家都超过这个距离就清除（与敌对生物的清除半径一致） */
    public static final double CLEAR_DISTANCE = 128.0;
    /** 注视 / 被准星指到的判定半径 */
    public static final double WATCH_DISTANCE = 64.0;

    /** 已经存在的时长 */
    private int lifeTicks;

    public HerobrineEntity(EntityType<? extends HerobrineEntity> type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_DAMAGE, 0.0);
    }

    /**
     * 空的 AI 目标
     */
    @Override
    protected void registerGoals() {
    }

    /**
     * 完全不动，重力、流体推动一并忽视
     */
    @Override
    public void travel(Vec3 input) {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    /**
     * 不会触发压力板、绊线等机关
     */
    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return null;
    }

    /**
     * 清除机制：最近的玩家超过清除半径时立刻清除。
     * <p>
     * 这里完全绕开原版的持久化判断，所以用命名牌命名也无法持久化。
     * 消失不走本类的vanish()，毕竟那么远玩家也听不见。
     */
    @Override
    public void checkDespawn() {
        Player nearest = this.level().getNearestPlayer(this, -1.0);
        if (nearest != null && nearest.distanceToSqr(this) > CLEAR_DISTANCE * CLEAR_DISTANCE) {
            this.discard();
        }
    }

    /**
     * 受到任何伤害立刻消失
     */
    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        this.vanish();
        return false;
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide()) {
            this.serverUpdate();
        }
        super.aiStep();
    }

    private void serverUpdate() {
        // 存在时长到了就消失
        if (++this.lifeTicks >= LIFETIME_TICKS) {
            this.vanish();
            return;
        }

        Player nearest = this.level().getNearestPlayer(this, WATCH_DISTANCE);
        if (nearest == null) {
            return;
        }

        // 玩家靠近到 16 格以内立刻消失
        if (nearest.distanceToSqr(this) <= VANISH_DISTANCE * VANISH_DISTANCE) {
            this.vanish();
            return;
        }

        // 面向最近的玩家
        this.faceTowards(nearest);

        // 被玩家注视时立刻消失
        if (this.isBeingLookedAt()) {
            this.vanish();
        }
    }

    /**
     * 面向目标玩家，“他在看着你。”
     * <p>
     * 设定上，Herobrine是因距离现象原因死亡的探境者，他们会注视附近的人类是一种“望乡”的本能。
     */
    private void faceTowards(Player player) {
        double dx = player.getX() - this.getX();
        double dz = player.getZ() - this.getZ();
        double dy = player.getEyeY() - this.getEyeY();
        double horizontal = Math.sqrt(dx * dx + dz * dz);

        float yRot = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
        float xRot = (float) (-(Mth.atan2(dy, horizontal) * (180.0 / Math.PI)));

        this.setYRot(yRot);
        this.setYBodyRot(yRot);
        this.setYHeadRot(yRot);
        this.setXRot(xRot);
    }

    /**
     * 判定是否被某个玩家注视（用准星指着），判定方式与末影人一致
     * <p>
     * 因距离现象场的不稳定性，Herobrine无法被人类注视。这大概也是这些无法瞑目的亡魂的又一场悲哀
     */
    private boolean isBeingLookedAt() {
        for (Player player : this.level().players()) {
            if (player.isSpectator()) {
                continue;
            }
            if (player.distanceToSqr(this) > WATCH_DISTANCE * WATCH_DISTANCE) {
                continue;
            }
            if (this.isLookingAtMe(player, 0.025, true, false, this.getEyeY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 永远不显示名称标签（渲染器里也有同样的处理，这里是兜底）
     */
    @Override
    public boolean shouldShowName() {
        return false;
    }

    /**
     * 消失：播放洞穴环境音效后移除自己。
     * <p>
     * 大部分消失原因（玩家靠近、被注视、受伤、寿命耗尽、同区域重复生成）
     * 都走这一个入口，保证音效行为一致。
     */
    public void vanish() {

        // 调试代码
        FarLandsTraveler.LOGGER.info("Herobrine已于" + this.position() + "消失");

        if (this.isRemoved()) {
            return;
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.AMBIENT_CAVE, SoundSource.AMBIENT, 2.0F, 1.0F);
        this.discard();
    }
}
