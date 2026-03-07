package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

import java.util.stream.IntStream;

public class BlendedNoiseOverflowable extends BlendedNoise {
    private static final Codec<Double> SCALE_RANGE = Codec.doubleRange(0.001, 1000.0);
    public static final MapCodec<BlendedNoiseOverflowable> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SCALE_RANGE.fieldOf("xz_scale").forGetter(blendedNoiseOverflowable -> blendedNoiseOverflowable.xzScale),
                            SCALE_RANGE.fieldOf("y_scale").forGetter(blendedNoiseOverflowable -> blendedNoiseOverflowable.yScale),
                            SCALE_RANGE.fieldOf("xz_factor").forGetter(blendedNoiseOverflowable -> blendedNoiseOverflowable.xzFactor),
                            SCALE_RANGE.fieldOf("y_factor").forGetter(blendedNoiseOverflowable -> blendedNoiseOverflowable.yFactor),
                            Codec.doubleRange(1.0, 8.0).fieldOf("smear_scale_multiplier").forGetter(blendedNoiseOverflowable -> blendedNoiseOverflowable.smearScaleMultiplier)
                    )
                    .apply(instance, BlendedNoiseOverflowable::createUnseeded)
    );
    public static final KeyDispatchDataCodec<BlendedNoiseOverflowable> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);
    private final PerlinNoise minLimitNoise;
    private final PerlinNoise maxLimitNoise;
    private final PerlinNoise mainNoise;
    private final double xzMultiplier;
    private final double yMultiplier;
    private final double xzFactor;
    private final double yFactor;
    private final double smearScaleMultiplier;
    private final double maxValue;
    private final double xzScale;
    private final double yScale;

    public static BlendedNoiseOverflowable createUnseeded(double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier) {
        return new BlendedNoiseOverflowable(new XoroshiroRandomSource(0L), xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier);
    }

    private BlendedNoiseOverflowable(
            RandomSource random,
            PerlinNoise minLimitNoise,
            PerlinNoise maxLimitNoise,
            PerlinNoise mainNoise,
            double xzScale,
            double yScale,
            double xzFactor,
            double yFactor,
            double smearScaleMultiplier
    ) {
        super(random, xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier);

        this.minLimitNoise = minLimitNoise;
        this.maxLimitNoise = maxLimitNoise;
        this.mainNoise = mainNoise;
        this.xzScale = xzScale;
        this.yScale = yScale;
        this.xzFactor = xzFactor;
        this.yFactor = yFactor;
        this.smearScaleMultiplier = smearScaleMultiplier;
        this.xzMultiplier = 684.412 * this.xzScale;
        this.yMultiplier = 684.412 * this.yScale;
        this.maxValue = minLimitNoise.maxBrokenValue(this.yMultiplier);
    }

    @VisibleForTesting
    public BlendedNoiseOverflowable(RandomSource random, double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier) {
        this(
                random,
                PerlinNoise.createLegacyForBlendedNoise(random, IntStream.rangeClosed(-15, 0)),
                PerlinNoise.createLegacyForBlendedNoise(random, IntStream.rangeClosed(-15, 0)),
                PerlinNoise.createLegacyForBlendedNoise(random, IntStream.rangeClosed(-7, 0)),
                xzScale,
                yScale,
                xzFactor,
                yFactor,
                smearScaleMultiplier
        );
    }

    public BlendedNoiseOverflowable withNewRandom(RandomSource random) {
        return new BlendedNoiseOverflowable(random, this.xzScale, this.yScale, this.xzFactor, this.yFactor, this.smearScaleMultiplier);
    }

    @Override
    public double compute(DensityFunction.FunctionContext context) {
        double noiseX = context.blockX() * this.xzMultiplier;
        double noiseY = context.blockY() * this.yMultiplier;
        double noiseZ = context.blockZ() * this.xzMultiplier;
        double noiseXSelector = noiseX / this.xzFactor;
        double noiseYSelector = noiseY / this.yFactor;
        double noiseZSelector = noiseZ / this.xzFactor;
        double referenceY = this.yMultiplier * this.smearScaleMultiplier;
        double referenceYSelector = referenceY / this.yFactor;
        double resultLow = 0.0;
        double resultHigh = 0.0;
        double resultSelector = 0.0;
        double fractal = 1.0;

        for (int i = 0; i < 8; i++) {
            ImprovedNoise noiseSelector = this.mainNoise.getOctaveNoise(i);
            if (noiseSelector != null) {
                resultSelector += noiseSelector.noise(
                        noiseXSelector * fractal,
                        noiseYSelector * fractal,
                        noiseZSelector * fractal,
                        referenceYSelector * fractal, noiseYSelector * fractal) / fractal;
            }

            fractal /= 2.0;
        }

        double resultSelectorProcessed = (resultSelector / 10.0 + 1.0) / 2.0;
        boolean useHighNoise = resultSelectorProcessed >= 1.0;
        boolean useLowNoise = resultSelectorProcessed <= 0.0;
        fractal = 1.0;

        for (int j = 0; j < 16; j++) {
            double x = noiseX * fractal;
            double y = noiseY * fractal;
            double z = noiseZ * fractal;
            double yScale = referenceY * fractal;
            if (!useHighNoise) {
                ImprovedNoise noiseLow = this.minLimitNoise.getOctaveNoise(j);
                if (noiseLow != null) {
                    resultLow += noiseLow.noise(x, y, z, yScale, noiseY * fractal) / fractal;
                }
            }

            if (!useLowNoise) {
                ImprovedNoise noiseHigh = this.maxLimitNoise.getOctaveNoise(j);
                if (noiseHigh != null) {
                    resultHigh += noiseHigh.noise(x, y, z, yScale, noiseY * fractal) / fractal;
                }
            }

            fractal /= 2.0;
        }

        return Mth.clampedLerp(resultLow / 512.0, resultHigh / 512.0, resultSelectorProcessed) / 128.0;
    }

    @Override
    public double minValue() {
        return -this.maxValue();
    }

    @Override
    public double maxValue() {
        return this.maxValue;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
