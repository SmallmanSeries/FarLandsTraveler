package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Player.class)
public class PlayerMixin {
    //允许玩家最大到33554431，防止出去后引发ticking entity崩溃
    //为什么要比33554432小一格？为了保险~
    @ModifyArgs(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private void modifyArgs(Args args){
        args.set(1, (double) -33554431f);
        args.set(2, (double) 33554431f);
    }
}
