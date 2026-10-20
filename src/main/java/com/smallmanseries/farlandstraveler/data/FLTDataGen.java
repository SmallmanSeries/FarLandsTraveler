package com.smallmanseries.farlandstraveler.data;

import com.smallmanseries.farlandstraveler.data.generators.FLTRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class FLTDataGen {
    public static void data(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // server
        generator.addProvider(true, new FLTRecipeGen.Runner(packOutput, lookupProvider));

        // client

    }
}
