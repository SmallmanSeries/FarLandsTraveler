package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 获取一个坐标随机数源（{@link PositionalRandomFactory}）在当前位置的 {@code nextDouble} 值
 * <p>
 * {
 * <p>
 * "type": "farlandstraveler:random_factory",
 * <p>
 * "dimension": 随机数源的源维度
 * <p>
 * "name": 随机数源的名称，如 {@code minecraft:bedrock_floor} 是基岩底板所用的随机数源
 * <p>
 * }
 * @author INF32768
 */
public record RandomFactoryFunction(ResourceKey<Level> dimension, ResourceLocation name) implements DensityFunction.SimpleFunction {
    static final KeyDispatchDataCodec<RandomFactoryFunction> CODEC = KeyDispatchDataCodec.of(
            RecordCodecBuilder.mapCodec(
                    instance -> instance.group(
                                    ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(RandomFactoryFunction::dimension),
                                    ResourceLocation.CODEC.fieldOf("name").forGetter(RandomFactoryFunction::name)
                            )
                            .apply(instance, RandomFactoryFunction::new)
            )
    );

    @Override
    public double compute(FunctionContext functionContext) {
        MinecraftServer server = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer());
        ServerLevel level = Objects.requireNonNull(server.getLevel(dimension));
        PositionalRandomFactory factory = level.getChunkSource().randomState().getOrCreateRandomFactory(name);
        return factory.at(functionContext.blockX(), functionContext.blockY(), functionContext.blockZ()).nextDouble();
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
    public @NotNull KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
