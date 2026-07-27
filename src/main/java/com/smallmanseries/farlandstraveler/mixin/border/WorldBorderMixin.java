package com.smallmanseries.farlandstraveler.mixin.border;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {
    // 使世界边界碰撞检测失效
    @ModifyReturnValue(method = "isWithinBounds(DDD)Z", at = @At("RETURN"))
    private boolean disableWorldBorder(boolean original) {
        if (Config.REMOVE_WORLD_BORDER.getAsBoolean()) {
            return true;
        }
        return original;
    }

    // 使世界边界警告、扣血机制失效
    @ModifyReturnValue(method = "getDistanceToBorder(DD)D", at = @At("RETURN"))
    private double disableBorderEffects(double original) {
        if (Config.REMOVE_WORLD_BORDER.getAsBoolean()) {
            return Double.MAX_VALUE;
        }
        return original;
    }
}
