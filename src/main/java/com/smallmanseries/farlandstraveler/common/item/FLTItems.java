package com.smallmanseries.farlandstraveler.common.item;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.entity.FLTEntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FarLandsTraveler.MODID);

    // 物品列表
    // 刷怪蛋（边境之地原生生物的刷怪蛋叫“生成 XXX”，其他刷怪蛋叫“XXX刷怪蛋”）
    public static final DeferredItem<SpawnEggItem> PRIMITIVE_ENDERMAN_SPAWN_EGG = ITEMS.registerItem("primitive_enderman_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(FLTEntityTypes.PRIMITIVE_ENDERMAN.get()));

    // 测试物品
    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerItem("test_item", Item::new, Item.Properties::new);
    public static final DeferredItem<Item> FAKE_CHUNK_MARKER = ITEMS.registerItem("fake_chunk_marker", FakeChunkMarkerItem::new, Item.Properties::new);
}
