package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.smallmanseries.farlandstraveler.common.DataRegister;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin {

    @Shadow
    @Final
    private static Logger LOGGER;

    @ModifyArgs(method = "applyStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/status/ChunkStep;apply(Lnet/minecraft/world/level/chunk/status/WorldGenContext;Lnet/minecraft/util/StaticCache2D;Lnet/minecraft/world/level/chunk/ChunkAccess;)Ljava/util/concurrent/CompletableFuture;"))
    private void modifyGenerator(Args args){
        WorldGenContext context = args.get(0);
        ChunkAccess chunk = args.get(2);
        ChunkGenerator generator = context.generator();
        ServerLevel level = context.level();
        //边境之地
        if (context.level().dimension() == Level.OVERWORLD &&
                (chunk.getPos().getMinBlockX() > 12550824
                || chunk.getPos().getMinBlockZ() > 12550824
                || chunk.getPos().getMaxBlockX() < -12550824
                || chunk.getPos().getMaxBlockZ() < -12550824)) {
            generator = new NoiseBasedChunkGenerator(
                    context.level().registryAccess().lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS).biomeSource(),
                    context.level().registryAccess().lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS).settings()
            );
            level.getChunkSource().randomState();
        }
        //遥远之地
        //边缘之地
        //边缘之角
        //基岩海
        WorldGenContext modifiedContext = new WorldGenContext(level, generator, context.structureManager(), context.lightEngine(), context.mainThreadExecutor(), context.unsavedListener());
        args.set(0, modifiedContext);

    }
}
