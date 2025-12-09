package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {
    // 使地形无限生成
    // 副作用：现在tp指令没有最大坐标限制了
    @Inject(method = "isInWorldBoundsHorizontal", at = @At("RETURN"), cancellable = true)
    private static void isInWorldBoundsHorizontal(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
