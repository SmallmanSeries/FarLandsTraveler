package com.smallmanseries.farlandstraveler.common.worldgen.structures;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.structures.abandonedvillage.AbandonedVillageStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, FarLandsTraveler.MODID);

    public static final DeferredHolder<StructureType<?>, StructureType<OOTSLaboratoryStructure>> OOTS_LABORATORY = STRUCTURES.register("oots_laboratory", () -> () -> OOTSLaboratoryStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<AbandonedVillageStructure>> ABANDONED_VILLAGE = STRUCTURES.register("abandoned_village", () -> () -> AbandonedVillageStructure.CODEC);
}
