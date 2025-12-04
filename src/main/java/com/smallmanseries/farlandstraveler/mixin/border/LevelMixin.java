package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {
    //使地形无限生成
    @Inject(at = @At("RETURN"), method = "isInWorldBoundsHorizontal",cancellable = true)
    private static void isInWorldBoundsHorizontal(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }


}
