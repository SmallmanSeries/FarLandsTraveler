package com.smallmanseries.farlandstraveler;

import com.mojang.logging.LogUtils;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTCreativeTabs;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.DataRegisterFarLands;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

@Mod(FarLandsTraveler.MODID)
public class FarLandsTraveler {
    public static final String MODID = "farlandstraveler";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FarLandsTraveler(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        /*
        FLTItems.registerItems(modEventBus);
        FLTCreativeTabs.registerCreativeTabs(modEventBus);
         */

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        //LOGGER.info("Far Lands Travelers, gather!");
    }

}
