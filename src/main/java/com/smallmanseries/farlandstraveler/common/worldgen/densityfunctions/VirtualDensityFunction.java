package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.slf4j.Logger;

public record VirtualDensityFunction ( Holder<NoiseGeneratorSettings> settings ) implements DensityFunction  {

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
    public void fillArray(double[] doubles, ContextProvider contextProvider) {
        this.settings.value().noiseRouter().finalDensity().fillArray(doubles, contextProvider);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return this.settings.value().noiseRouter().finalDensity().mapAll(visitor);
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
