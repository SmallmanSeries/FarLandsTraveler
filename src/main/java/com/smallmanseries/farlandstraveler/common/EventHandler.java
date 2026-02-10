package com.smallmanseries.farlandstraveler.common;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = FarLandsTraveler.MODID)
public class EventHandler {
    // 注册边境之地数据驱动文件
    @SubscribeEvent
    public static void registerData(DataPackRegistryEvent.NewRegistry event){
        event.dataPackRegistry(DataRegister.FAR_LANDS, FarLands.CODEC);
    }
}
