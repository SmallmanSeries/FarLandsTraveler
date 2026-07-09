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
    private static double lerpX(double alpha1, double p0, double p1) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp(alpha1, p0, p1) : Mth.lerp(alpha1, p0, p1);
    }

    @Redirect(method = "updateForY", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(DDD)D"))
    private static double lerpY(double alpha1, double p0, double p1) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp(alpha1, p0, p1) : Mth.lerp(alpha1, p0, p1);
    }

    @Redirect(method = "updateForZ", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(DDD)D"))
    private static double lerpZ(double alpha1, double p0, double p1) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp(alpha1, p0, p1) : Mth.lerp(alpha1, p0, p1);
    }

    @Redirect(method = "compute", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp3(DDDDDDDDDDD)D"))
    private static double lerp3(double alpha1, double alpha2, double alpha3, double x000, double x100, double x010, double x110, double x001, double x101, double x011, double x111) {
        return Config.ENABLE_SKY_GRID.getAsBoolean() ? MathUtil.lerp3(alpha1, alpha2, alpha3, x000, x100, x010, x110, x001, x101, x011, x111) : Mth.lerp3(alpha1, alpha2, alpha3, x000, x100, x010, x110, x001, x101, x011, x111);
    }
}
