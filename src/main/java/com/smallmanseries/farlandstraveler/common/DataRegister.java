package com.smallmanseries.farlandstraveler.common;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;

// 《注册机》
public class DataRegister{
    // 注册边境之地数据驱动定义文件
    public static final ResourceKey<Registry<FarLands>> FAR_LANDS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID,"worldgen/far_lands"));
    public static final ResourceKey<Registry<BiomeSource>> BIOME_SOURCE = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID,"worldgen/biome_source"));
    // 更多注册敬请期待~~~


}