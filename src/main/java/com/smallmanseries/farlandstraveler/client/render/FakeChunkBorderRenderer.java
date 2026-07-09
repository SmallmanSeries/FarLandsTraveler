package com.smallmanseries.farlandstraveler.client.render;

import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public record FakeChunkBorderRenderer(Minecraft minecraft) implements DebugRenderer.SimpleDebugRenderer {
    @Override
    public void emitGizmos(double camX, double camY, double camZ, DebugValueAccess debugValueAccess, Frustum frustum, float partialTicks) {
        int renderDistance = this.minecraft.options.getEffectiveRenderDistance();
        if (this.minecraft.level == null || this.minecraft.player == null) return;
        if (!this.minecraft.player.getMainHandItem().is(FLTItems.FAKE_CHUNK_MARKER)) return;
        Vec3 camera = new Vec3(camX, camY, camZ);
        ChunkPos pos = ChunkPos.containing(BlockPos.containing(camera));

        for (int dx = -renderDistance; dx <= renderDistance; dx++) {
            for (int dz = -renderDistance; dz <= renderDistance; dz++) {
                int chunkX = pos.x() + dx;
                int chunkZ = pos.z() + dz;
                ChunkAccess chunk = this.minecraft.level.getChunk(chunkX, chunkZ);
                if (chunk.getData(FLTAttachments.FAKE_CHUNK)) {
                    Gizmos.cuboid(new AABB(
                            chunk.getPos().getMinBlockX(),
                            chunk.getMinY(),
                            chunk.getPos().getMinBlockZ(),
                            chunk.getPos().getMaxBlockX() + 1,
                            chunk.getMaxY() + 1,
                            chunk.getPos().getMaxBlockZ() + 1
                    ), GizmoStyle.stroke(ARGB.color(255, 255, 0, 0), 1.0F));
                }
            }
        }
    }
}
