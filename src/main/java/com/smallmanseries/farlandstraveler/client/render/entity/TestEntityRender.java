package com.smallmanseries.farlandstraveler.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.model.entity.TestEntityModel;
import com.smallmanseries.farlandstraveler.client.render.entity.layers.GlowLayer;
import com.smallmanseries.farlandstraveler.client.render.entity.layers.TVNoiseLayer;
import com.smallmanseries.farlandstraveler.common.entity.TestEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class TestEntityRender extends HumanoidMobRenderer<TestEntity, ZombieRenderState, TestEntityModel> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "textures/entity/zombie_far.png");
    private final TVNoiseLayer<ZombieRenderState, TestEntityModel> tvNoiseLayer;

    public TestEntityRender(EntityRendererProvider.Context context) {
        super(context, new TestEntityModel(context.bakeLayer(FLTLayers.TEST_ENTITY)), 0.5F);
        this.addLayer(new GlowLayer<>(this, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "textures/entity/zombie_far_glow.png")));
        this.tvNoiseLayer = new TVNoiseLayer<>(this, TEXTURE_LOCATION);
        this.addLayer(this.tvNoiseLayer);
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState zombieRenderState) {
        return TEXTURE_LOCATION;
    }

    @Override
    public ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    public void submit(ZombieRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        // 取消受伤的红色遮罩
        this.tvNoiseLayer.hasTVNoise = state.hasRedOverlay;
        state.hasRedOverlay = false;

        super.submit(state, poseStack, submitNodeCollector, camera);
    }
}
