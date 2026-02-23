package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.slf4j.Logger;

/**
 * <p>
 * 注意：该功能尚未完成，完成后移除本段
 * <p>
 * 这是个“虚最终密度”，在噪声设置（noise_settings）定义文件中充当一个密度函数使用，作用是捕获指定噪声设置（noise_settings）中的最终密度（final_density）。
 * <p>
 * 用法：{
 *     "type": "farlandstraveler:virtual_density_function",
 *     "settings": "要捕获的噪声设置"
 *     }
 * @param settings 要捕获的噪声设置
 */

public record VirtualDensityFunction ( Holder<NoiseGeneratorSettings> settings ) implements DensityFunction.SimpleFunction  {

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final MapCodec<VirtualDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(VirtualDensityFunction::settings)
                    )
                    .apply(instance, VirtualDensityFunction::new)
    );
    public static final KeyDispatchDataCodec<VirtualDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);


    @Override
    public double compute(FunctionContext functionContext) {
        return this.settings.value().noiseRouter().finalDensity().compute(functionContext);
    }

    @Override
    public double minValue() {
        return this.settings.value().noiseRouter().finalDensity().minValue();
    }

    @Override
    public double maxValue() {
        return this.settings.value().noiseRouter().finalDensity().maxValue();
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
