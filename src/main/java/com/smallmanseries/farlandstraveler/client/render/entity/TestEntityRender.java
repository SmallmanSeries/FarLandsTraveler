package com.smallmanseries.farlandstraveler.client.render.entity;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.model.entity.TestEntityModel;
import com.smallmanseries.farlandstraveler.common.entity.TestEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

public class TestEntityRender extends HumanoidMobRenderer<TestEntity, ZombieRenderState, TestEntityModel> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "textures/entity/zombie_far.png");

    public TestEntityRender(EntityRendererProvider.Context context) {
        super(context, new TestEntityModel(context.bakeLayer(FLTLayers.TEST_ENTITY)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState zombieRenderState) {
        return TEXTURE_LOCATION;
    }

    @Override
    public ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }
}
