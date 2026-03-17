package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
 * 将密度函数计算结果中的NaN转换成指定的数值
 * <p>{
 * <p>"type": "farlandstraveler:nan_converter",
 * <p>"input": 要处理的密度函数,
 * <p>"convert_to": 要转换成的数
 * <p>}
 */

public record NaNConverterFunction(DensityFunction input, double convertTo) implements DensityFunction.SimpleFunction {
    public static final MapCodec<NaNConverterFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            DensityFunction.DIRECT_CODEC.fieldOf("input").forGetter(NaNConverterFunction::input),
                            Codec.DOUBLE.fieldOf("convert_to").forGetter(NaNConverterFunction::convertTo)
                    )
                    .apply(instance, NaNConverterFunction::new)
    );
    public static final KeyDispatchDataCodec<NaNConverterFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);


    @Override
    public double compute(FunctionContext functionContext) {
        double result = input.compute(functionContext);
        return Double.isNaN(result) ? convertTo : result;
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return visitor.apply(new NaNConverterFunction(this.input.mapAll(visitor), this.convertTo));
    }

    @Override
    public double minValue() {
        return this.convertTo;
    }

    @Override
    public double maxValue() {
        return this.convertTo;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
