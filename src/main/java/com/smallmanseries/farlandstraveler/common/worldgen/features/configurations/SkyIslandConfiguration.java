package com.smallmanseries.farlandstraveler.common.worldgen.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * “空岛”地物的配置
 * <p>
 * 生成一个空中的岛屿，类似于末地的小型末地石空岛。
 * <p>
 * 26.1.2 没有 {@code sequence} 地物，因此用本配置的 {@code decorations} 字段模拟一个地物序列：
 * 空岛放置完毕之后，依次把 {@code decorations} 中的地物按照几率全部放置一遍。
 * 于是“空岛 + 岛上的东西”可以拆成两个独立的地物组合出来，而不是把所有内容都塞进空岛地物里。
 *
 * @param surfaceProvider     地面的方块提供器
 * @param fillerProvider      岛体的方块提供器
 * @param radius              顶面半径
 * @param depth               岛心最厚处有多厚，向外按二次曲线收薄成一圈 1 格厚的边缘
 * @param canPlaceOn          托举条件：岛底往下 {@code depth} 格内出现这个方块才生成（例如擎天树的树叶）
 * @param pondStateProvider   水塘的方块，留空则不生成水塘
 * @param decorations         装饰地物列表，空岛放置完后会尝试根据概率放置列表中的所有地物，留空表示岛面上什么都不放
 */
public record SkyIslandConfiguration(
        BlockStateProvider surfaceProvider,
        BlockStateProvider fillerProvider,
        IntProvider radius,
        IntProvider depth,
        BlockPredicate canPlaceOn,
        Optional<BlockStateProvider> pondStateProvider,
        Optional<List<WeightedPlacedFeature>> decorations
) implements FeatureConfiguration {
    public static final Codec<SkyIslandConfiguration> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    BlockStateProvider.CODEC.fieldOf("surface_provider").forGetter(SkyIslandConfiguration::surfaceProvider),
                    BlockStateProvider.CODEC.fieldOf("filler_provider").forGetter(SkyIslandConfiguration::fillerProvider),
                    IntProviders.codec(1, 16).fieldOf("radius").forGetter(SkyIslandConfiguration::radius),
                    IntProviders.codec(1, 16).fieldOf("depth").forGetter(SkyIslandConfiguration::depth),
                    BlockPredicate.CODEC.fieldOf("can_place_on").forGetter(SkyIslandConfiguration::canPlaceOn),
                    BlockStateProvider.CODEC.optionalFieldOf("pond_provider").forGetter(SkyIslandConfiguration::pondStateProvider),
                    WeightedPlacedFeature.CODEC.listOf().optionalFieldOf("decorations").forGetter(SkyIslandConfiguration::decorations)
            ).apply(inst, SkyIslandConfiguration::new)
    );

    @Override
    public Stream<Holder<ConfiguredFeature<?, ?>>> getSubFeatures() {
        return this.decorations.orElse(List.of()).stream().flatMap(weighted -> weighted.feature.value().getFeatures());
    }
}
