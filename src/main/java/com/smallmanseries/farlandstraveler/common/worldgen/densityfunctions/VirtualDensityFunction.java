package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

/**
 * <p>
 * 注意：该功能尚未完成，完成后移除本段
 * <p>
 * 这是个“虚密度函数”，在噪声设置（noise_settings）定义文件中充当一个密度函数使用，作用是捕获指定噪声设置（noise_settings）中的指定密度函数。
 * <p>
 * 用法：{
 *     "type": "farlandstraveler:virtual_density_function",
 *     "settings": "要捕获的噪声设置",
 *     "function": "要捕获的密度函数"
 *     }
 * @param settings 要捕获的噪声设置
 * @param function 要捕获的密度函数
 */

public record VirtualDensityFunction (Holder<NoiseGeneratorSettings> settings, String function) implements DensityFunction.SimpleFunction  {

    public static final MapCodec<VirtualDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(VirtualDensityFunction::settings),
                    Codec.STRING.fieldOf("function").forGetter(VirtualDensityFunction::function)
                    )
                    .apply(instance, VirtualDensityFunction::new)
    );
    public static final KeyDispatchDataCodec<VirtualDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);


    @Override
    public double compute(FunctionContext functionContext) {
        double output = 0;
        switch (function){
            case "barrier" -> output = this.settings.value().noiseRouter().barrierNoise().compute(functionContext);
            case "fluid_level_floodedness" -> output = this.settings.value().noiseRouter().fluidLevelFloodednessNoise().compute(functionContext);
            case "fluid_level_spread" -> output = this.settings.value().noiseRouter().fluidLevelSpreadNoise().compute(functionContext);
            case "lava" -> output = this.settings.value().noiseRouter().lavaNoise().compute(functionContext);
            case "temperature" -> output = this.settings.value().noiseRouter().temperature().compute(functionContext);
            case "vegetation" -> output = this.settings.value().noiseRouter().vegetation().compute(functionContext);
            case "continents" -> output = this.settings.value().noiseRouter().continents().compute(functionContext);
            case "erosion" -> output = this.settings.value().noiseRouter().erosion().compute(functionContext);
            case "depth" -> output = this.settings.value().noiseRouter().depth().compute(functionContext);
            case "ridges" -> output = this.settings.value().noiseRouter().ridges().compute(functionContext);
            case "initial_density_without_jaggedness" -> output = this.settings.value().noiseRouter().initialDensityWithoutJaggedness().compute(functionContext);
            case "final_density" -> output = this.settings.value().noiseRouter().finalDensity().compute(functionContext);
            case "vein_toggle" -> output = this.settings.value().noiseRouter().veinToggle().compute(functionContext);
            case "vein_ridged" -> output = this.settings.value().noiseRouter().veinRidged().compute(functionContext);
            case "vein_gap" -> output = this.settings.value().noiseRouter().veinGap().compute(functionContext);
        }
        return output;
    }

    @Override
    public double minValue() {
        double output = 0;
        switch (function){
            case "barrier" -> output = this.settings.value().noiseRouter().barrierNoise().minValue();
            case "fluid_level_floodedness" -> output = this.settings.value().noiseRouter().fluidLevelFloodednessNoise().minValue();
            case "fluid_level_spread" -> output = this.settings.value().noiseRouter().fluidLevelSpreadNoise().minValue();
            case "lava" -> output = this.settings.value().noiseRouter().lavaNoise().minValue();
            case "temperature" -> output = this.settings.value().noiseRouter().temperature().minValue();
            case "vegetation" -> output = this.settings.value().noiseRouter().vegetation().minValue();
            case "continents" -> output = this.settings.value().noiseRouter().continents().minValue();
            case "erosion" -> output = this.settings.value().noiseRouter().erosion().minValue();
            case "depth" -> output = this.settings.value().noiseRouter().depth().minValue();
            case "ridges" -> output = this.settings.value().noiseRouter().ridges().minValue();
            case "initial_density_without_jaggedness" -> output = this.settings.value().noiseRouter().initialDensityWithoutJaggedness().minValue();
            case "final_density" -> output = this.settings.value().noiseRouter().finalDensity().minValue();
            case "vein_toggle" -> output = this.settings.value().noiseRouter().veinToggle().minValue();
            case "vein_ridged" -> output = this.settings.value().noiseRouter().veinRidged().minValue();
            case "vein_gap" -> output = this.settings.value().noiseRouter().veinGap().minValue();
        }
        return output;
    }

    @Override
    public double maxValue() {
        double output = 1;
        switch (function){
            case "barrier" -> output = this.settings.value().noiseRouter().barrierNoise().maxValue();
            case "fluid_level_floodedness" -> output = this.settings.value().noiseRouter().fluidLevelFloodednessNoise().maxValue();
            case "fluid_level_spread" -> output = this.settings.value().noiseRouter().fluidLevelSpreadNoise().maxValue();
            case "lava" -> output = this.settings.value().noiseRouter().lavaNoise().maxValue();
            case "temperature" -> output = this.settings.value().noiseRouter().temperature().maxValue();
            case "vegetation" -> output = this.settings.value().noiseRouter().vegetation().maxValue();
            case "continents" -> output = this.settings.value().noiseRouter().continents().maxValue();
            case "erosion" -> output = this.settings.value().noiseRouter().erosion().maxValue();
            case "depth" -> output = this.settings.value().noiseRouter().depth().maxValue();
            case "ridges" -> output = this.settings.value().noiseRouter().ridges().maxValue();
            case "initial_density_without_jaggedness" -> output = this.settings.value().noiseRouter().initialDensityWithoutJaggedness().maxValue();
            case "final_density" -> output = this.settings.value().noiseRouter().finalDensity().maxValue();
            case "vein_toggle" -> output = this.settings.value().noiseRouter().veinToggle().maxValue();
            case "vein_ridged" -> output = this.settings.value().noiseRouter().veinRidged().maxValue();
            case "vein_gap" -> output = this.settings.value().noiseRouter().veinGap().maxValue();
        }
        return output;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
