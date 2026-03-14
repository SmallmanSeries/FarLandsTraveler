package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

/**
 * 可循环、可选溢出的混合噪声，用于创建“循环边境之地”的地形。
 * @author INF32768
 */
public class BlendedNoiseRepeating extends BlendedNoiseOverflowable {
    /**
     * 是否可溢出。若为 true，则噪声会在坐标为 2^31 ÷ xzScale ÷ 684.412 的位置溢出，形成边境之地。
     * <p>
     * 由 {@link com.smallmanseries.farlandstraveler.mixin.worldgen.BlendedNoiseMixin} 根据此变量修改原版类实现溢出。
     */
    public final boolean canOverflow;
    /**
     * 循环起点。必须为正数。当任意一轴的坐标的绝对值大于此值时，该轴上的噪声值将开始按照设定的模式进行循环。
     * <p>
     * 建议为 8 的倍数，否则后续的线性插值可能破坏循环模式。
     */
    private final int repeatStart;
    /**
     * 单个循环区域内每个循环节的长度。必须为正数。
     * <p>
     * 建议为 8 的倍数，否则后续的线性插值可能破坏循环模式。
     */
    private final int repeatLength;
    /**
     * 每个循环区域内循环节的数量。必须为正数。
     */
    private final int repeatCount;

    private static final Codec<Integer> POSITIVE_INT_RANGE = Codec.intRange(1, Integer.MAX_VALUE);

    public static final MapCodec<BlendedNoiseRepeating> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.BOOL.fieldOf("can_overflow").forGetter(instance1 -> instance1.canOverflow),
                            POSITIVE_INT_RANGE.fieldOf("repeat_start").forGetter(instance1 -> instance1.repeatStart),
                            POSITIVE_INT_RANGE.fieldOf("repeat_length").forGetter(instance1 -> instance1.repeatLength),
                            POSITIVE_INT_RANGE.fieldOf("repeat_count").forGetter(instance1 -> instance1.repeatCount),
                            SCALE_RANGE.fieldOf("xz_scale").forGetter(instance1 -> instance1.xzScale),
                            SCALE_RANGE.fieldOf("y_scale").forGetter(instance1 -> instance1.yScale),
                            SCALE_RANGE.fieldOf("xz_factor").forGetter(instance1 -> instance1.xzFactor),
                            SCALE_RANGE.fieldOf("y_factor").forGetter(instance1 -> instance1.yFactor),
                            Codec.doubleRange(1.0, 8.0).fieldOf("smear_scale_multiplier").forGetter(instance1 -> instance1.smearScaleMultiplier)
                    )
                    .apply(instance, BlendedNoiseRepeating::createUnseeded)
    );

    public BlendedNoiseRepeating(RandomSource random, double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier, boolean canOverflow, int repeatStart, int repeatLength, int repeatCount) {
        super(random, xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier);
        this.canOverflow = canOverflow;
        this.repeatStart = repeatStart;
        this.repeatLength = repeatLength;
        this.repeatCount = repeatCount;
    }

    public static BlendedNoiseRepeating createUnseeded(boolean canOverflow, int repeatStart, int repeatLength, int repeatCount, double xzScale, double yScale, double xzFactor, double yFactor, double smearScaleMultiplier) {
        return new BlendedNoiseRepeating(new XoroshiroRandomSource(0L), xzScale, yScale, xzFactor, yFactor, smearScaleMultiplier, canOverflow, repeatStart, repeatLength, repeatCount);
    }

    @Override
    public BlendedNoiseOverflowable withNewRandom(RandomSource random) {
        return new BlendedNoiseRepeating(random, this.xzScale, this.yScale, this.xzFactor, this.yFactor, this.smearScaleMultiplier, this.canOverflow, this.repeatStart, this.repeatLength, this.repeatCount);
    }

    /**
     * 计算指定坐标处的噪声值。实际上只是将计算的坐标修改为循环模式下的坐标，并调用原版的 {@link BlendedNoise#compute(FunctionContext)} 的方法计算。
     * @param context 计算上下文，由密度函数调用器提供
     * @return 指定坐标处的噪声值
     */
    @Override
    public double compute(FunctionContext context) {
        int sectionLength = this.repeatLength * this.repeatCount;

        int x = context.blockX();
        if (Math.abs(x) >= repeatStart) {
            int a = x - repeatStart;
            x = a % repeatLength + repeatStart + (a / sectionLength) * sectionLength;
        }

        int y = context.blockY();
        if (Math.abs(y) > repeatStart) {
            int a = y - repeatStart;
            y = a % repeatLength + repeatStart + (a / sectionLength) * sectionLength;
        }

        int z = context.blockZ();
        if (Math.abs(z) > repeatStart) {
            int a = z - repeatStart;
            z = a % repeatLength + repeatStart + (a / sectionLength) * sectionLength;
        }

        context = new SinglePointContext(x, y, z);
        return super.compute(context);
    }
}
