package com.smallmanseries.farlandstraveler.common.worldgen.farlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.common.DataRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public record FarLands(
        String dimension,
        Holder<NoiseGeneratorSettings> settings,
        BiomeSource biomeSource
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
                BiomeSource.CODEC.fieldOf("biome_source").forGetter(FarLands::biomeSource)
        ).apply(farLandsInstance, farLandsInstance.stable(FarLands::new))
    );

    // 默认边境之地定义文件
    public static final ResourceKey<FarLands> FAR_LANDS = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "far_lands")); //边境之地
    //遥远之地
    //边缘之墙
    //边缘之角（由于边缘之地的墙变种和角变种缩放不一致，必须使用不一样的噪声设置）
    //终焉之海+条纹之海+切片之海（都是基岩海，很合理）

}
