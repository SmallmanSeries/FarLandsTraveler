package com.smallmanseries.farlandstraveler.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

@EventBusSubscriber(modid = FarLandsTraveler.MODID, value = Dist.CLIENT)
public class EventHandlerClient {
    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent.AfterTripwireBlocks event) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.level == null || mc.player == null) return;
        if(!mc.player.getMainHandItem().is(FLTItems.FAKE_CHUNK_MARKER)) return;
        PoseStack stack = event.getPoseStack();
        Vec3 camera = event.getCamera().getPosition();
        VertexConsumer consumer = mc.renderBuffers().bufferSource().getBuffer(RenderType.debugLineStrip(1.0));
        int renderDistance = mc.options.getEffectiveRenderDistance();
        ChunkPos pos = new ChunkPos(mc.player.blockPosition());

        Matrix4f matrix4f = stack.last().pose();

        for (int dx = -renderDistance; dx <= renderDistance; dx++){
            for (int dz = -renderDistance; dz <= renderDistance; dz++){
                int chunkX = pos.x + dx;
                int chunkZ = pos.z + dz;
                ChunkAccess chunk = mc.level.getChunk(chunkX, chunkZ);
                if (chunk.getData(FLTAttachments.FAKE_CHUNK)){
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
}
