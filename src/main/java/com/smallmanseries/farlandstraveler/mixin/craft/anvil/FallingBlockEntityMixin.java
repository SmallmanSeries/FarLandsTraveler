package com.smallmanseries.farlandstraveler.mixin.craft.anvil;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.particle.PEShockwaveParticleOptions;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {

    @WrapOperation(method = "lambda$causeFallDamage$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"))
    private static void anvilCraft(Entity entity, DamageSource source, float damage, Operation<Void> original) {
        if (entity instanceof ItemEntity itemEntity) {
            ItemStack item = itemEntity.getItem();
            if (item.is(FLTItems.PRIMITIVE_ENDER_CORE)) {
                if (FarLands.isInFarLands(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 3)) {
                    itemEntity.playSound(FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_BREAK_FAR.get(), 1.0F, 1.0F);

                } else {
                    itemEntity.playSound(FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_BREAK.get(), 1.0F, 1.0F);
                }

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

    @ModifyExpressionValue(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Ljava/util/function/Predicate;and(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;"))
    private <T> Predicate<T> modifyEntitySelector(Predicate<T> original) {
        return original.or(entity -> entity instanceof ItemEntity item && item.getItem().is(FLTItems.PRIMITIVE_ENDER_CORE));
    }
}
