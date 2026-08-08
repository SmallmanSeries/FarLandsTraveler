package com.smallmanseries.farlandstraveler.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class SpawnTestEntityItem extends SpawnEggItem {
    public SpawnTestEntityItem(Properties properties) {
        super(properties);
    }

    private static InteractionResult spawnMob(@Nullable LivingEntity user, ItemStack itemStack, Level level, BlockPos spawnPos, boolean tryMoveDown, boolean movedUp) {
        EntityType<?> type = getType(itemStack);
        if (type == null) {
            return InteractionResult.FAIL;
        }
        if (type.spawn((ServerLevel) level, itemStack, user, spawnPos, EntitySpawnReason.SPAWN_ITEM_USE, tryMoveDown, movedUp) != null) {
            level.gameEvent(user, GameEvent.ENTITY_PLACE, spawnPos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }

        ItemStack itemStack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockPos spawnPos;
        if (level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()) {
            spawnPos = pos;
        } else {
            spawnPos = pos.relative(clickedFace);
        }

        return spawnMob(context.getPlayer(), itemStack, level, spawnPos, true, !Objects.equals(pos, spawnPos) && clickedFace == Direction.UP);
    }


}




