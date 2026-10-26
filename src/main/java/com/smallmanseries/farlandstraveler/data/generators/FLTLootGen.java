package com.smallmanseries.farlandstraveler.data.generators;

import com.smallmanseries.farlandstraveler.common.misc.FLTLootTables;
import com.smallmanseries.farlandstraveler.data.generators.loot.FLTBlockLootGen;
import com.smallmanseries.farlandstraveler.data.generators.loot.FLTChestLootGen;
import com.smallmanseries.farlandstraveler.data.generators.loot.FLTEntityLootGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FLTLootGen {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new LootTableProvider(output, FLTLootTables.IMMUTABLE_LOCATIONS, List.of(
                new LootTableProvider.SubProviderEntry(FLTBlockLootGen::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(FLTChestLootGen::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(FLTEntityLootGen::new, LootContextParamSets.ENTITY)
        ), registries
        );
    }
}
