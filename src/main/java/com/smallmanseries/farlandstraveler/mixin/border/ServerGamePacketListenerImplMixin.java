package com.smallmanseries.farlandstraveler.mixin.border;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
    // 防止玩家被踢出服务器，只因为走得太远！！！
    @ModifyReturnValue(method = "clampHorizontal", at = @At("RETURN"))
    private static double disableClampHorizontal(double original, @Local(argsOnly = true, name = "value") double value) {
        return value;
    }
}
