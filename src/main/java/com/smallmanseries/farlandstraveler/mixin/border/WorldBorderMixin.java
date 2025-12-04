package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {
    //使世界边界碰撞检测失效
    @Inject(at = @At("HEAD"), method = "isWithinBounds(Lnet/minecraft/core/BlockPos;)Z",cancellable = true)
    public void isWithinBounds(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "isWithinBounds(Lnet/minecraft/world/level/ChunkPos;)Z",cancellable = true)
    public void isWithinBounds(ChunkPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "isWithinBounds(Lnet/minecraft/world/phys/Vec3;)Z",cancellable = true)
    public void isWithinBounds(Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "isWithinBounds(Lnet/minecraft/world/phys/AABB;)Z",cancellable = true)
    public void isWithinBounds(AABB box, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "isWithinBounds(DD)Z",cancellable = true)
    public void isWithinBounds(double x, double z, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "isWithinBounds(DDD)Z",cancellable = true)
    public void isWithinBounds(double x, double z, double offset, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Inject(at = @At("HEAD"), method = "isWithinBounds(DDDD)Z",cancellable = true)
    public void isWithinBounds(double x1, double z1, double x2, double z2, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    //使世界边界警告、扣血机制失效
    @Inject(at = @At("RETURN"), method = "getDistanceToBorder(Lnet/minecraft/world/entity/Entity;)D",cancellable = true)
    public void getDistanceToBorder(Entity entity, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(Double.MAX_VALUE);
    }
    @Inject(at = @At("RETURN"), method = "getDistanceToBorder(DD)D",cancellable = true)
    public void getDistanceToBorder(double x, double z, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(Double.MAX_VALUE);
    }
}
