package com.smallmanseries.farlandstraveler.client.sound;

import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class FLTSoundTypes {
    public static final SoundType HOLY_MOSS = new DeferredSoundType(
            2.0F,
            1.0F,
            FLTSoundEvents.BLOCK_HOLY_MOSS_BREAK,
            FLTSoundEvents.BLOCK_HOLY_MOSS_STEP,
            FLTSoundEvents.BLOCK_HOLY_MOSS_PLACE,
            FLTSoundEvents.BLOCK_HOLY_MOSS_HIT,
            FLTSoundEvents.BLOCK_HOLY_MOSS_FALL);
}
