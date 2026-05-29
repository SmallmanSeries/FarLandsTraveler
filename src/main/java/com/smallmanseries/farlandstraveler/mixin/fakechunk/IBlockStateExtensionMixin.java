package com.smallmanseries.farlandstraveler.mixin.fakechunk;

import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockStateExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IBlockStateExtension.class)
public interface IBlockStateExtensionMixin {

    @Inject(method = "isLadder", at = @At("RETURN"), cancellable = true)
    private void modifyLadder(LevelReader level, BlockPos pos, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (FakeChunk.isInFakeChunk(level, pos) && !((BlockState) this).is(FLTTags.Blocks.DESOLID_EFFECT_NO_EFFECT)) {
            cir.setReturnValue(false);
        }
    }
}
