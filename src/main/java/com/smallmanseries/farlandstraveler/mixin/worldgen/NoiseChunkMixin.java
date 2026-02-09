package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoiseChunk.class)
public abstract class NoiseChunkMixin {
    @Unique
    private NoiseRouter farLandsTraveler$router;

    // 如果在对应维度（还没做）且坐标大于指定值时，获取noise settings里的路由器，否则获取random里的（原版一律为random里的）
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/NoiseSettings;getCellHeight()I"))
    private void getRandomRouter(int cellCountXZ, RandomState random, int firstNoiseX, int firstNoiseZ, NoiseSettings noiseSettings, DensityFunctions.BeardifierOrMarker beardifier, NoiseGeneratorSettings noiseGeneratorSettings, Aquifer.FluidPicker fluidPicker, Blender blendifier, CallbackInfo ci){
        if((firstNoiseX > (Config.FAR_LANDS_DISTANCE.getAsInt() - 16)
                || firstNoiseZ > (Config.FAR_LANDS_DISTANCE.getAsInt() - 16)
                || firstNoiseX < -(Config.FAR_LANDS_DISTANCE.getAsInt())
                || firstNoiseZ < -(Config.FAR_LANDS_DISTANCE.getAsInt()))) {// 判断条件还没加维度
            this.farLandsTraveler$router = noiseGeneratorSettings.noiseRouter();
        } else {
            this.farLandsTraveler$router = random.router();
        }
    }

    // 应用路由器
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/RandomState;router()Lnet/minecraft/world/level/levelgen/NoiseRouter;"))
    private NoiseRouter applyRandomRouter(RandomState state) {
        return this.farLandsTraveler$router;
    }

}


