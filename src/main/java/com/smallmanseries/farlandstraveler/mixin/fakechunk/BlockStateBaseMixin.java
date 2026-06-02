package com.smallmanseries.farlandstraveler.mixin.fakechunk;

import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiConsumer;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Shadow
    public abstract boolean is(TagKey<Block> tag);

    // 取消实体（玩家、末影人等）互动
    @Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    private void modifyShape(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir){
        if (FakeChunk.shouldDisableInteraction(level.getBlockState(pos), level, pos, context) && !context.equals(CollisionContext.empty())) {
            cir.setReturnValue(Shapes.empty());
        }
    }

    // 防止读取缓存的碰撞体积
    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    private void modifyCollision(BlockGetter level, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {
        if (FakeChunk.shouldDisableCollision(level.getBlockState(pos), level, pos, CollisionContext.empty())) {
            cir.setReturnValue(Shapes.empty());
        }
    }

    // 取消方块的碰撞体积
    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    private void modifyCollision(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (FakeChunk.shouldDisableCollision(level.getBlockState(pos), level, pos, context)) {
            cir.setReturnValue(Shapes.empty());
        }
    }

    // 使方块不可被炸坏
    @Inject(method = "onExplosionHit", at = @At("HEAD"), cancellable = true)
    private void modifyExplosionHit(ServerLevel level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer, CallbackInfo ci) {
        if (FakeChunk.isInFakeChunk(level, pos) && !this.is(FLTTags.Blocks.DESOLID_EFFECT_NO_EFFECT)) {
            ci.cancel();
        }
    }

    // 使方块不对其中的实体产生特殊效果
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void modifyEntityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, CallbackInfo ci) {
        if (FakeChunk.isInFakeChunk(level, pos) && !this.is(FLTTags.Blocks.DESOLID_EFFECT_NO_EFFECT) && FakeChunk.isEntityNotImmune(entity)) {
            ci.cancel();
        }
    }
}
