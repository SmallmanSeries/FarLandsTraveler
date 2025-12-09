package com.smallmanseries.farlandstraveler.mixin.border;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Entity.class)
public abstract class EntityMixin {
    // 允许实体最大到33554432，防止实体出去后引发ticking entity崩溃（还未测试，测试后删掉括号及内容）
    @ModifyArgs(method = "absSnapTo(DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private void modifyArgsSnap(Args args){
        args.set(1, (double) -33554432f);
        args.set(2, (double) 33554432f);
    }

    // 防止外面的实体生成时回到30000512的位置。相对的，我（奥斯罗拉斯·岐）认为它们应该回到33554431.5的位置。
    @ModifyArgs(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private void modifyArgsLoad(Args args){
        args.set(1,-3.35544315E7);
        args.set(2,3.35544315E7);
    }
}
