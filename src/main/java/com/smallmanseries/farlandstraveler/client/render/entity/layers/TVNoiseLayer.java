package com.smallmanseries.farlandstraveler.client.render.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.smallmanseries.farlandstraveler.client.render.FLTRenderTypes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class TVNoiseLayer<S extends EntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    public boolean hasTVNoise;
    private final Identifier TEXTURE_LOCATION;

    public TVNoiseLayer(RenderLayerParent<S, M> renderer, Identifier id) {
        super(renderer);
        this.TEXTURE_LOCATION = id;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float v, float v1) {
        if (this.hasTVNoise) {
            submitNodeCollector.order(0).submitModel(
                    this.getParentModel(),
                    state,
                    poseStack,
                    FLTRenderTypes.tvNoise(this.TEXTURE_LOCATION),
                    lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor,
                    null);
        }
    }
}
