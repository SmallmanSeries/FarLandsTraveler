package com.smallmanseries.farlandstraveler.server.worldgen.farlands;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;


public class DataRegisterFarLands {
    // 注册边境之地数据驱动定义文件
    public static final ResourceKey<Registry<FarLands>> FAR_LANDS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("farlandstraveler","worldgen/far_lands"));

    @SubscribeEvent
    public static void registerData(DataPackRegistryEvent.NewRegistry event){
        event.dataPackRegistry(FAR_LANDS, FarLands.CODEC);
    }
}