package com.smallmanseries.farlandstraveler.data.generators;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.client.sound.FLTSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class FLTSoundGen extends SoundDefinitionsProvider {
    public FLTSoundGen(PackOutput output) {
        super(output, FarLandsTraveler.MODID);
    }

    @Override
    public void registerSounds() {

        // 方块音效
        // 经典草方块
        this.add(FLTSoundEvents.BLOCK_CLASSIC_GRASS_BREAK,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/grass1")),
                        sound(Identifier.withDefaultNamespace("dig/grass2")),
                        sound(Identifier.withDefaultNamespace("dig/grass3")),
                        sound(Identifier.withDefaultNamespace("dig/grass4"))
                ).subtitle("subtitles.block.generic.break"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_GRASS_FALL,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/grass1")),
                        sound(Identifier.withDefaultNamespace("dig/grass2")),
                        sound(Identifier.withDefaultNamespace("dig/grass3")),
                        sound(Identifier.withDefaultNamespace("dig/grass4"))
                ).subtitle("subtitles.block.generic.fall"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_GRASS_HIT,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/grass1")),
                        sound(Identifier.withDefaultNamespace("dig/grass2")),
                        sound(Identifier.withDefaultNamespace("dig/grass3")),
                        sound(Identifier.withDefaultNamespace("dig/grass4"))
                ).subtitle("subtitles.block.generic.hit"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_GRASS_PLACE,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/grass1")),
                        sound(Identifier.withDefaultNamespace("dig/grass2")),
                        sound(Identifier.withDefaultNamespace("dig/grass3")),
                        sound(Identifier.withDefaultNamespace("dig/grass4"))
                ).subtitle("subtitles.block.generic.place"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_GRASS_STEP,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/grass1")),
                        sound(Identifier.withDefaultNamespace("dig/grass2")),
                        sound(Identifier.withDefaultNamespace("dig/grass3")),
                        sound(Identifier.withDefaultNamespace("dig/grass4"))
                ).subtitle("subtitles.block.generic.footsteps"));

        // 经典石头
        this.add(FLTSoundEvents.BLOCK_CLASSIC_STONE_BREAK,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")),
                        sound(Identifier.withDefaultNamespace("dig/stone2")),
                        sound(Identifier.withDefaultNamespace("dig/stone3")),
                        sound(Identifier.withDefaultNamespace("dig/stone4"))
                ).subtitle("subtitles.block.generic.break"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_STONE_FALL,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")),
                        sound(Identifier.withDefaultNamespace("dig/stone2")),
                        sound(Identifier.withDefaultNamespace("dig/stone3")),
                        sound(Identifier.withDefaultNamespace("dig/stone4"))
                ).subtitle("subtitles.block.generic.fall"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_STONE_HIT,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")),
                        sound(Identifier.withDefaultNamespace("dig/stone2")),
                        sound(Identifier.withDefaultNamespace("dig/stone3")),
                        sound(Identifier.withDefaultNamespace("dig/stone4"))
                ).subtitle("subtitles.block.generic.hit"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_STONE_PLACE,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")),
                        sound(Identifier.withDefaultNamespace("dig/stone2")),
                        sound(Identifier.withDefaultNamespace("dig/stone3")),
                        sound(Identifier.withDefaultNamespace("dig/stone4"))
                ).subtitle("subtitles.block.generic.place"));
        this.add(FLTSoundEvents.BLOCK_CLASSIC_STONE_STEP,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")),
                        sound(Identifier.withDefaultNamespace("dig/stone2")),
                        sound(Identifier.withDefaultNamespace("dig/stone3")),
                        sound(Identifier.withDefaultNamespace("dig/stone4"))
                ).subtitle("subtitles.block.generic.footsteps"));

        // 携带版石头
        this.add(FLTSoundEvents.BLOCK_PE_STONE_BREAK,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")).pitch(1.25),
                        sound(Identifier.withDefaultNamespace("dig/stone2")).pitch(1.25),
                        sound(Identifier.withDefaultNamespace("dig/stone3")).pitch(1.25),
                        sound(Identifier.withDefaultNamespace("dig/stone4")).pitch(1.25)
                ).subtitle("subtitles.block.generic.break"));
        this.add(FLTSoundEvents.BLOCK_PE_STONE_FALL,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")),
                        sound(Identifier.withDefaultNamespace("dig/stone2")),
                        sound(Identifier.withDefaultNamespace("dig/stone3")),
                        sound(Identifier.withDefaultNamespace("dig/stone4"))
                ).subtitle("subtitles.block.generic.fall"));
        this.add(FLTSoundEvents.BLOCK_PE_STONE_HIT,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")).pitch(2),
                        sound(Identifier.withDefaultNamespace("dig/stone2")).pitch(2),
                        sound(Identifier.withDefaultNamespace("dig/stone3")).pitch(2),
                        sound(Identifier.withDefaultNamespace("dig/stone4")).pitch(2)
                ).subtitle("subtitles.block.generic.hit"));
        this.add(FLTSoundEvents.BLOCK_PE_STONE_PLACE,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")).pitch(1.25),
                        sound(Identifier.withDefaultNamespace("dig/stone2")).pitch(1.25),
                        sound(Identifier.withDefaultNamespace("dig/stone3")).pitch(1.25),
                        sound(Identifier.withDefaultNamespace("dig/stone4")).pitch(1.25)
                ).subtitle("subtitles.block.generic.place"));
        this.add(FLTSoundEvents.BLOCK_PE_STONE_STEP,
                definition().with(
                        sound(Identifier.withDefaultNamespace("dig/stone1")),
                        sound(Identifier.withDefaultNamespace("dig/stone2")),
                        sound(Identifier.withDefaultNamespace("dig/stone3")),
                        sound(Identifier.withDefaultNamespace("dig/stone4"))
                ).subtitle("subtitles.block.generic.footsteps"));

        // 物品音效
        // 原始末影核心
        this.add(FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_USE,
                definition().with(
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/use"))
                ).subtitle("subtitles.farlandstraveler.item.primitive_ender_core.use"));
        this.add(FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_USE_FAR,
                definition().with(
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/use_far"))
                ).subtitle("subtitles.farlandstraveler.item.primitive_ender_core.use"));
        this.add(FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_BREAK,
                definition().with(
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/break1")).pitch(1.25).volume(0.4),
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/break2")).pitch(1.25).volume(0.4),
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/break3")).pitch(1.25).volume(0.4)
                ).subtitle("subtitles.farlandstraveler.item.primitive_ender_core.break"));
        this.add(FLTSoundEvents.ITEM_PRIMITIVE_ENDER_CORE_BREAK_FAR,
                definition().with(
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/break_far1")).pitch(1.25).volume(0.4),
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/break_far2")).pitch(1.25).volume(0.4),
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "item/primitive_ender_core/break_far3")).pitch(1.25).volume(0.4)
                ).subtitle("subtitles.farlandstraveler.item.primitive_ender_core.break"));

        // 效果音效
        this.add(FLTSoundEvents.EFFECT_PRECISION_LOSS_FAILURE,
                definition().with(
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "mob_effect/precision_loss_failure"))
                ).subtitle("subtitles.farlandstraveler.weather.some_noise"));

        // 实体音效
        this.add(FLTSoundEvents.ENTITY_GENERIC_WU,
                definition().with(
                        sound(Identifier.withDefaultNamespace("random/classic_hurt"))
                ).subtitle("subtitles.farlandstraveler.entity.wu"));

        // 音乐
        this.add(FLTSoundEvents.MUSIC_FAR_LANDS_UNIVERSAL,
                definition().with(
                        // 专辑 - 「边境旅者：启程」
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "music/the_far_lands_traveler")).volume(0.4).stream(),
                        // sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "music/magnificent_walls")).volume(0.4).stream(),
                        sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "music/deep_tunnel")).volume(0.4).stream()
                        // sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "music/stack_continents")).volume(0.4).stream(),
                        // sound(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "music/high_wall_and_a_secret_place")).volume(0.4).stream()
                        // 专辑 - 「边境旅者：奇境」
                        // 专辑 - 「边境旅者：虹现」
                ));
    }
}
