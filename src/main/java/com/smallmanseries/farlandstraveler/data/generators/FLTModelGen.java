package com.smallmanseries.farlandstraveler.data.generators;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.data.resources.model.FLTTexturedModels;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class FLTModelGen extends ModelProvider {
    public FLTModelGen(PackOutput output) {
        super(output, FarLandsTraveler.MODID);
    }

    @Override
    public void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateBlockModels(blockModels);
        generateItemModels(itemModels);
    }

    private void generateBlockModels(BlockModelGenerators blockModels) {
        // 测试方块
        blockModels.createTrivialCube(FLTBlocks.TEST_BLOCK.get());
        // 无效方块
        blockModels.createTrivialCube(FLTBlocks.INVALID_BLOCK.get());
        // 地表方块
        blockModels.createTrivialCube(FLTBlocks.GLOWING_OBSIDIAN.get());
        blockModels.createTrivialBlock(FLTBlocks.ANCIENT_GRASS_BLOCK.get(), FLTTexturedModels.CUBE_ANCIENT);
        blockModels.createTrivialBlock(FLTBlocks.ANCIENT_STONE.get(), FLTTexturedModels.CUBE_ANCIENT);
        // 其他植物
        // 小型花 & 小型花盆栽
        blockModels.createPlantWithDefaultItem(FLTBlocks.FAR_LANDS_ROSE.get(), FLTBlocks.POTTED_FAR_LANDS_ROSE.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(FLTBlocks.CYAN_FAR_LANDS_ROSE.get(), FLTBlocks.POTTED_CYAN_FAR_LANDS_ROSE.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(FLTBlocks.FAR_LANDS_PAEONIA.get(), FLTBlocks.POTTED_FAR_LANDS_PAEONIA.get(), BlockModelGenerators.PlantType.NOT_TINTED);
    }

    private void generateItemModels(ItemModelGenerators itemModels) {
        // 物品
        itemModels.generateFlatItem(FLTItems.PRIMITIVE_ENDER_CORE.get(), ModelTemplates.FLAT_ITEM);
        // 刷怪蛋
        itemModels.generateFlatItem(FLTItems.PRIMITIVE_ENDERMAN_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        // 测试物品
        itemModels.generateFlatItem(FLTItems.TEST_ITEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(FLTItems.FAKE_CHUNK_MARKER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(FLTItems.SPAWN_TEST_ENTITY.get(), ModelTemplates.FLAT_ITEM);
    }
}
