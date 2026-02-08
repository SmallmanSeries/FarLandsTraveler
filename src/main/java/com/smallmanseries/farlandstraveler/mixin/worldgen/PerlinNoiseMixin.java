package com.smallmanseries.farlandstraveler.mixin.worldgen;

import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PerlinNoise.class)
public class PerlinNoiseMixin {

    // 使噪声可以溢出（副作用：在边境之地出现前，世界就会慢慢开始崩坏，出现通天石柱，不过这是个良性副作用，暂时不修）
    @Inject(method = "wrap", at = @At("HEAD"), cancellable = true)
    private static void doOverFlow(double value, CallbackInfoReturnable<Double> cir){
        cir.setReturnValue(value);
    }
}
