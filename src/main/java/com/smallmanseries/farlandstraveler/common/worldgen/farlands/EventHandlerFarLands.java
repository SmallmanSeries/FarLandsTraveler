package com.smallmanseries.farlandstraveler.common.worldgen.farlands;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = FarLandsTraveler.MODID)
public class EventHandlerFarLands {
    // 开始订阅事件
    @SubscribeEvent
    public static void registerData(DataPackRegistryEvent.NewRegistry event){
        event.dataPackRegistry(DataRegisterFarLands.FAR_LANDS, FarLands.CODEC);
    }
}
