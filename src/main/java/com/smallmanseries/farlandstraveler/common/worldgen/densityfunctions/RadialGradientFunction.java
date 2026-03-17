package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
 * 选择一个圆形区域并进行径向渐变，也可以当圆形选区使用
 * <p>
 *     {
 *     <p>
 *         "type": "farlandstraveler:radial_gradient",
 *         <p>
 *         "center_x": 圆心的x坐标,
 *         <p>
 *         "center_z": 圆心的z坐标,
 *         <p>
 *         "inner_radius": 内径/内色标的位置,
 *         <p>
 *         "outer_radius": 外径/外色标的位置,
 *         <p>
 *         "inner_value": 内色标的值,
 *         <p>
 *         "outer_value": 外色标的值
 *         <p>
 *     }
 */

public record RadialGradientFunction(double centerX, double centerZ, double innerRadius, double outerRadius, double innerValue, double outerValue) implements DensityFunction.SimpleFunction {
    private static final Codec<Double> INPUT_RANGE = Codec.doubleRange(0.0, Double.MAX_VALUE);
    public static final MapCodec<RadialGradientFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.DOUBLE.fieldOf("center_x").forGetter(RadialGradientFunction::centerX),
                            Codec.DOUBLE.fieldOf("center_z").forGetter(RadialGradientFunction::centerZ),
                            INPUT_RANGE.fieldOf("inner_radius").forGetter(RadialGradientFunction::innerRadius),
                            INPUT_RANGE.fieldOf("outer_radius").forGetter(RadialGradientFunction::outerRadius),
                            Codec.DOUBLE.fieldOf("inner_value").forGetter(RadialGradientFunction::innerValue),
                            Codec.DOUBLE.fieldOf("outer_value").forGetter(RadialGradientFunction::outerValue)
                    )
                    .apply(instance, RadialGradientFunction::new)
    );
    public static final KeyDispatchDataCodec<RadialGradientFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    @Override
    public double compute(FunctionContext functionContext) {
        double innerRadiusPow = innerRadius * innerRadius;
        double outerRadiusPow = outerRadius * outerRadius;
        double dp = (functionContext.blockX() - centerX) * (functionContext.blockX() - centerX) + (functionContext.blockZ() - centerZ) * (functionContext.blockZ() - centerZ);
        if (dp >= outerRadiusPow) return outerValue;
        if (dp <= innerRadiusPow) return innerValue;

        return innerValue + (outerValue - innerValue) * (dp - innerRadiusPow) / (outerRadiusPow - innerRadiusPow);
    }

    @Override
    public double minValue() {
        return 0;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
