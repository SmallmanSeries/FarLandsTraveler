package com.smallmanseries.farlandstraveler.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.placement.FixedStructurePlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {

    /**
     * 添加对{@link FixedStructurePlacement}（“固定”放置类型）的特殊判断，使locate指令能正确定位（虽然定位结果常常因整数溢出而不完全准确）
     */
    @Inject(method = "findNearestMapStructure", at = @At(value = "INVOKE", target = "Ljava/util/Map$Entry;getKey()Ljava/lang/Object;"), cancellable = true)
    private void fixedPlacement(ServerLevel level, HolderSet<Structure> wantedStructures, BlockPos pos, int maxSearchRadius, boolean createReference, CallbackInfoReturnable<Pair<BlockPos, Holder<Structure>>> cir, @Local Map.Entry<StructurePlacement, Set<Holder<Structure>>> entry) {
        if (entry.getKey() instanceof FixedStructurePlacement placement) {
            for (Holder<Structure> holder : entry.getValue()) {
                cir.setReturnValue(Pair.of(placement.getPos(), holder));
                return;
            }
        }
    }
}
