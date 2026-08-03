package com.smallmanseries.farlandstraveler.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

/**
 * 带工具提示的方块物品。工具提示的翻译键是方块的翻译键加上“.lore”
 */
public class BlockItemWithLore extends BlockItem {
    public BlockItemWithLore(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable(this.descriptionId + ".lore").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
