package com.smallmanseries.farlandstraveler.client.render.entity.layers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class GlowLayer<S extends EntityRenderState, M extends EntityModel<S>> extends EyesLayer<S, M> {
    private final RenderType GLOW_TEXTURE;

    public GlowLayer(RenderLayerParent<S, M> renderer, Identifier id) {
        super(renderer);
        GLOW_TEXTURE = RenderTypes.eyes(id);
    }

    public RenderType renderType() {
        return GLOW_TEXTURE;
    }
}
