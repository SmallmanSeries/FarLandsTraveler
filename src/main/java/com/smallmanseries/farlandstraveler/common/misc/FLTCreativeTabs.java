package com.smallmanseries.farlandstraveler.common.misc;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.block.FLTBlocks;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FLTCreativeTabs {
    /*
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FarLandsTraveler.MODID);

    // 创造模式物品栏列表
    // 方块
    public static final Supplier<CreativeModeTab> FLT_BLOCKS = TABS.register("flt_blocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_blocks"))
            .icon(() -> new ItemStack(FLTBlocks.ASD.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_items"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTBlocks.ASD);
            }))
            .build());
    // 物品
    public static final Supplier<CreativeModeTab> FLT_ITEMS = TABS.register("flt_items", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.farlandstraveler.flt_items"))
            .icon(() -> new ItemStack(FLTItems.ALL.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_blocks"))
            // .withTabsAfter(ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, "flt_equipments"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FLTItems.ALL);
                output.accept(FLTItems.QWE);
            }))
            .build());
    // 探境装备
    // 探境补给品
    // 刷怪蛋
    // 调试用品

    public static void registerCreativeTabs(IEventBus modEventBus){
        TABS.register(modEventBus);
    }
     */
}
