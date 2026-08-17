package com.smallmanseries.farlandstraveler.common.network;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.swing.*;

public record PopUpPacket(
    String title,
    String content
) implements CustomPacketPayload {
    public static final Type<PopUpPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "pop_up"));

    public static final StreamCodec<ByteBuf, PopUpPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, PopUpPacket::title,
            ByteBufCodecs.STRING_UTF8, PopUpPacket::content,
            PopUpPacket::new
    );

    public static void handle(PopUpPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            System.setProperty("java.awt.headless", "false");
            JOptionPane.showConfirmDialog(
                    null,
                    String.format(Component.translatable(packet.content).getString(), System.getProperty("user.name")),
                    Component.translatable(packet.title).getString(),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            System.setProperty("java.awt.headless", "true");
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
