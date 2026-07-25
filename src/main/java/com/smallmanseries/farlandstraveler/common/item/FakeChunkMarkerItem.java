package com.smallmanseries.farlandstraveler.common.item;

import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class FakeChunkMarkerItem extends Item {
    public FakeChunkMarkerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = context.getClickedPos();
            LevelChunk chunk = level.getChunkAt(pos);
            Player player = context.getPlayer();
            if (chunk.getData(FLTAttachments.FAKE_CHUNK)) {
                chunk.setData(FLTAttachments.FAKE_CHUNK, false);
                if (player != null) {
                    player.sendOverlayMessage(Component.translatable("message.farlandstraveler.fake_chunk_unmarked"));
                }
            } else {
                chunk.setData(FLTAttachments.FAKE_CHUNK, true);
                if (player != null) {
                    player.sendOverlayMessage(Component.translatable("message.farlandstraveler.fake_chunk_marked"));
                }
            }
        }


        int[][] a = new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        System.out.println(a[3][4]); // 20

        return InteractionResult.SUCCESS;
    }
}
