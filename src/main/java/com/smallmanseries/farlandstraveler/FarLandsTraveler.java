package com.smallmanseries.farlandstraveler;

import com.mojang.logging.LogUtils;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTCreativeTabs;
import com.smallmanseries.farlandstraveler.common.worldgen.FLTDensityFunctions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(FarLandsTraveler.MODID)
public class FarLandsTraveler {
    public static final String MODID = "farlandstraveler";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FarLandsTraveler(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        FLTBlocks.BLOCKS.register(modEventBus);
        FLTItems.ITEMS.register(modEventBus);
        FLTCreativeTabs.TABS.register(modEventBus);
        FLTDensityFunctions.FUNCTIONS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            FLTBlocks.registerPots();
            FLTBlocks.registerFlammability();
        });
        //LOGGER.info("Far Lands Travelers, gather!");
    }

}
