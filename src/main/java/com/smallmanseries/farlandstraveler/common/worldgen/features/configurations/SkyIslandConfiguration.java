package com.smallmanseries.farlandstraveler.common.worldgen.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * “空岛”地物的配置
 * <p>
 * 生成一个空中的岛屿，类似于末地的小型末地石空岛。
 *
 * @param surfaceProvider 地面的方块提供器
 * @param fillerProvider  岛体的方块提供器
 * @param radius          顶面半径
 * @param depth           岛心最厚处有多厚，向外按二次曲线收薄成一圈 1 格厚的边缘
 */
public record SkyIslandConfiguration(
        BlockStateProvider surfaceProvider,
        BlockStateProvider fillerProvider,
        IntProvider radius,
        IntProvider depth,
        BlockPredicate canPlaceOn
) implements FeatureConfiguration {
    public static final Codec<SkyIslandConfiguration> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    BlockStateProvider.CODEC.fieldOf("surface_provider").forGetter(SkyIslandConfiguration::surfaceProvider),
                    BlockStateProvider.CODEC.fieldOf("filler_provider").forGetter(SkyIslandConfiguration::fillerProvider),
                    IntProviders.codec(1, 16).fieldOf("radius").forGetter(SkyIslandConfiguration::radius),
                    IntProviders.codec(1, 16).fieldOf("depth").forGetter(SkyIslandConfiguration::depth),
                    BlockPredicate.CODEC.fieldOf("can_place_on").forGetter(SkyIslandConfiguration::canPlaceOn)
            ).apply(inst, SkyIslandConfiguration::new)
    );
}
