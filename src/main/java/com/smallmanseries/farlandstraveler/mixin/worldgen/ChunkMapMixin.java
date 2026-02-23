package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.mojang.datafixers.DataFixer;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.DataRegister;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TicketStorage;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin {

    @Shadow
    @Final
    private RandomState randomState;

    @Unique
    public RandomState randomStateFarLands = this.randomState;

    // 计算噪声路由器（不计算会导致生成基岩海）
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/RandomState;create(Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;Lnet/minecraft/core/HolderGetter;J)Lnet/minecraft/world/level/levelgen/RandomState;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getRandom(ServerLevel level,
                           LevelStorageSource.LevelStorageAccess levelStorageAccess,
                           DataFixer fixerUpper,
                           StructureTemplateManager structureManager,
                           Executor dispatcher,
                           BlockableEventLoop mainThreadExecutor,
                           LightChunkGetter lightChunk,
                           ChunkGenerator generator,
                           ChunkProgressListener progressListener,
                           ChunkStatusUpdateListener chunkStatusListener,
                           Supplier overworldDataStorage,
                           TicketStorage ticketStorage,
                           int serverViewDistance,
                           boolean sync,
                           CallbackInfo ci,
                           Path path,
                           RegistryAccess registryaccess,
                           long i
                           ) {
        // 边境之地
        this.randomStateFarLands = RandomState.create(level.registryAccess().lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS).settings().value(), registryaccess.lookupOrThrow(Registries.NOISE), i);
        // 遥远之地
        // 边缘之墙
        // 边缘之角
        // 基岩海
    }

    // 应用世界生成器
    @ModifyArgs(method = "applyStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/status/ChunkStep;apply(Lnet/minecraft/world/level/chunk/status/WorldGenContext;Lnet/minecraft/util/StaticCache2D;Lnet/minecraft/world/level/chunk/ChunkAccess;)Ljava/util/concurrent/CompletableFuture;"))
    private void modifyGenerator(Args args){
        // 获取一些必要的数据
        WorldGenContext context = args.get(0);
        ChunkAccess chunk = args.get(2);
        ChunkGenerator generator = context.generator();
        ServerLevel level = context.level();
        // 开始替换生成器
        // 边境之地边缘
        if (context.level().dimension() == Level.OVERWORLD &&
                (chunk.getPos().getMaxBlockX() > Config.FAR_LANDS_DISTANCE.getAsInt()
                || chunk.getPos().getMaxBlockZ() > Config.FAR_LANDS_DISTANCE.getAsInt()
                || chunk.getPos().getMinBlockX() < -(Config.FAR_LANDS_DISTANCE.getAsInt())
                || chunk.getPos().getMinBlockZ() < -(Config.FAR_LANDS_DISTANCE.getAsInt()))) {
            // 这里需要获取噪声设置，并计算这个噪声设置中的NoiseRouter，不然会生成基岩海
            NoiseGeneratorSettings settings = context.level().registryAccess().lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS_EDGE).settings().value();
            NoiseGeneratorSettings settingsMapped = new NoiseGeneratorSettings(
                    settings.noiseSettings(),
                    settings.defaultBlock(),
                    settings.defaultFluid(),
                    this.randomStateFarLands.router(),
                    settings.surfaceRule(),
                    settings.spawnTarget(),
                    settings.seaLevel(),
                    settings.disableMobGeneration(),
                    settings.aquifersEnabled(),
                    settings.oreVeinsEnabled(),
                    settings.useLegacyRandomSource());
            Holder<NoiseGeneratorSettings> settingsHolder = Holder.direct(settingsMapped);
            generator = new NoiseBasedChunkGenerator(
                    context.level().registryAccess().lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS_EDGE).biomeSource(),
                    settingsHolder
            );
        }// 以下同理
        // 边境之地
        if (context.level().dimension() == Level.OVERWORLD &&
                (chunk.getPos().getMinBlockX() > Config.FAR_LANDS_DISTANCE.getAsInt()
                        || chunk.getPos().getMinBlockZ() > Config.FAR_LANDS_DISTANCE.getAsInt()
                        || chunk.getPos().getMaxBlockX() < -(Config.FAR_LANDS_DISTANCE.getAsInt())
                        || chunk.getPos().getMaxBlockZ() < -(Config.FAR_LANDS_DISTANCE.getAsInt()))) {
            // 这里需要获取噪声设置，并计算这个噪声设置中的NoiseRouter，不然会生成基岩海
            NoiseGeneratorSettings settings = context.level().registryAccess().lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS).settings().value();
            NoiseGeneratorSettings settingsMapped = new NoiseGeneratorSettings(
                    settings.noiseSettings(),
                    settings.defaultBlock(),
                    settings.defaultFluid(),
                    this.randomStateFarLands.router(),
                    settings.surfaceRule(),
                    settings.spawnTarget(),
                    settings.seaLevel(),
                    settings.disableMobGeneration(),
                    settings.aquifersEnabled(),
                    settings.oreVeinsEnabled(),
                    settings.useLegacyRandomSource());
            Holder<NoiseGeneratorSettings> settingsHolder = Holder.direct(settingsMapped);
            generator = new NoiseBasedChunkGenerator(
                    context.level().registryAccess().lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(FarLands.FAR_LANDS).biomeSource(),
                    settingsHolder
            );
        }
        // 遥远之地
        // 边缘之地
        // 边缘之角
        // 基岩海

        // 应用生成器
        WorldGenContext modifiedContext = new WorldGenContext(level, generator, context.structureManager(), context.lightEngine(), context.mainThreadExecutor(), context.unsavedListener());
        args.set(0, modifiedContext);

    }
}
