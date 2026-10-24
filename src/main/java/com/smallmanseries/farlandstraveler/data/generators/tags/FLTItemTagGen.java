package com.smallmanseries.farlandstraveler.data.generators.tags;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class FLTItemTagGen extends ItemTagsProvider {
    public FLTItemTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FarLandsTraveler.MODID);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {

        // Far Lands Traveler
        this.tag(FLTTags.Items.ANVIL_SQUASHABLE).add(
                FLTItems.PRIMITIVE_ENDER_CORE.get()
        );
        this.tag(FLTTags.Items.ILLUSION_EFFECT_NO_EFFECT).add(
                FLTItems.FAKE_CHUNK_MARKER.get(),
                FLTItems.TEST_ITEM.get() // 测试用占位符
        );
        this.tag(FLTTags.Items.ILLUSION_EFFECT_IMMUNE).add(
                Items.TURTLE_HELMET // 测试用占位符
        );

    }
}
