package com.smallmanseries.farlandstraveler.mixin.border;

import com.smallmanseries.farlandstraveler.Config;
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
        if(Config.REMOVE_COORDINATE_LIMITS.getAsBoolean()) {
            cir.setReturnValue(true);
        }
    }

    // 我去边境之地的，把高度限制删了有什么用啊
    @Inject(method = "isOutsideSpawnableHeight", at = @At("RETURN"), cancellable = true)
    private static void isOutsideSpawnableHeight(int y, CallbackInfoReturnable<Boolean> cir) {
        if(Config.REMOVE_COORDINATE_LIMITS.getAsBoolean()) {
            cir.setReturnValue(false);
        }
    }
}
