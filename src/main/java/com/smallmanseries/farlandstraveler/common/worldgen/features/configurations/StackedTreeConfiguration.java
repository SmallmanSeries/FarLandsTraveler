package com.smallmanseries.farlandstraveler.common.worldgen.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.stream.Stream;

/**
 * "树上树"地物的配置
 * <p>
 * 其实不仅限于“树”，任何地物都可以进行叠加。
 *
 * @param base          底部地物
 * @param top           顶部地物
 * @param maxScanHeight 从原点向上最多扫描多少格来找顶部
 */
public record StackedTreeConfiguration(
        Holder<ConfiguredFeature<?, ?>> base,
        Holder<ConfiguredFeature<?, ?>> top,
        int maxScanHeight
) implements FeatureConfiguration {
    public static final Codec<StackedTreeConfiguration> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    ConfiguredFeature.CODEC.fieldOf("base").forGetter(StackedTreeConfiguration::base),
                    ConfiguredFeature.CODEC.fieldOf("top").forGetter(StackedTreeConfiguration::top),
                    Codec.intRange(1, 256).optionalFieldOf("max_scan_height", 48).forGetter(StackedTreeConfiguration::maxScanHeight)
            ).apply(inst, StackedTreeConfiguration::new)
    );

    @Override
    public Stream<Holder<ConfiguredFeature<?, ?>>> getSubFeatures() {
        return Stream.of(this.base, this.top);
    }
}
