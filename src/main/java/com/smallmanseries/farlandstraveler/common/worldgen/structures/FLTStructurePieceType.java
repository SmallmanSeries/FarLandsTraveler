package com.smallmanseries.farlandstraveler.common.worldgen.structures;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.abandonedvillage.AbandonedVillagePieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTStructurePieceType {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(Registries.STRUCTURE_PIECE, FarLandsTraveler.MODID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> OOTS_LABORATORY_MAZE = STRUCTURE_PIECES.register("oots_laboratory_maze", () -> OOTSLaboratoryStructure.Piece::new);

    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_WELL = STRUCTURE_PIECES.register("abandoned_village_well", () -> AbandonedVillagePieces.Well::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_HUT = STRUCTURE_PIECES.register("abandoned_village_hut", () -> AbandonedVillagePieces.Hut::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_COTTAGE = STRUCTURE_PIECES.register("abandoned_village_cottage", () -> AbandonedVillagePieces.Cottage::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_HOUSE = STRUCTURE_PIECES.register("abandoned_village_house", () -> AbandonedVillagePieces.House::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_BUTCHER_SHOP = STRUCTURE_PIECES.register("abandoned_village_butcher_shop", () -> AbandonedVillagePieces.ButcherShop::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_LIBRARY = STRUCTURE_PIECES.register("abandoned_village_library", () -> AbandonedVillagePieces.Library::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_LARGE_FARM = STRUCTURE_PIECES.register("abandoned_village_large_farm", () -> AbandonedVillagePieces.LargeFarm::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_FARM = STRUCTURE_PIECES.register("abandoned_village_farm", () -> AbandonedVillagePieces.Farm::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_SMITHY = STRUCTURE_PIECES.register("abandoned_village_smithy", () -> AbandonedVillagePieces.Smithy::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_CHURCH = STRUCTURE_PIECES.register("abandoned_village_church", () -> AbandonedVillagePieces.Church::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_STREETLIGHT = STRUCTURE_PIECES.register("abandoned_village_streetlight", () -> AbandonedVillagePieces.Streetlight::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType.ContextlessType> ABANDONED_VILLAGE_ROAD = STRUCTURE_PIECES.register("abandoned_village_road", () -> AbandonedVillagePieces.Road::new);
}
