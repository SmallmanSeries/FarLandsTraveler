package com.smallmanseries.farlandstraveler.data.generators.loot;

import com.smallmanseries.farlandstraveler.common.misc.FLTLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record FLTChestLootGen(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {

        // indev小屋早期方块箱子（占位）
        consumer.accept(FLTLootTables.CHESTS_EB_INDEV_BLOCKS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 100))
                        .add(LootItem.lootTableItem(Items.STONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GRASS_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.COBBLESTONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_SAPLING).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.BEDROCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.SAND).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GRAVEL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GOLD_ORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.IRON_ORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.COAL_ORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_LOG).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_LEAVES).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.SPONGE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GLASS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.DANDELION).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.POPPY).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.RED_MUSHROOM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GOLD_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.SMOOTH_STONE_SLAB).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.BRICKS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.TNT).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.BOOKSHELF).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.MOSSY_COBBLESTONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.TORCH).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.CHEST).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(8, 16))
                        .add(LootItem.lootTableItem(Items.WHITE_WOOL))
                        .add(LootItem.lootTableItem(Items.BLACK_WOOL))
                        .add(LootItem.lootTableItem(Items.BLUE_WOOL))
                        .add(LootItem.lootTableItem(Items.BROWN_WOOL))
                        .add(LootItem.lootTableItem(Items.CYAN_WOOL))
                        .add(LootItem.lootTableItem(Items.GRAY_WOOL))
                        .add(LootItem.lootTableItem(Items.GREEN_WOOL))
                        .add(LootItem.lootTableItem(Items.LIGHT_BLUE_WOOL))
                        .add(LootItem.lootTableItem(Items.LIGHT_GRAY_WOOL))
                        .add(LootItem.lootTableItem(Items.LIME_WOOL))
                        .add(LootItem.lootTableItem(Items.MAGENTA_WOOL))
                        .add(LootItem.lootTableItem(Items.ORANGE_WOOL))
                        .add(LootItem.lootTableItem(Items.PINK_WOOL))
                        .add(LootItem.lootTableItem(Items.PURPLE_WOOL))
                        .add(LootItem.lootTableItem(Items.RED_WOOL))
                        .add(LootItem.lootTableItem(Items.YELLOW_WOOL))
                )
        );

        // indev小屋方块箱子（占位）
        consumer.accept(FLTLootTables.CHESTS_EB_INDEV_BLOCKS_NOWOOL, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 100))
                        .add(LootItem.lootTableItem(Items.STONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GRASS_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.COBBLESTONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_SAPLING).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.BEDROCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.SAND).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GRAVEL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GOLD_ORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.IRON_ORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.COAL_ORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_LOG).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OAK_LEAVES).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.SPONGE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GLASS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.DANDELION).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.POPPY).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.RED_MUSHROOM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.GOLD_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.SMOOTH_STONE_SLAB).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.BRICKS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.TNT).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.BOOKSHELF).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.MOSSY_COBBLESTONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.TORCH).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                        .add(LootItem.lootTableItem(Items.CHEST).apply(SetItemCountFunction.setCount(ConstantValue.exactly(99))))
                )
        );

        // indev小屋羊毛箱子
        consumer.accept(FLTLootTables.CHESTS_EB_INDEV_BLOCKS_WOOL, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(8, 16))
                        .add(LootItem.lootTableItem(Items.WHITE_WOOL))
                        .add(LootItem.lootTableItem(Items.BLACK_WOOL))
                        .add(LootItem.lootTableItem(Items.BLUE_WOOL))
                        .add(LootItem.lootTableItem(Items.BROWN_WOOL))
                        .add(LootItem.lootTableItem(Items.CYAN_WOOL))
                        .add(LootItem.lootTableItem(Items.GRAY_WOOL))
                        .add(LootItem.lootTableItem(Items.GREEN_WOOL))
                        .add(LootItem.lootTableItem(Items.LIGHT_BLUE_WOOL))
                        .add(LootItem.lootTableItem(Items.LIGHT_GRAY_WOOL))
                        .add(LootItem.lootTableItem(Items.LIME_WOOL))
                        .add(LootItem.lootTableItem(Items.MAGENTA_WOOL))
                        .add(LootItem.lootTableItem(Items.ORANGE_WOOL))
                        .add(LootItem.lootTableItem(Items.PINK_WOOL))
                        .add(LootItem.lootTableItem(Items.PURPLE_WOOL))
                        .add(LootItem.lootTableItem(Items.RED_WOOL))
                        .add(LootItem.lootTableItem(Items.YELLOW_WOOL))
                )
        );

        // indev小屋TNT箱子
        consumer.accept(FLTLootTables.CHESTS_EB_INDEV_TNT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 4))
                        .add(LootItem.lootTableItem(Items.TNT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                )
        );

        // indev小屋工具箱子
        consumer.accept(FLTLootTables.CHESTS_EB_INDEV_TOOLS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1))
                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 4))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(Items.BOW).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(Items.WOODEN_SWORD).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 1F))))
                        .add(LootItem.lootTableItem(Items.WOODEN_SHOVEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 1F))))
                        .add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 1F))))
                        .add(LootItem.lootTableItem(Items.WOODEN_AXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 1F))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(Items.STONE_SWORD).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.STONE_SHOVEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.STONE_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.STONE_AXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(Items.IRON_SWORD).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.IRON_SHOVEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.IRON_AXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.8F))))
                )
        );

        // 岩石之令实验室飞行驳船箱子，提供9个红石粉和1个黏液球（实际上黏液球不是必要的，因为故事模式里修复飞行驳船用的黏性活塞其实可以换成普通活塞）
        consumer.accept(FLTLootTables.CHESTS_OOTSLAB_MAZE_BARGE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(9, 18))
                        .add(LootItem.lootTableItem(Items.REDSTONE))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 4))
                        .add(LootItem.lootTableItem(Items.SLIME_BALL))
                )
        );

        // 岩石之令实验室 小屋壁炉箱子，提供木板以制作拉杆（故事模式中Jesse身上本来就有圆石）
        consumer.accept(FLTLootTables.CHESTS_OOTSLAB_COTTAGE_FIREPLACE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 5))
                        .add(LootItem.lootTableItem(Items.OAK_PLANKS))
                )
        );

        // 岩石之令实验室 小屋阁楼箱子（这个箱子在故事模式中没有出现过，属于模组自由发挥。那就添加一点符合Ivor特征的物品吧，比如瓶子和书）
        consumer.accept(FLTLootTables.CHESTS_OOTSLAB_COTTAGE_ATTIC, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE))
                        .add(LootItem.lootTableItem(Items.BOOK))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3, 7))
                        .add(LootItem.lootTableItem(Items.COBWEB).setWeight(5))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 5))))
                )
        );

        // 岩石之令实验室 地下室武器箱子。Jesse在这里找到了3个钻石，5个红石块和12个黏性活塞
        consumer.accept(FLTLootTables.CHESTS_OOTSLAB_BASEMENT_WEAPON, LootTable.lootTable()
                // 黏性活塞
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 4))
                        .add(LootItem.lootTableItem(Items.STICKY_PISTON).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 8))))
                )
                // 红石块
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 5))
                        .add(LootItem.lootTableItem(Items.REDSTONE_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                )
                // 固定生成钻石
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5))))
                )
                // 其他材料（自由发挥）
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 3))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 5))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                )
        );

        // 岩石之令实验室 地下室武器箱子左边那个大箱子（在故事模式中出现但没有揭示其内容，自由发挥）
        consumer.accept(FLTLootTables.CHESTS_OOTSLAB_BASEMENT_TOOLS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(Items.WOODEN_SWORD).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.2F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.WOODEN_SHOVEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.2F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.2F, 0.8F))))
                        .add(LootItem.lootTableItem(Items.WOODEN_AXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.2F, 0.8F))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 2))
                        .add(LootItem.lootTableItem(Items.STONE_SWORD).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                        .add(LootItem.lootTableItem(Items.STONE_SHOVEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                        .add(LootItem.lootTableItem(Items.STONE_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                        .add(LootItem.lootTableItem(Items.STONE_AXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1))
                        .add(LootItem.lootTableItem(Items.IRON_SWORD).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                        .add(LootItem.lootTableItem(Items.IRON_SHOVEL).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                        .add(LootItem.lootTableItem(Items.IRON_AXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.4F))))
                )
        );

        // 废弃村庄铁匠铺箱子。还原一下旧版村庄铁匠铺的战利品表
        consumer.accept(FLTLootTables.CHESTS_AV_NORMAL_BLACKSMITH, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3, 8))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(5))
                        .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(5))
                        .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(5))
                        .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(5))
                        .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(5))
                        .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(5))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 7))))
                        .add(LootItem.lootTableItem(Items.OAK_SAPLING).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 7))))
                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(1))
                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(1))
                )
        );
    }
}
