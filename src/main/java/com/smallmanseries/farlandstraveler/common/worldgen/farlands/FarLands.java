package com.smallmanseries.farlandstraveler.common.worldgen.farlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.common.DataRegister;
import com.smallmanseries.farlandstraveler.common.worldgen.biomesources.BiomeSourceHolder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

/**
 * 边境之地数据驱动文件
 *
 * @param dimension 应用维度（暂未实现）
 * @param settings 噪声设置
 * @param biomeSource 生物群系源
 */


public record FarLands(
        String dimension,
        Holder<NoiseGeneratorSettings> settings,
        Holder<BiomeSource> biomeSource
) {

    // 编解码器
    /* json文件格式：
        {
            "dimension" : 生成维度。
            "settings" : 生成该边境之地使用的噪声设置（NoiseSettings）
            "biome_source" : 生成该边境之地使用的生物群系源（BiomeSource）
        }
     */
    public static final Codec<FarLands> CODEC = RecordCodecBuilder.create(
        farLandsInstance -> farLandsInstance.group(
                Codec.STRING.fieldOf("dimension").forGetter(FarLands::dimension),
                NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(FarLands::settings),
                BiomeSourceHolder.HOLDER_CODEC.fieldOf("biome_source").forGetter(FarLands::biomeSource)
        ).apply(farLandsInstance, farLandsInstance.stable(FarLands::new))
    );

    public static final ResourceKey<FarLands> FAR_LANDS = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "far_lands"));
    public static final ResourceKey<FarLands> OOTS_LABORATORY = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "oots_laboratory"));

}
