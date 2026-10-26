package com.smallmanseries.farlandstraveler.mixin.worldgen.biome;

import com.smallmanseries.farlandstraveler.common.worldgen.biomes.BiomeInjectors;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiNoiseBiomeSource.class, priority = 900)
public abstract class MultiNoiseBiomeSourceMixin extends BiomeSourceMixin {

    // 暴力兼容TerraBlender：如果在边境之地，直接取消掉TerraBlender的操作
    @Inject(method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;", at = @At("HEAD"), cancellable = true)
    private void modifyNoiseBiome(int quartX, int quartY, int quartZ, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
        if (this.FLT$injected && FarLands.isInFarLands((quartX - 1) << 2, quartY << 2, (quartZ - 1) << 2, 16)) {
            cir.setReturnValue(BiomeInjectors.FAR_BIOMES.getNoiseBiome(quartX, quartY, quartZ, sampler));
        }
    }
}
