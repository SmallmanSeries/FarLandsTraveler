package com.smallmanseries.farlandstraveler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTCreativeTabs;
import com.smallmanseries.farlandstraveler.common.worldgen.FLTDensityFunctions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.ramixin.mixson.inline.Mixson;
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

        Mixson.registerEvent(0, ResourceLocation.withDefaultNamespace("worldgen/noise_settings/overworld").toString(), "noiseRouterInjectorOverworld", (context) -> {
            // 获取noise_router
            JsonObject noiseRouterOverworld = context.getFile().getAsJsonObject().getAsJsonObject("noise_router");
            // 基于base_3d_noise的溢出切换final_density
            JsonElement finalDensity = noiseRouterOverworld.get("final_density");
            JsonObject finalDensityModified = new JsonObject();
            finalDensityModified.addProperty("type", "minecraft:range_choice");
            finalDensityModified.addProperty("input", "farlandstraveler:far_lands/far_lands_generation_check");
            finalDensityModified.addProperty("min_inclusive", -100);
            finalDensityModified.addProperty("max_exclusive", 100);
            finalDensityModified.add("when_in_range", finalDensity);
            finalDensityModified.addProperty("when_out_of_range", "farlandstraveler:far_lands/far_lands");
            //基于距离（distance）切换其他密度函数

            //应用修改后的noise_router
            noiseRouterOverworld.add("final_density", finalDensityModified);

        }, false);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            FLTBlocks.registerPots();
            FLTBlocks.registerFlammability();
        });
        //LOGGER.info("Far Lands Travelers, gather!");
    }

}
