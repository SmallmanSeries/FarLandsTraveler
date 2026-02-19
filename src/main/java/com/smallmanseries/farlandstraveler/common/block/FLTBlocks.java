package com.smallmanseries.farlandstraveler.common.block;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class FLTBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FarLandsTraveler.MODID);

    // 方块列表
    public static final DeferredBlock<Block> TEST_BLOCK = RegisterBlockItem("test_block", Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(1346237208F, 30071926F));



    private static <T extends Block> DeferredBlock<T> RegisterBlockItem(String name, Function<Block.Properties, T> function, BlockBehaviour.Properties prop){
        DeferredBlock<T> obj = BLOCKS.register(name, (key) -> function.apply(prop.setId(ResourceKey.create(Registries.BLOCK, key))));
        FLTItems.ITEMS.register(name, (key) -> new BlockItem(obj.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, key))));
        return obj;
    }

    public static void register(IEventBus modEventBus){
        BLOCKS.register(modEventBus);
    }
}
