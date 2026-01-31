package com.smallmanseries.farlandstraveler.common.block;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FLTBlocks {
    /*
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FarLandsTraveler.MODID);

    //方块列表
    public static final DeferredBlock<Block> ASD = RegisterBlockItem("asd", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1346237208F, 30071926F)));

    private static <T extends Block> DeferredBlock<T> RegisterBlockItem(String name, Supplier<T> block){
        DeferredBlock<T> obj = BLOCKS.register(name, block);
        FLTItems.ITEMS.register(name, () -> new BlockItem(obj.get(), new Item.Properties()));
        return obj;
    }
    */
}
