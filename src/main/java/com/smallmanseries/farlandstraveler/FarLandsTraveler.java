package com.smallmanseries.farlandstraveler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import com.smallmanseries.farlandstraveler.common.misc.FLTCreativeTabs;
import com.smallmanseries.farlandstraveler.common.worldgen.FLTDensityFunctions;
import net.minecraft.resources.ResourceLocation;
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
        FLTAttachments.ATTACHMENT_TYPES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        Mixson.registerEvent(0, ResourceLocation.withDefaultNamespace("worldgen/noise_settings/overworld").toString(), "noiseRouterInjectorOverworld", (context) -> {
            // 获取noise_router
            JsonObject noiseRouterOverworld = context.getFile().getAsJsonObject().getAsJsonObject("noise_router");
            // 基于base_3d_noise的溢出切换final_density
            JsonElement finalDensity = noiseRouterOverworld.get("final_density");
            JsonObject overflowCheck = new JsonObject();
            overflowCheck.addProperty("type", "minecraft:range_choice");
            overflowCheck.addProperty("input", "farlandstraveler:far_lands/far_lands_generation_check");
            overflowCheck.addProperty("min_inclusive", -100);
            overflowCheck.addProperty("max_exclusive", 100);
            overflowCheck.add("when_in_range", finalDensity);
            overflowCheck.addProperty("when_out_of_range", "farlandstraveler:far_lands/final_density");
            JsonObject boxSelect = new JsonObject();
            boxSelect.addProperty("type", "farlandstraveler:box_select");
            boxSelect.addProperty("invert", true);
            boxSelect.addProperty("origin_x", -12550824);
            boxSelect.addProperty("origin_y", -25101648);
            boxSelect.addProperty("origin_z", -12550824);
            boxSelect.addProperty("extend_x", 25101649);
            boxSelect.addProperty("extend_y", 50203297);
            boxSelect.addProperty("extend_z", 25101649);
            JsonObject finalDensityModified = new JsonObject();
            finalDensityModified.addProperty("type", "minecraft:range_choice");
            finalDensityModified.add("input", boxSelect);
            finalDensityModified.addProperty("min_inclusive", 0);
            finalDensityModified.addProperty("max_exclusive", 1);
            finalDensityModified.add("when_in_range", overflowCheck);
            finalDensityModified.addProperty("when_out_of_range", "farlandstraveler:far_lands/final_density");

            //基于框选（box_select）切换其他密度函数
            JsonElement initialDensity = noiseRouterOverworld.get("initial_density_without_jaggedness");
            boxSelect = new JsonObject();
            boxSelect.addProperty("type", "farlandstraveler:box_select");
            boxSelect.addProperty("invert", true);
            boxSelect.addProperty("origin_x", -12550824);
            boxSelect.addProperty("origin_y", -25101648);
            boxSelect.addProperty("origin_z", -12550824);
            boxSelect.addProperty("extend_x", 25101645);
            boxSelect.addProperty("extend_y", 50203297);
            boxSelect.addProperty("extend_z", 25101645);
            JsonObject initialDensityModified = new JsonObject();
            initialDensityModified.addProperty("type", "minecraft:range_choice");
            initialDensityModified.add("input", boxSelect);
            initialDensityModified.addProperty("min_inclusive", 0);
            initialDensityModified.addProperty("max_exclusive", 1);
            initialDensityModified.add("when_in_range", initialDensity);
            initialDensityModified.addProperty("when_out_of_range", "farlandstraveler:far_lands/initial_density_without_jaggedness");

            //应用修改后的noise_router
            noiseRouterOverworld.add("final_density", finalDensityModified);
            noiseRouterOverworld.add("initial_density_without_jaggedness", initialDensityModified);

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
