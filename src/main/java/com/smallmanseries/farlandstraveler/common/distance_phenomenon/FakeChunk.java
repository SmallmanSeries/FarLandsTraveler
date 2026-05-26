package com.smallmanseries.farlandstraveler.common.distance_phenomenon;

import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;

/**
 * “假区块”类，用于实现“去固体效应”距离现象（表现为假区块）
 */
public class FakeChunk {

    /**
     * 判断当前坐标是否位于假区块中
     *
     * @param level 当前存档，用于读取区块信息
     * @param pos   方块坐标
     * @return 布尔值，是否位于假区块
     */
    public static boolean isInFakeChunk(Level level, BlockPos pos) {
        return level.getChunkAt(pos).getData(FLTAttachments.FAKE_CHUNK);
    }

    /**
     * 判断是否需要取消实体碰撞，用于{@link com.smallmanseries.farlandstraveler.mixin.fakechunk.BlockStateBaseMixin}
     *
     * @param state   方块状态，判断该方块是否不受假区块影响
     * @param getter  方块获取器，一般是level，目前没有作用
     * @param pos     方块坐标
     * @param context 碰撞上下文
     * @return 布尔值，是否取消碰撞
     */
    public static boolean shouldDisableCollision(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if (entity != null && isInFakeChunk(entity.level(), pos) && !state.is(FLTTags.Blocks.DESOLID_EFFECT_NO_EFFECT)) {
                if (entity instanceof LivingEntity living && !entity.getType().is(FLTTags.EntityTypes.DESOLID_EFFECT_IMMUNE)) { // 忽略免疫“去固体效应”的实体
                    return !(  living.getItemBySlot(EquipmentSlot.BODY).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE) // 忽略穿着减免“去固体效应”的装备的实体
                            || living.getItemBySlot(EquipmentSlot.HEAD).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE) // Todo 这一段以后做探境装备的时候再细化
                            || living.getItemBySlot(EquipmentSlot.CHEST).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE)
                            || living.getItemBySlot(EquipmentSlot.LEGS).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE)
                            || living.getItemBySlot(EquipmentSlot.FEET).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE));
                }
            }
        }
        return false;
    }
}
