package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.mixin.worldgen.BlendedNoiseMixin;
import com.smallmanseries.farlandstraveler.mixin.worldgen.IBlendedNoiseMixin;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

/**
 * 可溢出的混合噪声，用于创建可生成边境之地的自定义噪声。由 {@link BlendedNoiseMixin} 和 {@link IBlendedNoiseMixin} 基于修改原版的 {@link BlendedNoise} 类来实现。
 */
public class BlendedNoiseOverflowable {
    /**
     * 是否正在创建可溢出的混合噪声。{@link BlendedNoiseMixin} 会识别这个标志，并自动调整构造函数。
     */
    public static boolean createOverflowable = false;

    private static final Codec<Double> SCALE_RANGE = Codec.doubleRange(0.001, 1000.0);

    public static final MapCodec<BlendedNoise> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SCALE_RANGE.fieldOf("xz_scale").forGetter(instance1 -> ((IBlendedNoiseMixin) instance1).getXzScale()),
                            SCALE_RANGE.fieldOf("y_scale").forGetter(instance2 -> ((IBlendedNoiseMixin) instance2).getYScale()),
                            SCALE_RANGE.fieldOf("xz_factor").forGetter(instance3 -> ((IBlendedNoiseMixin) instance3).getXzFactor()),
                            SCALE_RANGE.fieldOf("y_factor").forGetter(instance4 -> ((IBlendedNoiseMixin) instance4).getYFactor()),
                            Codec.doubleRange(1.0, 8.0).fieldOf("smear_scale_multiplier").forGetter(instance5 -> ((IBlendedNoiseMixin) instance5).getSmearScaleMultiplier())
                    )
                    .apply(instance, BlendedNoiseOverflowable::createUnseeded)
    );

    private static BlendedNoise createUnseeded(double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier) {
        return createSeeded(new XoroshiroRandomSource(0L), xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier);
    }

    public static BlendedNoise createSeeded(RandomSource random, double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier) {
        createOverflowable = true;
        BlendedNoise blendedNoise = new BlendedNoise(random, xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier);
        createOverflowable = false;
        return blendedNoise;
    }
}
