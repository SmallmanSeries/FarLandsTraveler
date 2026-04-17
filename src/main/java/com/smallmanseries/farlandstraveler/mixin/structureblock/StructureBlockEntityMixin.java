package com.smallmanseries.farlandstraveler.mixin.structureblock;

import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StructureBlockEntity.class)
public abstract class StructureBlockEntityMixin {

    @ModifyConstant(method = "loadAdditional", constant = @Constant(intValue = 48))
    private int maxLoadSize(int value) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "loadAdditional", constant = @Constant(intValue = -48))
    private int minLoadSize(int value) {
        return Integer.MIN_VALUE;
    }

    @ModifyConstant(method = "detectSize", constant = @Constant(intValue = 80))
    private int maxDetectSize(int value) {
        return Integer.MAX_VALUE;
    }
}
