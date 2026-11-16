package com.smallmanseries.farlandstraveler.common.worldgen.features.placements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class VerticalOffsetPlacement extends PlacementModifier {
    public static final MapCodec<VerticalOffsetPlacement> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    IntProviders.CODEC.fieldOf("offset").forGetter(VerticalOffsetPlacement::offset)
            ).apply(inst, VerticalOffsetPlacement::new)
    );

    private final IntProvider offset;

    public VerticalOffsetPlacement(IntProvider offset) {
        this.offset = offset;
    }

    public static VerticalOffsetPlacement of(IntProvider offset) {
        return new VerticalOffsetPlacement(offset);
    }

    public IntProvider offset() {
        return this.offset;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        return Stream.of(origin.offset(0, this.offset.sample(random), 0));
    }

    @Override
    public PlacementModifierType<?> type() {
        return FLTPlacementModifiers.VERTICAL_OFFSET.get();
    }
}
