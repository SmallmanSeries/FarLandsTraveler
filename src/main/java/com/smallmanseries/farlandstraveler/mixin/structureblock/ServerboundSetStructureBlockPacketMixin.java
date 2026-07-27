package com.smallmanseries.farlandstraveler.mixin.structureblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerboundSetStructureBlockPacket.class)
public abstract class ServerboundSetStructureBlockPacketMixin {

    @Shadow
    @Final
    @Mutable
    private BlockPos offset;

    @Shadow
    @Final
    @Mutable
    private Vec3i size;

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = {@At("RETURN")})
    public void read(FriendlyByteBuf input, CallbackInfo ci) {
        this.offset = new BlockPos(Mth.clamp(input.readVarInt(), Integer.MIN_VALUE, Integer.MAX_VALUE), Mth.clamp(input.readVarInt(), Integer.MIN_VALUE, Integer.MAX_VALUE), Mth.clamp(input.readVarInt(), Integer.MIN_VALUE, Integer.MAX_VALUE));
        this.size = new BlockPos(Mth.clamp(input.readVarInt(), 0, Integer.MAX_VALUE), Mth.clamp(input.readVarInt(), 0, Integer.MAX_VALUE), Mth.clamp(input.readVarInt(), 0, Integer.MAX_VALUE));
    }

    @Inject(method = "write(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
    public void write(FriendlyByteBuf output, CallbackInfo ci) {
        output.writeVarInt(this.offset.getX());
        output.writeVarInt(this.offset.getY());
        output.writeVarInt(this.offset.getZ());
        output.writeVarInt(this.size.getX());
        output.writeVarInt(this.size.getY());
        output.writeVarInt(this.size.getZ());
    }
}
