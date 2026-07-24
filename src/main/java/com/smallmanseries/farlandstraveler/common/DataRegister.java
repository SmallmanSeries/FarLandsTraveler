package com.smallmanseries.farlandstraveler.common;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.SurfaceRules;

// 《注册机》
public class DataRegister {
    // 注册边境之地数据驱动定义文件
    public static final ResourceKey<Registry<FarLands>> FAR_LANDS = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "worldgen/far_lands"));
    public static final ResourceKey<Registry<BiomeSource>> BIOME_SOURCE = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "worldgen/biome_source"));
    // Todo 26.3及以后的版本，原版自带材料规则功能，本模组的此功能可以删除
    public static final ResourceKey<Registry<SurfaceRules.RuleSource>> MATERIAL_RULE = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "worldgen/material_rule"));
    // 更多注册敬请期待~~~
}
