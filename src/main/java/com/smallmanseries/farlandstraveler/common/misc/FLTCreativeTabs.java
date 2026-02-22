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
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FLTCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FarLandsTraveler.MODID);

    // 创造模式物品栏列表
    // 测试用品
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
    public static final Supplier<CreativeModeTab> FLT_BUILDING_BLOCKS = TABS.register("flt_building_blocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_building_blocks"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 自然方块
    public static final Supplier<CreativeModeTab> FLT_NATURAL_BLOCKS = TABS.register("flt_natural_blocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_natural_blocks"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_building_blocks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTBlocks.FAR_LANDS_ROSE);
                output.accept(FLTBlocks.CYAN_FAR_LANDS_ROSE);
                output.accept(FLTBlocks.FAR_LANDS_PAEONIA);
                output.accept(FLTBlocks.GLOWING_OBSIDIAN);
            }))
            .build());
    // 功能方块
    public static final Supplier<CreativeModeTab> FLT_FUNCTIONAL_BLOCKS = TABS.register("flt_functional_blocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_functional_blocks"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_natural_blocks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 物品
    public static final Supplier<CreativeModeTab> FLT_ITEMS = TABS.register("flt_items", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_items"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_functional_blocks"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 探境工具
    public static final Supplier<CreativeModeTab> FLT_TOOLS = TABS.register("flt_tools", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_tools"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_items"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 探境装备
    public static final Supplier<CreativeModeTab> FLT_EQUIPMENTS = TABS.register("flt_equipments", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_equipments"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_tools"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 探境补给品
    public static final Supplier<CreativeModeTab> FLT_SUPPLIES = TABS.register("flt_supplies", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_supplies"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_equipments"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());
    // 刷怪蛋
    public static final Supplier<CreativeModeTab> FLT_SPAWN_EGGS = TABS.register("flt_spawn_eggs", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_spawn_eggs"))
            .icon(() -> new ItemStack(FLTItems.TEST_ITEM.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_supplies"))
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_test"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.TEST_ITEM);
            }))
            .build());

}
