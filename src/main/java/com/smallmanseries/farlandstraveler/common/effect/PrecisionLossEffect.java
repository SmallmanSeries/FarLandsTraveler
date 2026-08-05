package com.smallmanseries.farlandstraveler.common.effect;

import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class PrecisionLossEffect extends MobEffect {

    protected PrecisionLossEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    /**
     * 计算精度丢失255级触发传送的目的地
     *
     * @param living 实体
     * @param x      实体的x坐标
     * @param z      实体的z坐标
     * @return 目的地位置
     */
    public static @NonNull Vec3 calculateDest(LivingEntity living, double x, double y, double z, ServerLevel level) {
        int dest = Config.PE_TELEPORT_DEST.getAsInt();
        double dx = living.getDeltaMovement().x();
        double dz = living.getDeltaMovement().z();

        // 计算水平坐标
        double t = Math.min(((dx > 0 ? dest : -dest) - x) / dx, ((dz > 0 ? dest : -dest) - z) / dz);
        x += t * dx;
        z += t * dz;

        // 计算高度（当实体低于地形高度时将其移动到地形高度）
        y = Math.max(y, level
                .getChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z))
                .getHeight(Heightmap.Types.WORLD_SURFACE, (int) x, (int) z)
                + 0.5);

        return new Vec3(x, y, z);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
        // 当实体进入边境之地，由于物理规则发生改变，精度丢失效果在一声噪音后消失
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
}
