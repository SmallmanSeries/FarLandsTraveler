package com.smallmanseries.farlandstraveler.common.item;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FarLandsTraveler.MODID);

    // 物品列表
    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerItem("test_item", Item::new, new Item.Properties());
    public static final DeferredItem<Item> TEST_ITEM1 = ITEMS.registerItem("test_item1", Item::new, new Item.Properties());
}
