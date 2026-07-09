package com.smallmanseries.farlandstraveler.common.item;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FarLandsTraveler.MODID);

    // 物品列表
    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerItem("test_item", Item::new, Item.Properties::new);
    public static final DeferredItem<Item> FAKE_CHUNK_MARKER = ITEMS.registerItem("fake_chunk_marker", FakeChunkMarkerItem::new, Item.Properties::new);
}
