package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public class EntityMixin {
    //允许实体最大到33554432，防止实体出去后引发ticking entity崩溃
    @ModifyArgs(method = "absSnapTo(DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private void modifyArgs(Args args){
        args.set(1, (double) -33554432f);
        args.set(2, (double) 33554432f);
    }
}
