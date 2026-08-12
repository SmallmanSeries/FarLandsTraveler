package com.smallmanseries.farlandstraveler.client.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.resources.Identifier;

import static net.minecraft.client.renderer.RenderPipelines.MATRICES_FOG_LIGHT_DIR_SNIPPET;

public class FLTRenderPipelines {
    public static final RenderPipeline ENTITY_TV_NOISE = RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET)
            .withVertexShader(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "core/rendertype_entity_tvnoise"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "core/rendertype_entity_tvnoise"))
            .withSampler("Sampler0")
            .withSampler("Sampler1")
            .withSampler("Sampler2")
            .withVertexFormat(DefaultVertexFormat.ENTITY, VertexFormat.Mode.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withLocation(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "pipeline/rendertype_entity_tvnoise"))
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withCull(false)
            .build();

}
