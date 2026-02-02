package com.smallmanseries.farlandstraveler.mixin.worldgen;

import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NoiseChunk.class)
public abstract class NoiseChunkMixin {

    @Final
    @Mutable
    @Shadow private final NoiseSettings noiseSettings;

    protected NoiseChunkMixin(NoiseSettings noiseSettings) {
        this.noiseSettings = noiseSettings;
    }
}
