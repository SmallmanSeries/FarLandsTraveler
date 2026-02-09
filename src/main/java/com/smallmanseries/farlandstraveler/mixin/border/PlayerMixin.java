package com.smallmanseries.farlandstraveler.mixin.border;

import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
    // 允许玩家出界
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private double modifyClamp(double value, double min, double max){
        if(Config.REMOVE_COORDINATE_LIMITS.getAsBoolean()) {
            return value;
        }
        return value < min ? min : Math.min(value, max);
    }
}
