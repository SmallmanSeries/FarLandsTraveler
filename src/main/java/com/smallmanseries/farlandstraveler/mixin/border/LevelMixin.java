package com.smallmanseries.farlandstraveler.mixin.border;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Level.class)
public abstract class LevelMixin {
    // 使地形无限生成
    // 副作用：现在tp指令没有最大坐标限制了
    @ModifyReturnValue(method = "isInWorldBoundsHorizontal", at = @At("RETURN"))
    private static boolean removeWorldBoundsHorizontal(boolean original) {
        if (Config.REMOVE_COORDINATE_LIMITS.getAsBoolean()) {
            return true;
        }
        return original;
    }

    // 我去除边境之地的，把高度限制删了有什么用啊
    @ModifyReturnValue(method = "isOutsideSpawnableHeight", at = @At("RETURN"))
    private static boolean removeWorldBoundsVertical(boolean original) {
        if (Config.REMOVE_COORDINATE_LIMITS.getAsBoolean()) {
            return false;
        }
        return original;
    }
}
