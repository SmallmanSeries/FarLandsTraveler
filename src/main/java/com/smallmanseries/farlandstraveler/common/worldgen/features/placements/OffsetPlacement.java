package com.smallmanseries.farlandstraveler.common.worldgen.features.placements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class OffsetPlacement extends PlacementModifier {
    public static final MapCodec<OffsetPlacement> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    IntProviders.CODEC.fieldOf("x_offset").forGetter(OffsetPlacement::xOffset),
                    IntProviders.CODEC.fieldOf("y_offset").forGetter(OffsetPlacement::yOffset),
                    IntProviders.CODEC.fieldOf("z_offset").forGetter(OffsetPlacement::zOffset)
            ).apply(inst, OffsetPlacement::new)
    );

    private final IntProvider xOffset;
    private final IntProvider yOffset;
    private final IntProvider zOffset;

    public OffsetPlacement(IntProvider xOffset, IntProvider yOffset, IntProvider zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    public static OffsetPlacement of(IntProvider xOffset, IntProvider yOffset, IntProvider zOffset) {
        return new OffsetPlacement(xOffset, yOffset, zOffset);
    }

    public static OffsetPlacement of(IntProvider yOffset) {
        return new OffsetPlacement(ConstantInt.ZERO, yOffset, ConstantInt.ZERO);
    }

    public IntProvider xOffset() {
        return this.xOffset;
    }
    public IntProvider yOffset() {
        return this.yOffset;
    }
    public IntProvider zOffset() {
        return this.zOffset;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        return Stream.of(origin.offset(this.xOffset.sample(random), this.yOffset.sample(random), this.zOffset.sample(random)));
    }

    @Override
    public PlacementModifierType<?> type() {
        return FLTPlacementModifiers.OFFSET.get();
    }
}
