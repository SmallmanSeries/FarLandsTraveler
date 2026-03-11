package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions.BlendedNoiseOverflowable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 将原版的混合噪声改造成有特定变量控制溢出的混合噪声，为 {@link BlendedNoiseOverflowable} 类提供服务。
 *
 * @see IBlendedNoiseMixin
 *
 * @author INF32768
 */
@Mixin(BlendedNoise.class)
public abstract class BlendedNoiseMixin {
    @Unique
    boolean _$isOverflowable = false;

    @Inject(method = "<init>(Lnet/minecraft/world/level/levelgen/synth/PerlinNoise;Lnet/minecraft/world/level/levelgen/synth/PerlinNoise;Lnet/minecraft/world/level/levelgen/synth/PerlinNoise;DDDDD)V", at = @At("RETURN"))
    private void addOverflowableFlag(PerlinNoise minLimitNoise, PerlinNoise maxLimitNoise, PerlinNoise mainNoise, double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier, CallbackInfo ci) {
        this._$isOverflowable = BlendedNoiseOverflowable.createOverflowable;
    }

    @Inject(at = @At("HEAD"), method = "withNewRandom", cancellable = true)
    private void redirectWithNewRandom(RandomSource random, CallbackInfoReturnable<BlendedNoise> cir) {
        if (this._$isOverflowable) {
            cir.setReturnValue(BlendedNoiseOverflowable.createSeeded(random, ((IBlendedNoiseMixin) this).getXzScale(), ((IBlendedNoiseMixin) this).getYScale(), ((IBlendedNoiseMixin) this).getXzFactor(), ((IBlendedNoiseMixin) this).getYFactor(), ((IBlendedNoiseMixin) this).getSmearScaleMultiplier()));
        }
    }

    @Redirect(method = "compute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/synth/PerlinNoise;wrap(D)D"))
    private double redirectWrap(double value) {
        return this._$isOverflowable ? value : PerlinNoise.wrap(value);
    }
}
