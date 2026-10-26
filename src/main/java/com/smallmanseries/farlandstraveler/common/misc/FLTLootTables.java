package com.smallmanseries.farlandstraveler.common.misc;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FLTLootTables {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet<>();
    public static final Set<ResourceKey<LootTable>> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    // chests
    // 探境者基地-Indev房子
    public static final ResourceKey<LootTable> CHESTS_EB_INDEV_BLOCKS = register("chests/explorer_base/indev_blocks");
    public static final ResourceKey<LootTable> CHESTS_EB_INDEV_BLOCKS_NOWOOL = register("chests/explorer_base/indev_blocks_nowool");
    public static final ResourceKey<LootTable> CHESTS_EB_INDEV_BLOCKS_WOOL = register("chests/explorer_base/indev_blocks_wool");
    public static final ResourceKey<LootTable> CHESTS_EB_INDEV_TNT = register("chests/explorer_base/indev_tnt");
    public static final ResourceKey<LootTable> CHESTS_EB_INDEV_TOOLS = register("chests/explorer_base/indev_tools");
    // 岩石之令实验室
    public static final ResourceKey<LootTable> CHESTS_OOTSLAB_BASEMENT_TOOLS = register("chests/oots_laboratory/basement_tools");
    public static final ResourceKey<LootTable> CHESTS_OOTSLAB_BASEMENT_WEAPON = register("chests/oots_laboratory/basement_weapon");
    public static final ResourceKey<LootTable> CHESTS_OOTSLAB_COTTAGE_ATTIC = register("chests/oots_laboratory/cottage_attic");
    public static final ResourceKey<LootTable> CHESTS_OOTSLAB_COTTAGE_FIREPLACE = register("chests/oots_laboratory/cottage_fireplace");
    public static final ResourceKey<LootTable> CHESTS_OOTSLAB_MAZE_BARGE = register("chests/oots_laboratory/maze_barge");
    // 废弃村庄-铁匠铺
    public static final ResourceKey<LootTable> CHESTS_AV_NORMAL_BLACKSMITH = register("chests/abanconed_village/normal_blacksmith");

    private static ResourceKey<LootTable> register(String id) {
        ResourceKey<LootTable> location = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, id));
        if (LOCATIONS.add(location)) {
            return location;
        }
        throw new IllegalArgumentException(location.identifier() + " is already a registered loot table");
    }
}
