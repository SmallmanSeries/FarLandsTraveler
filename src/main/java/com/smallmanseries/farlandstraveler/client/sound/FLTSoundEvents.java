package com.smallmanseries.farlandstraveler.client.sound;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, FarLandsTraveler.MODID);

    // 方块音效
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HOLY_MOSS_BREAK = register("block.holy_moss.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HOLY_MOSS_STEP = register("block.holy_moss.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HOLY_MOSS_PLACE = register("block.holy_moss.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HOLY_MOSS_HIT = register("block.holy_moss.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_HOLY_MOSS_FALL = register("block.holy_moss.fall");

    public static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, name)));
    }
}
