package com.smallmanseries.farlandstraveler.mixin.worldgen;

import com.smallmanseries.farlandstraveler.Config;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Aquifer.NoiseBasedAquifer.class)
public abstract class AquiferMixin {

    @Shadow
    @Final
    private Aquifer.FluidPicker globalFluidPicker;

    // 替换硬编码的岩浆层
    @Inject(method = "computeSubstance", at = @At("HEAD"), cancellable = true)
    private void replaceLavaLayers(DensityFunction.FunctionContext context, double substance, CallbackInfoReturnable<BlockState> cir){
        int x = context.blockX();
        int y = context.blockY();
        int z = context.blockZ();
        if((Math.max(x, z) >= (Config.FAR_LANDS_DISTANCE.getAsInt() - 3) || Math.min(x, z) <= -Config.FAR_LANDS_DISTANCE.getAsInt()) && substance <= 0.0 && this.globalFluidPicker.computeFluid(x, y, z).at(y).is(Blocks.LAVA)) {
            cir.setReturnValue(Blocks.WATER.defaultBlockState());
        }
    }
}
