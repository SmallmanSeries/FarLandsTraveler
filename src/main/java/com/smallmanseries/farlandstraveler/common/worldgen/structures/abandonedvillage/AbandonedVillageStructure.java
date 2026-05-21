package com.smallmanseries.farlandstraveler.common.worldgen.structures.abandonedvillage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.FLTStructures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.List;
import java.util.Optional;

public class AbandonedVillageStructure extends Structure {
    public static final MapCodec<AbandonedVillageStructure> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    settingsCodec(instance),
                    Codec.INT.fieldOf("size").forGetter(ins -> ins.size),
                    AbandonedVillagePreset.CODEC.fieldOf("preset").forGetter(ins -> ins.preset)
            ).apply(instance, AbandonedVillageStructure::new)
    );
    public final int size;
    private final AbandonedVillagePreset preset;

    public AbandonedVillageStructure(StructureSettings settings, int size, AbandonedVillagePreset preset) {
        super(settings);
        this.size = size;
        this.preset = preset;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return Optional.of(new Structure.GenerationStub(context.chunkPos().getWorldPosition(), builder -> this.generatePieces(builder, context)));
    }

    private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
        RandomSource rand = context.random();
        List<AbandonedVillagePieces.PieceWeight> list = AbandonedVillagePieces.getStructureVillageWeightedPieceList(rand, size);
        AbandonedVillagePieces.Start structurevillagepieces$start = new AbandonedVillagePieces.Start(this.preset, rand, (context.chunkPos().x << 4) + 2, (context.chunkPos().z << 4) + 2, list, size);
        builder.addPiece(structurevillagepieces$start);
        structurevillagepieces$start.addChildren(structurevillagepieces$start, builder, rand);
        List<StructurePiece> roadList = structurevillagepieces$start.pendingRoads;
        List<StructurePiece> houseList = structurevillagepieces$start.pendingHouses;

        while (!roadList.isEmpty() || !houseList.isEmpty()) {
            if (roadList.isEmpty()) {
                int i = rand.nextInt(houseList.size());
                StructurePiece piece1 = houseList.remove(i);
                piece1.addChildren(structurevillagepieces$start, builder, rand);
            } else {
                int j = rand.nextInt(roadList.size());
                StructurePiece piece2 = roadList.remove(j);
                piece2.addChildren(structurevillagepieces$start, builder, rand);
            }
        }
    }

    @Override
    public StructureType<?> type() {
        return FLTStructures.ABANDONED_VILLAGE.get();
    }
}
