package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    // 防止玩家被踢出服务器，只因为走得太远！！！
    @Inject(at = @At("RETURN"), method = "clampHorizontal",cancellable = true)
    private static void clampHorizontal(double d, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(d);
    }
}
