package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.MathUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseChunk.NoiseInterpolator.class)
public abstract class NoiseInterpolatorMixin {

    // 生成天空网格
    @Redirect(method = "updateForX", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(DDD)D"))
    private static double lerpX(double delta, double start, double end) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp(delta, start, end) : Mth.lerp(delta, start, end);
    }

    @Redirect(method = "updateForY", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(DDD)D"))
    private static double lerpY(double delta, double start, double end) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp(delta, start, end) : Mth.lerp(delta, start, end);
    }

    @Redirect(method = "updateForZ", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(DDD)D"))
    private static double lerpZ(double delta, double start, double end) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp(delta, start, end) : Mth.lerp(delta, start, end);
    }

    @Redirect(method = "compute", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp3(DDDDDDDDDDD)D"))
    private static double lerp3(double delta1, double delta2, double delta3, double start1, double end1, double start2, double end2, double start3, double end3, double start4, double end4) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp3(delta1, delta2, delta3, start1, end1, start2, end2, start3, end3, start4, end4) : Mth.lerp3(delta1, delta2, delta3, start1, end1, start2, end2, start3, end3, start4, end4);
    }
}
