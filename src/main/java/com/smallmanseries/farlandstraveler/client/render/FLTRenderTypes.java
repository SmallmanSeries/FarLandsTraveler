package com.smallmanseries.farlandstraveler.client.render;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class FLTRenderTypes {
    private static final Function<Identifier, RenderType> ENTITY_TV_NOISE = Util.memoize((texture) -> {
        RenderSetup state = RenderSetup.builder(FLTRenderPipelines.ENTITY_TV_NOISE).withTexture("Sampler0", texture).useLightmap().useOverlay().affectsCrumbling().sortOnUpload().setOutline(RenderSetup.OutlineProperty.NONE).createRenderSetup();
        return RenderType.create("entity_tv_noise", state);
    });

    public static RenderType tvNoise(Identifier texture) {
        return ENTITY_TV_NOISE.apply(texture);
    }
}
