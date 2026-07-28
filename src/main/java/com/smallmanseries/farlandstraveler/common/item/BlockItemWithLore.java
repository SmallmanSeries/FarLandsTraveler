package com.smallmanseries.farlandstraveler.common.item;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class BlockItemWithLore extends BlockItem {
    public BlockItemWithLore(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable(this.descriptionId + ".lore").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
