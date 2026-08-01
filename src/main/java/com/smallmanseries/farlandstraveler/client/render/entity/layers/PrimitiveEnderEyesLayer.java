package com.smallmanseries.farlandstraveler.client.render.entity.layers;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.client.model.monster.enderman.EndermanModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.client.renderer.entity.state.EndermanRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class PrimitiveEnderEyesLayer extends EnderEyesLayer {
    private static final RenderType PRIMITIVE_ENDERMAN_EYES = RenderTypes.eyes(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "textures/entity/primitive_enderman/enderman_eyes.png"));

    public PrimitiveEnderEyesLayer(RenderLayerParent<EndermanRenderState, EndermanModel<EndermanRenderState>> renderer) {
        super(renderer);
    }

    public RenderType renderType() {
        return PRIMITIVE_ENDERMAN_EYES;
    }
}
