package com.smallmanseries.farlandstraveler.common.worldgen.structure;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTStructurePieceType {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(Registries.STRUCTURE_PIECE, FarLandsTraveler.MODID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> OOTS_LABORATORY_MAZE = STRUCTURE_PIECES.register("oots_laboratory_maze", () -> OOTSLaboratoryStructure.Piece::new);

}
