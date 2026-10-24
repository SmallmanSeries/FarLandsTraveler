package com.smallmanseries.farlandstraveler.data;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.data.generators.*;
import com.smallmanseries.farlandstraveler.data.generators.tags.FLTBiomeTagGen;
import com.smallmanseries.farlandstraveler.data.generators.tags.FLTBlockTagGen;
import com.smallmanseries.farlandstraveler.data.generators.tags.FLTEntityTypeTagGen;
import com.smallmanseries.farlandstraveler.data.generators.tags.FLTItemTagGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class FLTDataGen {
    public static void data(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // server
        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(
                packOutput,
                lookupProvider,
                FLTRegistrySets.BUILDER,
                Collections.singleton(FarLandsTraveler.MODID)
        );
        generator.addProvider(true, provider);
        generator.addProvider(true, new FLTRecipeGen.Runner(packOutput, lookupProvider));

        // client
        generator.addProvider(true, new FLTModelGen(packOutput));
        generator.addProvider(true, new FLTParticleGen(packOutput));
        generator.addProvider(true, new FLTSoundGen(packOutput));

        // tags
        CompletableFuture<HolderLookup.Provider> registryProvider = provider.getRegistryProvider();
        generator.addProvider(true, new FLTBiomeTagGen(packOutput, registryProvider));
        generator.addProvider(true, new FLTBlockTagGen(packOutput, registryProvider));
        generator.addProvider(true, new FLTEntityTypeTagGen(packOutput, registryProvider));
        generator.addProvider(true, new FLTItemTagGen(packOutput, registryProvider));
    }
}
