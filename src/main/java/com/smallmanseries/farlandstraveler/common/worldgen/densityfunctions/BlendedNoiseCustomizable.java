package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.mixin.worldgen.BlendedNoiseMixin;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

import java.util.Optional;

/**
 * “高级”混合噪声，用于创建可生成边境之地/可循环的自定义噪声。由 {@link BlendedNoiseMixin} 基于修改原版的 {@link BlendedNoise} 类来实现。
 * <p>{
 * <p>"type": "farlandstraveler:old_blended_noise_customizable",
 * <p>"x_scale": X轴平面比例,
 * <p>"y_scale": 高度比例,
 * <p>"x_scale": Z轴平面比例,
 * <p>"x_factor": 主地形差异值X,
 * <p>"y_factor": 主地形差异值Y,
 * <p>"z_factor": 主地形差异值Z,
 * <p>"smear_scale_multiplier": 与噪声在Y轴上的采样有关,
 * <p>"overflowable": （可选）布尔值，噪声是否可以32位溢出，默认为false,
 * <p>"repeat_start": （可选）循环开始坐标，必须为正整数。
 * <p>"repeat_length": （可选）单个循环区域内每个循环节的长度，必须为正整数,
 * <p>"repeat_count": （可选）每个循环区域内循环节的数量。必须为正整数,
 * <p>"x_shift": （可选）噪声在x轴上的偏移量，默认为0,
 * <p>"y_shift": （可选）噪声在y轴上的偏移量，默认为0,
 * <p>"z_shift": （可选）噪声在z轴上的偏移量，默认为0,
 * <p>}
 * <p>注："repeat_start"和"repeat_length"建议为 8 的倍数，否则后续的线性插值可能破坏循环模式。
 * <p>注2："repeat_start""repeat_length"和"repeat_count"三者都存在时，才会应用循环
 *
 * @author Osloras Ki
 * @author INF32768
 */
public class BlendedNoiseCustomizable extends BlendedNoise{
    protected static final Codec<Double> SCALE_RANGE = Codec.doubleRange(0.001, Double.MAX_VALUE);
    private static final Codec<Integer> POSITIVE_INT_RANGE = Codec.intRange(1, Integer.MAX_VALUE);
    public static final MapCodec<BlendedNoiseCustomizable> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SCALE_RANGE.fieldOf("x_scale").forGetter(instance1 -> instance1.xzScale),
                            SCALE_RANGE.fieldOf("y_scale").forGetter(instance2 -> instance2.yScale),
                            SCALE_RANGE.fieldOf("z_scale").forGetter(instance1 -> instance1.zScale),
                            SCALE_RANGE.fieldOf("x_factor").forGetter(instance3 -> instance3.xzFactor),
                            SCALE_RANGE.fieldOf("y_factor").forGetter(instance4 -> instance4.yFactor),
                            SCALE_RANGE.fieldOf("z_factor").forGetter(instance3 -> instance3.zFactor),
                            Codec.doubleRange(1.0, 8.0).fieldOf("smear_scale_multiplier").forGetter(instance5 -> instance5.smearScaleMultiplier),
                            Codec.BOOL.optionalFieldOf("overflowable", false).forGetter(instance6 -> instance6.overflowable),
                            POSITIVE_INT_RANGE.optionalFieldOf("repeat_start").forGetter(instance7 -> instance7.repeatStart),
                            POSITIVE_INT_RANGE.optionalFieldOf("repeat_length").forGetter(instance8 -> instance8.repeatLength),
                            POSITIVE_INT_RANGE.optionalFieldOf("repeat_count").forGetter(instance9 -> instance9.repeatCount),
                            Codec.DOUBLE.optionalFieldOf("x_shift", 0.0).forGetter(instance10 -> instance10.xShift),
                            Codec.DOUBLE.optionalFieldOf("y_shift", 0.0).forGetter(instance11 -> instance11.yShift),
                            Codec.DOUBLE.optionalFieldOf("z_shift", 0.0).forGetter(instance12 -> instance12.zShift)
                    )
                    .apply(instance, BlendedNoiseCustomizable::createUnseeded)
    );

    /**
     * 是否可溢出。若为 true，则噪声会在坐标为 2^31 ÷ xzScale ÷ 684.412 的位置溢出，形成边境之地。
     * <p>
     * 由 {@link com.smallmanseries.farlandstraveler.mixin.worldgen.BlendedNoiseMixin} 根据此变量修改原版类实现溢出。
     */
    public final boolean overflowable;
    /**
     * 循环起点。必须为正数。当任意一轴的坐标的绝对值大于此值时，该轴上的噪声值将开始按照设定的模式进行循环。
     * <p>
     * 建议为 8 的倍数，否则后续的线性插值可能破坏循环模式。
     */
    private final Optional<Integer> repeatStart;
    /**
     * 单个循环区域内每个循环节的长度。必须为正数。
     * <p>
     * 建议为 8 的倍数，否则后续的线性插值可能破坏循环模式。
     */
    private final Optional<Integer> repeatLength;
    /**
     * 每个循环区域内循环节的数量。必须为正数。
     */
    private final Optional<Integer> repeatCount;

    public final double zScale;
    public final double zFactor;
    public final double zMultiplier;

    public final double xShift;
    public final double yShift;
    public final double zShift;

    public BlendedNoiseCustomizable(RandomSource random, double xScale, double yScale, double zScale, double xFactor, double yFactor, double zFactor, double smearScaleMultiplier, boolean overflowable, Optional<Integer> repeatStart, Optional<Integer> repeatLength, Optional<Integer> repeatCount, double xShift, double yShift, double zShift) {
        super(random, xScale, yScale, xFactor, yFactor, smearScaleMultiplier);
        this.overflowable = overflowable;
        this.repeatStart = repeatStart;
        this.repeatLength = repeatLength;
        this.repeatCount = repeatCount;
        this.zScale = zScale;
        this.zFactor = zFactor;
        this.zMultiplier = 684.412 * this.zScale;
        this.xShift = xShift;
        this.yShift = yShift;
        this.zShift = zShift;
    }

    public static BlendedNoiseCustomizable createUnseeded(double xScale, double yScale, double zScale, double xFactor, double yFactor, double zFactor, double smearScaleMultiplier, boolean overflowable, Optional<Integer> repeatStart, Optional<Integer> repeatLength, Optional<Integer> repeatCount, double xShift, double yShift, double zShift) {
        return new BlendedNoiseCustomizable(new XoroshiroRandomSource(0L), xScale, yScale, zScale, xFactor, yFactor, zFactor, smearScaleMultiplier, overflowable, repeatStart, repeatLength, repeatCount, xShift, yShift, zShift);
    }

    @Override
    public BlendedNoiseCustomizable withNewRandom(RandomSource random) {
        return new BlendedNoiseCustomizable(random, this.xzScale, this.yScale, this.zScale, this.xzFactor, this.yFactor, this.zFactor, this.smearScaleMultiplier, this.overflowable, this.repeatStart, this.repeatLength, this.repeatCount, this.xShift, this.yShift, this.zShift);
    }

    /**
     * 计算指定坐标处的噪声值。实际上只是将计算的坐标修改为循环模式下的坐标，并调用原版的 {@link BlendedNoise#compute(FunctionContext)} 的方法计算。
     * @param context 计算上下文，由密度函数调用器提供
     * @return 指定坐标处的噪声值
     */
    @Override
    public double compute(FunctionContext context) {
        if (this.repeatStart.isPresent() && this.repeatLength.isPresent() && this.repeatCount.isPresent()) {
            int repeatStart = this.repeatStart.get();
            int repeatLength = this.repeatLength.get();
            int sectionLength = repeatLength * this.repeatCount.get();

            int x = Math.abs(context.blockX());
            if (x >= repeatStart) {
                int a = x - repeatStart;
                x = a % repeatLength + repeatStart + (a / sectionLength) * sectionLength;
            }
            x *= context.blockX() < 0 ? -1 : 1;

            int y = Math.abs(context.blockY());
            if (y > repeatStart) {
                int a = y - repeatStart;
                y = a % repeatLength + repeatStart + (a / sectionLength) * sectionLength;
            }
            y *= context.blockY() < 0 ? -1 : 1;

            int z = Math.abs(context.blockZ());
            if (z > repeatStart) {
                int a = z - repeatStart;
                z = a % repeatLength + repeatStart + (a / sectionLength) * sectionLength;
            }
            z *= context.blockZ() < 0 ? -1 : 1;

            context = new SinglePointContext(x, y, z);
        }
        return super.compute(context);
    }
}
