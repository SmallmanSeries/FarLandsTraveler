package com.smallmanseries.farlandstraveler;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = FarLandsTraveler.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = FarLandsTraveler.MODID, value = Dist.CLIENT)
public class FarLandsTravelerClient {
    public FarLandsTravelerClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new); //添加配置屏幕
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        FarLandsTraveler.LOGGER.info("Far Lands Travelers, set off!"); //边境旅者，启程！
    }
}
