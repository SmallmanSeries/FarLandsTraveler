package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.mixin.worldgen.BlendedNoiseMixin;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

/**
 * 可溢出的混合噪声，用于创建可生成边境之地的自定义噪声。由 {@link BlendedNoiseMixin} 基于修改原版的 {@link BlendedNoise} 类来实现。
 */
public class BlendedNoiseOverflowable extends BlendedNoise{
    protected static final Codec<Double> SCALE_RANGE = Codec.doubleRange(0.001, 1000.0);
    public static final MapCodec<BlendedNoiseOverflowable> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SCALE_RANGE.fieldOf("xz_scale").forGetter(instance1 -> instance1.xzScale),
                            SCALE_RANGE.fieldOf("y_scale").forGetter(instance2 -> instance2.yScale),
                            SCALE_RANGE.fieldOf("xz_factor").forGetter(instance3 -> instance3.xzFactor),
                            SCALE_RANGE.fieldOf("y_factor").forGetter(instance4 -> instance4.yFactor),
                            Codec.doubleRange(1.0, 8.0).fieldOf("smear_scale_multiplier").forGetter(instance5 -> instance5.smearScaleMultiplier)
                    )
                    .apply(instance, BlendedNoiseOverflowable::createUnseeded)
    );

    public BlendedNoiseOverflowable(RandomSource random, double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier) {
        super(random, xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier);
    }

    public static BlendedNoiseOverflowable createUnseeded(double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier) {
        return new BlendedNoiseOverflowable(new XoroshiroRandomSource(0L), xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier);
    }

    @Override
    public BlendedNoiseOverflowable withNewRandom(RandomSource random) {
        return new BlendedNoiseOverflowable(random, this.xzScale, this.yScale, this.xzFactor, this.yFactor, this.smearScaleMultiplier);
    }
}
