package com.smallmanseries.farlandstraveler.data.generators.loot;

import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FLTBlockLootGen extends BlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(
                    FLTItems.TEST_ITEM // 占位
            )
            .map(ItemLike::asItem)
            .collect(Collectors.toSet());

    public FLTBlockLootGen(HolderLookup.Provider registries) {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        HolderLookup.RegistryLookup<Item> items = this.registries.lookupOrThrow(Registries.ITEM);

        // 地表方块
        this.dropSelf(FLTBlocks.GLOWING_OBSIDIAN.get());
        this.dropSelf(FLTBlocks.ANCIENT_GRASS_BLOCK.get());
        this.dropSelf(FLTBlocks.ANCIENT_STONE.get());

        // 小型花
        this.dropSelf(FLTBlocks.FAR_LANDS_ROSE.get());
        this.dropSelf(FLTBlocks.CYAN_FAR_LANDS_ROSE.get());
        this.dropSelf(FLTBlocks.FAR_LANDS_PAEONIA.get());

        // 小型花盆栽
        this.dropPottedContents(FLTBlocks.POTTED_FAR_LANDS_ROSE.get());
        this.dropPottedContents(FLTBlocks.POTTED_CYAN_FAR_LANDS_ROSE.get());
        this.dropPottedContents(FLTBlocks.POTTED_FAR_LANDS_PAEONIA.get());

    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return FLTBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}
