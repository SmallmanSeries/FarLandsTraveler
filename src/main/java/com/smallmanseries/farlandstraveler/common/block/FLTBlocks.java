package com.smallmanseries.farlandstraveler.common.block;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class FLTBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FarLandsTraveler.MODID);

    // 方块列表
    // 测试方块
    public static final DeferredBlock<Block> TEST_BLOCK = RegisterBlockItem("test_block", Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(1346237208F, 30071926F));

    // 地表方块
    public static final DeferredBlock<Block> GLOWING_OBSIDIAN = RegisterBlockItem("glowing_obsidian", Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 12)
                    .pushReaction(PushReaction.BLOCK)
                    .strength(50.0F, 1200.0F));

    // 地底方块

    // 矿石

    // 树木及其制品（顺序：原木、木、去皮原木、去皮木、木板、楼梯、台阶、栅栏、栅栏门、门、活板门、压力板、按钮、树叶、繁花树叶（若有）、垂藤（若有）、光体（如白玉树晶）、其他）
    // 苍境树

    // 白玉树

    // ...

    // 其他植物
    // 小型花
    public static final DeferredBlock<Block> FAR_LANDS_ROSE = RegisterBlockItem("far_lands_rose", (prop) -> new FlowerBlock(MobEffects.NIGHT_VISION, 5.0F, prop), Block.Properties.ofFullCopy(Blocks.POPPY));
    public static final DeferredBlock<Block> CYAN_FAR_LANDS_ROSE = RegisterBlockItem("cyan_far_lands_rose", (prop) -> new FlowerBlock(MobEffects.NIGHT_VISION, 5.0F, prop), Block.Properties.ofFullCopy(Blocks.POPPY));
    public static final DeferredBlock<Block> FAR_LANDS_PAEONIA = RegisterBlockItem("far_lands_paeonia", (prop) -> new FlowerBlock(MobEffects.NIGHT_VISION, 5.0F, prop), Block.Properties.ofFullCopy(Blocks.POPPY));

    // 树苗

    // 菌类（按植物处理）

    // 巨型菌类

    // 小型花盆栽
    public static final DeferredBlock<FlowerPotBlock> POTTED_FAR_LANDS_ROSE = BLOCKS.registerBlock("potted_far_lands_rose", (prop) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FAR_LANDS_ROSE, prop), Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CYAN_FAR_LANDS_ROSE = BLOCKS.registerBlock("potted_cyan_far_lands_rose", (prop) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CYAN_FAR_LANDS_ROSE, prop), Block.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<FlowerPotBlock> POTTED_FAR_LANDS_PAEONIA = BLOCKS.registerBlock("potted_far_lands_paeonia", (prop) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FAR_LANDS_PAEONIA, prop), Block.Properties.ofFullCopy(Blocks.FLOWER_POT));

    // 树苗盆栽

    // 菌类盆栽


    // 注册
    private static <T extends Block> DeferredBlock<T> RegisterBlockItem(String name, Function<Block.Properties, T> function, BlockBehaviour.Properties prop){
        DeferredBlock<T> obj = BLOCKS.register(name, (key) -> function.apply(prop.setId(ResourceKey.create(Registries.BLOCK, key))));
        FLTItems.ITEMS.register(name, (key) -> new BlockItem(obj.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, key))));
        return obj;
    }

    public static void registerPots(){
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(FAR_LANDS_ROSE.get()), POTTED_FAR_LANDS_ROSE);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(CYAN_FAR_LANDS_ROSE.get()), POTTED_CYAN_FAR_LANDS_ROSE);
        pot.addPlant(BuiltInRegistries.BLOCK.getKey(FAR_LANDS_PAEONIA.get()), POTTED_FAR_LANDS_PAEONIA);
    }

    public static void registerFlammability(){
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(FAR_LANDS_ROSE.get(), 60, 100);
        fire.setFlammable(CYAN_FAR_LANDS_ROSE.get(), 60, 100);
        fire.setFlammable(FAR_LANDS_PAEONIA.get(), 60, 100);

    }
}
