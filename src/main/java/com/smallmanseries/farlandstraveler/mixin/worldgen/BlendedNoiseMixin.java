package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.llamalad7.mixinextras.sugar.Local;
import com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions.BlendedNoiseCustomizable;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 将原版的混合噪声改造成基于派生类类型控制溢出的混合噪声，若为 {@link BlendedNoiseCustomizable} 派生类则溢出。
 *
 *
 *
 * @author INF32768
 *
 * @author OslorasKi
 */
@Mixin(BlendedNoise.class)
public abstract class BlendedNoiseMixin {

    @Shadow
    @Final
    protected double xzFactor;

    @Shadow
    @Final
    private double xzMultiplier;

    @Shadow
    @Final
    private double yMultiplier;

    @ModifyVariable(method = "compute", at = @At("STORE"), ordinal = 0)
    private double setXMain(double original, @Local(argsOnly = true) DensityFunction.FunctionContext context) {
        if(((BlendedNoise)(Object)this) instanceof BlendedNoiseCustomizable noise){
            return (context.blockX() + noise.xShift) * this.xzMultiplier;
        }
        return original;
    }

    @ModifyVariable(method = "compute", at = @At("STORE"), ordinal = 1)
    private double setYMain(double original, @Local(argsOnly = true) DensityFunction.FunctionContext context) {
        if(((BlendedNoise)(Object)this) instanceof BlendedNoiseCustomizable noise){
            return (context.blockY() + noise.yShift) * this.yMultiplier;
        }
        return original;
    }

    @ModifyVariable(method = "compute", at = @At("STORE"), ordinal = 2)
    private double setZMain(double original, @Local(argsOnly = true) DensityFunction.FunctionContext context) {
        if(((BlendedNoise)(Object)this) instanceof BlendedNoiseCustomizable noise){
            return (context.blockZ() + noise.zShift) * noise.zMultiplier;
        }
        return original;
    }

    @ModifyVariable(method = "compute", at = @At("STORE"), ordinal = 5)
    private double setZSelector(double original) {
        if(((BlendedNoise)(Object)this) instanceof BlendedNoiseCustomizable noise){
            return (original * this.xzFactor) / noise.zFactor;
        }
        return original;
    }

    @Redirect(method = "compute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/synth/PerlinNoise;wrap(D)D"))
    private double redirectWrap(double value) {
        if(((BlendedNoise)(Object)this) instanceof BlendedNoiseCustomizable noise) {
            return noise.overflowable ? value : PerlinNoise.wrap(value);
        }
        return PerlinNoise.wrap(value);
    }
}
