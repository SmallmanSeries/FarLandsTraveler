package com.smallmanseries.farlandstraveler.common.misc;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FLTAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FarLandsTraveler.MODID);

    public static final Supplier<AttachmentType<Boolean>> FAKE_CHUNK = ATTACHMENT_TYPES.register(
            "fake_chunk", () -> AttachmentType.builder(() -> false)
                    .sync(ByteBufCodecs.BOOL)
                    .serialize(Codec.BOOL.fieldOf("fake"))
                    .build());

}
