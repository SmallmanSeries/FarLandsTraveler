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
    public static void registerDebugRenderer(RegisterDebugRenderersEvent event){
        event.register(FakeChunkBorderRenderer::new);
    }

    /*
    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent.AfterWeather event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (!mc.player.getMainHandItem().is(FLTItems.FAKE_CHUNK_MARKER)) return;
        PoseStack stack = event.getPoseStack();
        Vec3 camera = event.getLevelRenderState().cameraRenderState.pos;
        VertexConsumer consumer = mc.renderBuffers().bufferSource().getBuffer(RenderTypes.lines());
        int renderDistance = mc.options.getEffectiveRenderDistance();
        ChunkPos pos = ChunkPos.containing(mc.player.blockPosition());

        Matrix4f matrix4f = stack.last().pose();

        for (int dx = -renderDistance; dx <= renderDistance; dx++) {
            for (int dz = -renderDistance; dz <= renderDistance; dz++) {
                int chunkX = pos.x() + dx;
                int chunkZ = pos.z() + dz;
                ChunkAccess chunk = mc.level.getChunk(chunkX, chunkZ);
                if (chunk.getData(FLTAttachments.FAKE_CHUNK)) {
                    double minX = chunk.getPos().getMinBlockX() - camera.x();
                    double minY = chunk.getMinY() - camera.y();
                    double minZ = chunk.getPos().getMinBlockZ() - camera.z();
                    double maxX = chunk.getPos().getMaxBlockX() - camera.x() + 1;
                    double maxY = chunk.getMaxY() - camera.y() + 1;
                    double maxZ = chunk.getPos().getMaxBlockZ() - camera.z() + 1;

                    consumer.addVertex(matrix4f, (float) minX, (float) minY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) minY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) minY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) minX, (float) minY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) minX, (float) minY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) minX, (float) maxY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) maxY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) maxY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) minX, (float) maxY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) minX, (float) maxY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) maxY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) minY, (float) minZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) minY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) maxX, (float) maxY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) minX, (float) maxY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                    consumer.addVertex(matrix4f, (float) minX, (float) minY, (float) maxZ).setColor(1.0F, 0.0F, 0.0F, 1.0F);
                }
            }
        }
        mc.renderBuffers().bufferSource().endBatch();
    }

     */
}
