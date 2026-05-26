package com.smallmanseries.farlandstraveler.common.distance_phenomenon;

import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;

public class FakeChunk {

    public static boolean isInFakeChunk(Level level, BlockPos pos) {
        return level.getChunkAt(pos).getData(FLTAttachments.FAKE_CHUNK);
    }

    public static boolean shouldDisableCollision(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if (entity != null) {
                return isInFakeChunk(entity.level(), pos);
            }
        }
        return false;
    }
}
