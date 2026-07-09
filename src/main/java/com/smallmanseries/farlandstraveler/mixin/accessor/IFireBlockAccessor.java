package com.smallmanseries.farlandstraveler.mixin.accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface IFireBlockAccessor {
    @Invoker("setFlammable")
    void invokeSetFlammable(Block block, int igniteOdds, int burnOdds);
}
