package com.smallmanseries.farlandstraveler.client;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.render.FakeChunkBorderRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDebugRenderersEvent;

@EventBusSubscriber(modid = FarLandsTraveler.MODID, value = Dist.CLIENT)
public class EventHandlerClient {
    // 注册调试渲染器
    @SubscribeEvent
    public static void registerDebugRenderer(RegisterDebugRenderersEvent event) {
        event.register(FakeChunkBorderRenderer::new);
    }
}
