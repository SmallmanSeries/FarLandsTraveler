package com.smallmanseries.farlandstraveler.client;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.model.entity.TestEntityModel;
import com.smallmanseries.farlandstraveler.client.particle.PEShockwaveParticle;
import com.smallmanseries.farlandstraveler.client.render.FakeChunkBorderRenderer;
import com.smallmanseries.farlandstraveler.client.render.entity.FLTLayers;
import com.smallmanseries.farlandstraveler.client.render.entity.PrimitiveEndermanRender;
import com.smallmanseries.farlandstraveler.client.render.entity.TestEntityRender;
import com.smallmanseries.farlandstraveler.common.entity.FLTEntityTypes;
import com.smallmanseries.farlandstraveler.common.particle.FLTParticleTypes;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDebugRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = FarLandsTraveler.MODID, value = Dist.CLIENT)
public class EventHandlerClient {
    // 注册实体渲染器
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FLTEntityTypes.PRIMITIVE_ENDERMAN.get(), PrimitiveEndermanRender::new);
        event.registerEntityRenderer(FLTEntityTypes.TEST_ENTITY.get(), TestEntityRender::new);
    }

    // 注册实体模型层
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FLTLayers.TEST_ENTITY, () -> TestEntityModel.createBodyLayer(CubeDeformation.NONE));
    }


    // 注册调试渲染器
    @SubscribeEvent
    public static void registerDebugRenderer(RegisterDebugRenderersEvent event) {
        event.register(FakeChunkBorderRenderer::new);
    }

    // 注册粒子提供器
    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FLTParticleTypes.PRIMITIVE_ENDER_SHOCKWAVE.get(), PEShockwaveParticle.Provider::new);
    }
}
