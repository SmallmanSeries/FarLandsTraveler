package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.smallmanseries.farlandstraveler.common.DataRegister;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLandsAccess;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin {

    @Final
    @Mutable
    @Shadow private Holder<NoiseGeneratorSettings> settings;

    @Inject(method = "createNoiseChunk", at = @At("RETURN"), cancellable = true)
    private void modifyNoiseChunk(ChunkAccess chunk, StructureManager structureManager, Blender blender, RandomState random, CallbackInfoReturnable<NoiseChunk> cir) {
        if (chunk.getPos().getMinBlockX() >= 12550824){
            FarLandsAccess access = new FarLandsAccess();
            settings = access.getFarLands(FarLands.FAR_LANDS).settings();
        }
        cir.setReturnValue(cir.getReturnValue());
    }

}
