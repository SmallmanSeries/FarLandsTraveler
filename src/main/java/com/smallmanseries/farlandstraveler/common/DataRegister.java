package com.smallmanseries.farlandstraveler.common;

import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

// 《注册机》
public class DataRegister{
    // 注册边境之地数据驱动定义文件
    public static final ResourceKey<Registry<FarLands>> FAR_LANDS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("farlandstraveler","worldgen/far_lands"));
    // 更多注册敬请期待~~~


}