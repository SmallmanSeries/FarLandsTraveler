package com.smallmanseries.farlandstraveler.client;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.render.FakeChunkBorderRenderer;
import com.smallmanseries.farlandstraveler.client.render.entity.PrimitiveEndermanRender;
import com.smallmanseries.farlandstraveler.common.entity.FLTEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDebugRenderersEvent;

@EventBusSubscriber(modid = FarLandsTraveler.MODID, value = Dist.CLIENT)
public class EventHandlerClient {
    // 注册实体渲染器
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FLTEntityTypes.PRIMITIVE_ENDERMAN.get(), PrimitiveEndermanRender::new);
    }

    // 注册实体模型层


    // 注册调试渲染器
    @SubscribeEvent
    public static void registerDebugRenderer(RegisterDebugRenderersEvent event) {
        event.register(FakeChunkBorderRenderer::new);
    }
}
