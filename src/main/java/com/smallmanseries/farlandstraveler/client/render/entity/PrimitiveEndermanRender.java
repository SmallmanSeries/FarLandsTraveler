package com.smallmanseries.farlandstraveler.client.render.entity;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.render.entity.layers.GlowLayer;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

import java.util.List;

public class PrimitiveEndermanRender extends EndermanRenderer {
    public PrimitiveEndermanRender(EntityRendererProvider.Context context) {
        super(context);

        for (RenderLayer<?, ?> layer : List.copyOf(this.layers)) {
            if (layer instanceof EnderEyesLayer) {
                this.layers.remove(layer);
            }
        }
        this.addLayer(new GlowLayer<>(this, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "textures/entity/primitive_enderman/enderman_eyes.png")));
    }
}
