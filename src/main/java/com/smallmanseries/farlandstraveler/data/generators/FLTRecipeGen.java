package com.smallmanseries.farlandstraveler.data.generators;

import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class FLTRecipeGen extends RecipeProvider {
    public FLTRecipeGen(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        this.oneToOneConversionRecipe(FLTItems.FAKE_CHUNK_MARKER, FLTItems.TEST_ITEM, "test", 64);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new FLTRecipeGen(registries, output);
        }

        @Override
        public String getName() {
            return "Far Lands Traveler Recipes";
        }
    }
}
