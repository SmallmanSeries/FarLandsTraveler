package com.smallmanseries.farlandstraveler.mixin.fakechunk;

import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowingFluid.class)
public abstract class FlowingFluidMixin {

    @Inject(method = "getShape", at = @At("RETURN"), cancellable = true)
    private void modifyShape(FluidState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {
        if (level instanceof Level && FakeChunk.isInFakeChunk((Level) level, pos)) {
            cir.setReturnValue(Shapes.empty());
        }
    }

    @Inject(method = "canMaybePassThrough", at = @At("RETURN"), cancellable = true)
    private void cancelSpread(BlockGetter level, BlockPos pos, BlockState state, Direction direction, BlockPos spreadPos, BlockState spreadState, FluidState fluidState, CallbackInfoReturnable<Boolean> cir){
        if (level instanceof Level && (
                (FakeChunk.isInFakeChunk((Level) level, pos) && !FakeChunk.isInFakeChunk((Level) level, spreadPos))
                || (!FakeChunk.isInFakeChunk((Level) level, pos) && FakeChunk.isInFakeChunk((Level) level, spreadPos))
        )) {
            cir.setReturnValue(false);
        }
    }
}
