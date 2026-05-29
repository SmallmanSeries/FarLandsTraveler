package com.smallmanseries.farlandstraveler.mixin.fakechunk;

import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ExplosionDamageCalculator.class)
public abstract class ExplosionDamageCalculatorMixin {
    @Inject(method = "getBlockExplosionResistance", at = @At("RETURN"), cancellable = true)
    private void modifyExplosionResistance(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, FluidState fluid, CallbackInfoReturnable<Optional<Float>> cir) {
        if (reader instanceof Level level
                && FakeChunk.isInFakeChunk(level, pos)
                && !(state.is(FLTTags.Blocks.DESOLID_EFFECT_NO_EFFECT))) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
