package com.smallmanseries.farlandstraveler.common.worldgen.farlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.common.DataRegister;
import net.minecraft.core.Holder;
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
        boolean isCorner,
        int dist,
        Holder<NoiseGeneratorSettings> settings,
        BiomeSource biomeSource
) {

    // 编解码器
    /* json文件格式：
        {
            "dimension" : 生成维度。
            "iscorner" : 填true或false，默认为false。决定该边境之地是边境之墙还是边境之角。其中边境之角会把边境之墙覆盖掉。
            "dist" : 边境之地开始生成的坐标。如果iscorner为false，则当x或z坐标有一个绝对值大于该值，就生成该边境之地。如果iscorner为true，则当x和z坐标的绝对值都大于该值时生成该边境之地。
            "settings" : 生成该边境之地使用的噪声设置（NoiseSettings）
            "biome_source" : 生成该边境之地使用的生物群系源（BiomeSource）
        }
     */
    public static final Codec<FarLands> CODEC = RecordCodecBuilder.create(
        farLandsInstance -> farLandsInstance.group(
                Codec.STRING.fieldOf("dimension").forGetter(FarLands::dimension),
                Codec.BOOL.optionalFieldOf("iscorner", false).forGetter(FarLands::isCorner),
                Codec.INT.fieldOf("dist").forGetter(FarLands::dist),
                NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(FarLands::settings),
                BiomeSource.CODEC.fieldOf("biome_source").forGetter(FarLands::biomeSource)
        ).apply(farLandsInstance, farLandsInstance.stable(FarLands::new))
    );

    // 默认边境之地定义文件
    public static final ResourceKey<FarLands> FAR_LANDS = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "far_lands")); //边境之地
    public static final ResourceKey<FarLands> FAR_LANDS_REPEATING = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "far_lands_repeating")); //循环边境之地
    public static final ResourceKey<FarLands> FARTHER_LANDS = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "farther_lands")); //遥远之地
    public static final ResourceKey<FarLands> FARTHER_LANDS_LSC = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "farther_lands_lsc")); //异光遥远之地（LSC是“Light System has Crashed，光照系统已崩溃"的缩写）
    public static final ResourceKey<FarLands> FRINGE_LANDS = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "fringe_lands")); //边缘之地
    public static final ResourceKey<FarLands> SEA_OF_END = ResourceKey.create(DataRegister.FAR_LANDS, ResourceLocation.fromNamespaceAndPath("farlandstraveler", "sea_of_end")); //终焉之海+条纹之海+切片之海（都是基岩海，很合理）


    // 获取该区块中生效的边境之地
    public static FarLands[] getFarLandsInChunk(ChunkPos pos){

        return new FarLands[0];
    }


    public static NoiseChunk[] createNoiseChunks(ChunkAccess chunk) {

        return new NoiseChunk[0];
    }
}
