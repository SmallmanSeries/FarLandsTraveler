package com.smallmanseries.farlandstraveler.mixin.worldgen.biome;

import com.llamalad7.mixinextras.sugar.Local;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Todo 26.3之后，理论上无需mixin来实现自定义恶地表面规则，改成一个单独的“材料规则”类型来实现。
@Mixin(SurfaceSystem.class)
public class SurfaceSystemMixin {

    @Unique
    private NormalNoise bandOffset;

    @Unique
    private static final ResourceKey<NormalNoise.NoiseParameters> SUNSET_BAND_OFFSET_NOISE = ResourceKey.create(Registries.NOISE, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "sunset_band_offset"));

    // 获取偏移噪声
    @Inject(method = "<init>", at = @At("RETURN"))
    private void getDensityFunction(RandomState randomState, BlockState defaultBlock, int seaLevel, PositionalRandomFactory noiseRandom, CallbackInfo ci) {
        bandOffset = randomState.getOrCreateNoise(SUNSET_BAND_OFFSET_NOISE);
    }

    // 自定义恶地表面规则（bandlands，中文直译：条带之地）
    @ModifyVariable(method = "getBand", at = @At("STORE"), name = "offset")
    private int modifyOffset(int offset, @Local(argsOnly = true, name = "worldX") int worldX, @Local(argsOnly = true, name = "worldZ") int worldZ) {
        if (FarLands.isInFarLands(worldX, 0, worldZ, 4)) {
            offset += (int) (bandOffset.getValue(worldX, 0.0, worldZ) * 8);
        }
        return offset;
    }
}
