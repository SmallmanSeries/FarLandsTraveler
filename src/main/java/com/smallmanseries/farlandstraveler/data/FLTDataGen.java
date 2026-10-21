package com.smallmanseries.farlandstraveler.data;

import com.smallmanseries.farlandstraveler.data.generators.FLTModelGen;
import com.smallmanseries.farlandstraveler.data.generators.FLTParticleGen;
import com.smallmanseries.farlandstraveler.data.generators.FLTRecipeGen;
import com.smallmanseries.farlandstraveler.data.generators.FLTSoundGen;
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
        generator.addProvider(true, new FLTModelGen(packOutput));
        generator.addProvider(true, new FLTParticleGen(packOutput));
        generator.addProvider(true, new FLTSoundGen(packOutput));
    }
}
