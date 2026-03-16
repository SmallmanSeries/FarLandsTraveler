package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
* <p>{
* <p>"type": "farlandstraveler:nan_converter",
* <p>"input": 要处理的密度函数,
* <p>"convert_to": 要转换成的数
* <p>}
*/

public record NaNConverter(DensityFunction input, double convertTo) implements DensityFunction.SimpleFunction {
    public static final MapCodec<NaNConverter> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            DensityFunction.DIRECT_CODEC.fieldOf("input").forGetter(NaNConverter::input),
                            Codec.DOUBLE.fieldOf("convert_to").forGetter(NaNConverter::convertTo)
                    )
                    .apply(instance, NaNConverter::new)
    );
    public static final KeyDispatchDataCodec<NaNConverter> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);


    @Override
    public double compute(FunctionContext functionContext) {
        double result = input.compute(functionContext);
        return Double.isNaN(result) ? convertTo : result;
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return visitor.apply(new NaNConverter(this.input.mapAll(visitor), this.convertTo));
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
