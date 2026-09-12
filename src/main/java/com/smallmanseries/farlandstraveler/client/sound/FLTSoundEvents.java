package com.smallmanseries.farlandstraveler.client.sound;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, FarLandsTraveler.MODID);

    // 方块音效
    // 经典草方块
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_GRASS_BREAK = register("block.classic_grass.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_GRASS_STEP = register("block.classic_grass.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_GRASS_PLACE = register("block.classic_grass.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_GRASS_HIT = register("block.classic_grass.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_GRASS_FALL = register("block.classic_grass.fall");

    // 经典石头
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_STONE_BREAK = register("block.classic_stone.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_STONE_STEP = register("block.classic_stone.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_STONE_PLACE = register("block.classic_stone.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_STONE_HIT = register("block.classic_stone.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CLASSIC_STONE_FALL = register("block.classic_stone.fall");

    // 携带版石头
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_PE_STONE_BREAK = register("block.pe_stone.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_PE_STONE_STEP = register("block.pe_stone.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_PE_STONE_PLACE = register("block.pe_stone.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_PE_STONE_HIT = register("block.pe_stone.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_PE_STONE_FALL = register("block.pe_stone.fall");

    // 物品音效
    // 原始末影核心
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_PRIMITIVE_ENDER_CORE_USE = register("item.primitive_ender_core.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_PRIMITIVE_ENDER_CORE_BREAK = register("item.primitive_ender_core.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_PRIMITIVE_ENDER_CORE_USE_FAR = register("item.primitive_ender_core.use_far");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_PRIMITIVE_ENDER_CORE_BREAK_FAR = register("item.primitive_ender_core.break_far");

    // 效果音效
    public static final DeferredHolder<SoundEvent, SoundEvent> EFFECT_PRECISION_LOSS_FAILURE = register("effect.precision_loss.failure");

    // 实体音效
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_GENERIC_WU = register("entity.generic.wu");

    // 音乐
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_FAR_LANDS_UNIVERSAL = register("music.far_lands.universal");

    public static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name)));
    }
}
