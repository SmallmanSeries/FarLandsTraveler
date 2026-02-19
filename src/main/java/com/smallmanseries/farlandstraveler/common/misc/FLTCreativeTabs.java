package com.smallmanseries.farlandstraveler.common.misc;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FLTCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FarLandsTraveler.MODID);

    // 创造模式物品栏列表
    // test
    public static final Supplier<CreativeModeTab> FLT_TEST = TABS.register("flt_test", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_test"))
            .icon(() -> new ItemStack(FLTBlocks.TEST_BLOCK.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            // .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_blocks"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
                output.accept(FLTItems.TEST_ITEM1);
                output.accept(FLTBlocks.TEST_BLOCK);
            }))
            .build());
    // 建筑方块
    public static final Supplier<CreativeModeTab> FLT_BUILDING_BLOCKS = TABS.register("flt_blocks_building", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_blocks_building"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 自然方块
    public static final Supplier<CreativeModeTab> FLT_NATURAL_BLOCKS = TABS.register("flt_blocks_natural", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_blocks_natural"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 物品
    public static final Supplier<CreativeModeTab> FLT_ITEMS = TABS.register("flt_items", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_items"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 探境装备
    // 探境补给品
    // 刷怪蛋
    // 调试用品

    public static void register(IEventBus modEventBus){
        TABS.register(modEventBus);
    }

}
