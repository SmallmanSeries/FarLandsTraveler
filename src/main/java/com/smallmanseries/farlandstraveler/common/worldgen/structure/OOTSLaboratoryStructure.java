package com.smallmanseries.farlandstraveler.common.worldgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class OOTSLaboratoryStructure extends Structure {
    public static final MapCodec<OOTSLaboratoryStructure> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    settingsCodec(instance),
                            BlockPos.CODEC.fieldOf("pos").forGetter(ins -> ins.pos),
                    Codec.STRING.fieldOf("part").forGetter(ins -> ins.part)
                    ).apply(instance, OOTSLaboratoryStructure::new)
    );
    private static final ResourceLocation EXTERIOR = ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "oots_laboratory/exterior");
    private static final ResourceLocation GATE = ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "oots_laboratory/gate");
    private static final ResourceLocation MAZE = ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "oots_laboratory/maze");
    private static final ResourceLocation COTTAGE = ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "oots_laboratory/cottage");

    public final BlockPos pos;
    public final String part;


    protected OOTSLaboratoryStructure(StructureSettings settings, BlockPos pos, String part) {
        super(settings);
        this.pos = pos;
        this.part = part;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if(Config.GENERATE_OOTS_LABORATORY.getAsBoolean()) {
            return Optional.of(new Structure.GenerationStub(this.pos, builder -> {
                switch (this.part) {
                    case "exterior" -> builder.addPiece(new Piece(context.structureTemplateManager(), EXTERIOR, new BlockPos(this.pos.getX() - 11, this.pos.getY(), this.pos.getZ() - 51)));
                    case "gate" -> builder.addPiece(new Piece(context.structureTemplateManager(), GATE, new BlockPos(this.pos.getX() - 73, this.pos.getY() - 10, this.pos.getZ() - 33)));
                    case "maze" -> builder.addPiece(new Piece(context.structureTemplateManager(), MAZE, new BlockPos(this.pos.getX() - 128, this.pos.getY() - 20, this.pos.getZ() - 128)));
                    case "cottage" -> builder.addPiece(new Piece(context.structureTemplateManager(), COTTAGE, new BlockPos(this.pos.getX() - 82, this.pos.getY() - 20, this.pos.getZ() - 50)));
                }
            }));
        }
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return FLTStructures.OOTS_LABORATORY.get();
    }

    public static class Piece extends TemplateStructurePiece {

        private BlockPos pos;

        public Piece(StructureTemplateManager manager, ResourceLocation location, BlockPos pos) {
            super(FLTStructurePieceType.OOTS_LABORATORY_MAZE.get(), 0, manager, location, location.toString(), makeSettings(), pos);
        }

        public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
            super(FLTStructurePieceType.OOTS_LABORATORY_MAZE.get(), tag, context.structureTemplateManager(), id -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings()
                    .setRotation(Rotation.NONE)
                    .setMirror(Mirror.NONE)
                    .setRotationPivot(new BlockPos(0, 0, 0))
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
                    .setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
        }

        @Override
        protected void handleDataMarker(String s, BlockPos blockPos, ServerLevelAccessor serverLevelAccessor, RandomSource randomSource, BoundingBox boundingBox) {

        }
    }
}
