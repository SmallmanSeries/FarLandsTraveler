package com.smallmanseries.farlandstraveler.common.worldgen.structure.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

public class FixedStructurePlacement extends StructurePlacement {
    public static final MapCodec<FixedStructurePlacement> CODEC = RecordCodecBuilder.mapCodec(
                    instance -> placementCodec(instance)
                            .and(
                                    instance.group(
                                            Codec.INT.fieldOf("x").forGetter(ins -> ins.x),
                                            Codec.INT.fieldOf("z").forGetter(ins -> ins.z)
                                    )
                            )
                            .apply(instance, FixedStructurePlacement::new)
            );
    private final int x;
    private final int z;
    private final ChunkPos pos;

    protected FixedStructurePlacement(Vec3i locateOffset, FrequencyReductionMethod frequencyReductionMethod, float frequency, int salt, Optional<ExclusionZone> exclusionZone, int x, int z) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone);
        this.x = x;
        this.z = z;
        this.pos = new ChunkPos(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
    }

    @Override
    protected boolean isPlacementChunk(ChunkGeneratorStructureState chunkGeneratorStructureState, int x, int z) {
        return this.pos.x == x && this.pos.z == z;
    }

    public BlockPos getPos() {
        return new BlockPos(this.x, 64, this.z);
    }

    @Override
    public StructurePlacementType<?> type() {
        return FLTStructurePlacements.FIXED.get();
    }
}
