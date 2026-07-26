package com.smallmanseries.farlandstraveler.common.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class FLTConfigurationScreen extends ConfigurationScreen.ConfigurationSectionScreen {
    public FLTConfigurationScreen(Screen parent, ModConfig.Type type, ModConfig modConfig, Component title) {
        super(parent, type, modConfig, title);
    }

}

