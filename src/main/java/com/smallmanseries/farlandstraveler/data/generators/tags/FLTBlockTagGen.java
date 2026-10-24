package com.smallmanseries.farlandstraveler.data.generators.tags;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class FLTBlockTagGen extends BlockTagsProvider {
    public FLTBlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FarLandsTraveler.MODID);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {

        // Far Lands Traveler
        this.tag(FLTTags.Blocks.ILLUSION_EFFECT_NO_EFFECT).add(
                FLTBlocks.TEST_BLOCK.get()
        );

        // Minecraft
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                FLTBlocks.GLOWING_OBSIDIAN.get(),
                FLTBlocks.ANCIENT_STONE.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                FLTBlocks.ANCIENT_GRASS_BLOCK.get()
        );
        this.tag(BlockTags.BEE_ATTRACTIVE).add(
                FLTBlocks.FAR_LANDS_ROSE.get(),
                FLTBlocks.CYAN_FAR_LANDS_ROSE.get(),
                FLTBlocks.FAR_LANDS_PAEONIA.get()
        );
        this.tag(BlockTags.SMALL_FLOWERS).add(
                FLTBlocks.FAR_LANDS_ROSE.get(),
                FLTBlocks.CYAN_FAR_LANDS_ROSE.get(),
                FLTBlocks.FAR_LANDS_PAEONIA.get()
        );
        this.tag(BlockTags.DRAGON_IMMUNE).add(
                FLTBlocks.GLOWING_OBSIDIAN.get()
        );
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                FLTBlocks.GLOWING_OBSIDIAN.get()
        );
    }
}
