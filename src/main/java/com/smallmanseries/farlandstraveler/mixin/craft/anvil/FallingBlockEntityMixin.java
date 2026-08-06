package com.smallmanseries.farlandstraveler.mixin.craft.anvil;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.item.PrimitiveEnderCoreItem;
import com.smallmanseries.farlandstraveler.common.particle.PEShockwaveParticleOptions;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {

    // 铁砧砸击合成
    @WrapOperation(method = "lambda$causeFallDamage$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"))
    private static void anvilCraft(Entity entity, DamageSource source, float damage, Operation<Void> original) {
        if (source.is(DamageTypes.FALLING_ANVIL) && entity instanceof ItemEntity itemEntity) {
            ItemStack item = itemEntity.getItem();

            // 原始末影核心的合成：铁砧砸击原始末影核心，消耗一个该物品，产生物品碎裂的粒子效果和冲击波效果，产生一个无伤害且不破坏方块的爆炸。
            if (item.is(FLTItems.PRIMITIVE_ENDER_CORE)) {
                itemEntity.level().explode(
                        itemEntity,
                        null,
                        PrimitiveEnderCoreItem.EXPLOSION_DAMAGE_CALCULATOR,
                        itemEntity.getBlockX() + 0.5,
                        itemEntity.getY() + 0.5,
                        itemEntity.getBlockZ() + 0.5,
                        1.5F,
                        false,
                        Level.ExplosionInteraction.NONE,
                        ParticleTypes.SMOKE,
                        ParticleTypes.LARGE_SMOKE,
                        WeightedList.of(),
                        FarLands.isInFarLands(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 3) ?
                                FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_BREAK_FAR : FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_BREAK
                );

                if (itemEntity.level() instanceof ServerLevel server) {
                    server.sendParticles(
                            new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item)),
                            itemEntity.getBlockX() + 0.5,
                            itemEntity.getY() + 0.1,
                            itemEntity.getBlockZ() + 0.5,
                            20,
                            0.2,
                            0.05,
                            0.2,
                            0.1
                    );
                    server.sendParticles(
                            new PEShockwaveParticleOptions(Direction.UP, 10, 10),
                            true,
                            true,
                            itemEntity.getBlockX() + 0.5,
                            itemEntity.getY() + 0.1,
                            itemEntity.getBlockZ() + 0.5,
                            1,
                            0,
                            0,
                            0,
                            0
                    );
                    server.sendParticles(
                            new PEShockwaveParticleOptions(Direction.DOWN, 10, 10),
                            true,
                            true,
                            itemEntity.getBlockX() + 0.5,
                            itemEntity.getY() + 0.1,
                            itemEntity.getBlockZ() + 0.5,
                            1,
                            0,
                            0,
                            0,
                            0
                    );
                }

                item.setCount(item.getCount() - 1);
            }
        }
        original.call(entity, source, damage);
    }

    // 修改爆炸的实体选择器，使其能选中部分物品实体
    @ModifyExpressionValue(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Ljava/util/function/Predicate;and(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;"))
    private <T> Predicate<T> modifyEntitySelector(Predicate<T> original) {
        return original.or(entity -> entity instanceof ItemEntity item && item.getItem().is(FLTItems.PRIMITIVE_ENDER_CORE));
    }
}
