package com.smallmanseries.farlandstraveler.data.generators.tags;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.entity.FLTEntityTypes;
import com.smallmanseries.farlandstraveler.common.misc.FLTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;

import java.util.concurrent.CompletableFuture;

public class FLTEntityTypeTagGen extends EntityTypeTagsProvider {
    public FLTEntityTypeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FarLandsTraveler.MODID);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {

        // Far Lands Traveler
        this.tag(FLTTags.EntityTypes.ILLUSION_EFFECT_IMMUNE).add(
                FLTEntityTypes.TEST_ENTITY.get() // 测试用占位符
        );
    }
}
