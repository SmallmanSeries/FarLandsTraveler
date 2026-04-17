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
    public void read(FriendlyByteBuf buf, CallbackInfo ci) {
        this.offset = new BlockPos(Mth.clamp(buf.readVarInt(), Integer.MIN_VALUE, Integer.MAX_VALUE), Mth.clamp(buf.readVarInt(), Integer.MIN_VALUE, Integer.MAX_VALUE), Mth.clamp(buf.readVarInt(), Integer.MIN_VALUE, Integer.MAX_VALUE));
        this.size = new BlockPos(Mth.clamp(buf.readVarInt(), 0, Integer.MAX_VALUE), Mth.clamp(buf.readVarInt(), 0, Integer.MAX_VALUE), Mth.clamp(buf.readVarInt(), 0, Integer.MAX_VALUE));
    }

    @Inject(method = "write(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
    public void write(FriendlyByteBuf buf, CallbackInfo ci) {
        buf.writeVarInt(this.offset.getX());
        buf.writeVarInt(this.offset.getY());
        buf.writeVarInt(this.offset.getZ());
        buf.writeVarInt(this.size.getX());
        buf.writeVarInt(this.size.getY());
        buf.writeVarInt(this.size.getZ());
    }
}
