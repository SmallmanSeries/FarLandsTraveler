package com.smallmanseries.farlandstraveler.common.worldgen.farlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.DataRegister;
import com.smallmanseries.farlandstraveler.common.worldgen.biomesources.BiomeSourceHolder;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

/**
 * 边境之地数据驱动文件
 *
 * @param dimension   应用维度（暂未实现）
 * @param settings    噪声设置
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

    public static final ResourceKey<FarLands> FAR_LANDS = ResourceKey.create(DataRegister.FAR_LANDS, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "far_lands"));
    public static final ResourceKey<FarLands> OOTS_LABORATORY = ResourceKey.create(DataRegister.FAR_LANDS, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "oots_laboratory"));

    /**
     * 判断一个位置是否在边境之地内部
     *
     * @param x             x坐标
     * @param y             y坐标
     * @param z             z坐标
     * @param positiveShift 一般情况下，正半轴的边境之地地形会比负半轴提前生成几格。这个数值用于抵消掉提前的这几格。
     * @return 布尔值，该位置是否位于边境之地
     */
    public static boolean isInFarLands(double x, double y, double z, int positiveShift) {
        return Math.max(x, z) > (Config.FAR_LANDS_DISTANCE.getAsInt() - positiveShift) || Math.min(x, z) < -Config.FAR_LANDS_DISTANCE.getAsInt();
    }
}
