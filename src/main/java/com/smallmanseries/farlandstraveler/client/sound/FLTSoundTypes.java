package com.smallmanseries.farlandstraveler.client.sound;

import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class FLTSoundTypes {
    public static final SoundType CLASSIC_GRASS = new DeferredSoundType(
            2.0F,
            1.0F,
            FLTSoundEvents.BLOCK_CLASSIC_GRASS_BREAK,
            FLTSoundEvents.BLOCK_CLASSIC_GRASS_STEP,
            FLTSoundEvents.BLOCK_CLASSIC_GRASS_PLACE,
            FLTSoundEvents.BLOCK_CLASSIC_GRASS_HIT,
            FLTSoundEvents.BLOCK_CLASSIC_GRASS_FALL);

    public static final SoundType CLASSIC_STONE = new DeferredSoundType(
            2.0F,
            1.0F,
            FLTSoundEvents.BLOCK_CLASSIC_STONE_BREAK,
            FLTSoundEvents.BLOCK_CLASSIC_STONE_STEP,
            FLTSoundEvents.BLOCK_CLASSIC_STONE_PLACE,
            FLTSoundEvents.BLOCK_CLASSIC_STONE_HIT,
            FLTSoundEvents.BLOCK_CLASSIC_STONE_FALL);

    public static final SoundType PE_STONE = new DeferredSoundType(
            1.0F,
            1.0F,
            FLTSoundEvents.BLOCK_PE_STONE_BREAK,
            FLTSoundEvents.BLOCK_PE_STONE_STEP,
            FLTSoundEvents.BLOCK_PE_STONE_PLACE,
            FLTSoundEvents.BLOCK_PE_STONE_HIT,
            FLTSoundEvents.BLOCK_PE_STONE_FALL);
}
