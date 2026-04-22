package com.smallmanseries.farlandstraveler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import com.smallmanseries.farlandstraveler.common.misc.FLTCreativeTabs;
import com.smallmanseries.farlandstraveler.common.worldgen.biomesources.FLTBiomeSources;
import com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions.FLTDensityFunctions;
import com.smallmanseries.farlandstraveler.common.worldgen.structure.FLTStructurePieceType;
import com.smallmanseries.farlandstraveler.common.worldgen.structure.FLTStructures;
import com.smallmanseries.farlandstraveler.common.worldgen.structure.placement.FLTStructurePlacements;
import net.minecraft.core.SectionPos;
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
        FLTBiomeSources.BIOME_SOURCES.register(modEventBus);
        FLTAttachments.ATTACHMENT_TYPES.register(modEventBus);
        FLTSoundEvents.SOUNDS.register(modEventBus);
        FLTStructurePlacements.STRUCTURE_PLACEMENTS.register(modEventBus);
        FLTStructures.STRUCTURES.register(modEventBus);
        FLTStructurePieceType.STRUCTURE_PIECES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        DataInjectors.noiseRouterInjector("worldgen/noise_settings/overworld", "noiseRouterInjectorNormal");
        // DataInjectors.noiseRouterInjector("worldgen/noise_settings/amplified", "noiseRouterInjectorAmplified");
        // DataInjectors.noiseRouterInjector("worldgen/noise_settings/large_biomes", "noiseRouterInjectorLargeBiomes");

        DataInjectors.worldPresentInjector("worldgen/world_preset/normal", "worldPresentInjectorNormal");
        // DataInjectors.worldPresentInjector("worldgen/world_preset/amplified", "biomeSourceInjectorAmplified");
        // DataInjectors.worldPresentInjector("worldgen/world_preset/large_biome", "biomeSourceInjectorLargeBiome");

        DataInjectors.dimensionInjector("dimension/overworld", "dimensionInjectorNormal");
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            FLTBlocks.registerPots();
            FLTBlocks.registerFlammability();
        });
        //LOGGER.info("Far Lands Travelers, gather!");
    }

}
