package com.smallmanseries.farlandstraveler.mixin.fakechunk;

import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract Level level();

    @Shadow
    public abstract BlockPos blockPosition();

    // 取消实体对于假区块中的流体的碰撞判定
    @Inject(method = "updateFluidHeightAndDoCanPushEntityFluidPushing", at = @At("HEAD"), cancellable = true)
    private void modifyFluidEffects(boolean performFluidPushing, CallbackInfoReturnable<Boolean> cir) {
        if (FakeChunk.isInFakeChunk(this.level(), this.blockPosition())) {
            if (FakeChunk.isEntityNotImmune((Entity) (Object) this)) {
                cir.setReturnValue(false);
            }
        }
    }

    // 取消实体对于假区块中的流体的浸没效果
    @Inject(method = "updateFluidOnEyes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeY()D"), cancellable = true)
    private void modifyFluidEffects(CallbackInfo ci) {
        if (FakeChunk.isInFakeChunk(this.level(), this.blockPosition())) {
            if (FakeChunk.isEntityNotImmune((Entity) (Object) this)) {
                ci.cancel();
            }
        }
    }
}
