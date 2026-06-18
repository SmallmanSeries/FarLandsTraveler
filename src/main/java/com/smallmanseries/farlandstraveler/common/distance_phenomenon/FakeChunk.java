package com.smallmanseries.farlandstraveler.common.distance_phenomenon;

import com.smallmanseries.farlandstraveler.common.misc.FLTAttachments;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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

    public static boolean isInFakeChunk(LevelReader level, BlockPos pos) {
        return level.getChunk(pos).getData(FLTAttachments.FAKE_CHUNK);
    }

    /**
     * 判断该实体是否免疫假区块
     *
     * @param entity 输入一个实体
     * @return 该实体是否免疫假区块
     */
    public static boolean isEntityNotImmune(Entity entity) {
        // 空实体，直接取消
        if (entity == null) {
            return true;
        }
        // 忽略免疫“去固体效应”的实体
        if (entity.getType().is(FLTTags.EntityTypes.DESOLID_EFFECT_IMMUNE)) {
            return false;
        }
        // 对生物实体的判断
        if (entity instanceof LivingEntity living) {
            return !(living.getItemBySlot(EquipmentSlot.BODY).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE) // 忽略穿着减免“去固体效应”的装备的实体
                    || living.getItemBySlot(EquipmentSlot.HEAD).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE) // Todo 这一段以后做探境装备的时候再细化
                    || living.getItemBySlot(EquipmentSlot.CHEST).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE)
                    || living.getItemBySlot(EquipmentSlot.LEGS).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE)
                    || living.getItemBySlot(EquipmentSlot.FEET).is(FLTTags.Items.DESOLID_EFFECT_IMMUNE)
                    || living.hasEffect(MobEffects.LUCK)); // 忽略带有特定药水效果的实体，此处先设为幸运效果
        }
        return true;
    }

    /**
     * 判断是否需要取消碰撞，用于{@link com.smallmanseries.farlandstraveler.mixin.fakechunk.BlockStateBaseMixin}
     *
     * @param state   方块状态，判断该方块是否不受假区块影响
     * @param getter  方块获取器，一般是level
     * @param pos     方块坐标
     * @param context 碰撞上下文
     * @return 布尔值，是否取消碰撞
     */
    public static boolean shouldDisableCollision(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (getter instanceof Level level && isInFakeChunk(level, pos)) {
            // 对方块的判断 - 忽略“去固体效应”无效化的方块
            if (state.is(FLTTags.Blocks.DESOLID_EFFECT_NO_EFFECT)) {
                return false;
            }
            // 对实体的判断
            if (context instanceof EntityCollisionContext entityContext) {
                Entity entity = entityContext.getEntity();
                return isEntityNotImmune(entity);
            }
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要取消互动，用于{@link com.smallmanseries.farlandstraveler.mixin.fakechunk.BlockStateBaseMixin}
     *
     * @param state   方块状态，判断该方块是否不受假区块影响
     * @param getter  方块获取器，一般是level
     * @param pos     方块坐标
     * @param context 碰撞上下文
     * @return 布尔值，是否取消互动
     */
    public static boolean shouldDisableInteraction(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (getter instanceof Level level && isInFakeChunk(level, pos)) {
            // 对方块的判断 - 忽略“去固体效应”无效化的方块
            if (state.is(FLTTags.Blocks.DESOLID_EFFECT_NO_EFFECT)) {
                return false;
            }
            // 对实体的判断
            if (context instanceof EntityCollisionContext entityContext) {
                Entity entity = entityContext.getEntity();
                // 空实体，直接取消
                if (entity == null) {
                    return true;
                }
                // 忽略免疫“去固体效应”的实体
                if (entity.getType().is(FLTTags.EntityTypes.DESOLID_EFFECT_IMMUNE)) {
                    return false;
                }
                // 对生物实体的判断
                if (entity instanceof LivingEntity living) {
                    return !(living.getItemBySlot(EquipmentSlot.MAINHAND).is(FLTTags.Items.DESOLID_EFFECT_NO_EFFECT)); // 忽略手持带有特定标签的物品的实体
                }
            }
            return true;
        }
        return false;
    }
}
