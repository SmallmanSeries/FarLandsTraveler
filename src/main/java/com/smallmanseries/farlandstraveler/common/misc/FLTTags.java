package com.smallmanseries.farlandstraveler.common.misc;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class FLTTags {
    public static class Items {
        public static final TagKey<Item> DESOLID_EFFECT_NO_EFFECT = createTag("desolid_effect_no_effect"); // 手持打上此标签的物品可以与假区块中的方块正常交互
        public static final TagKey<Item> DESOLID_EFFECT_IMMUNE = createTag("desolid_effect_immune"); // 生物穿戴打上此标签的物品后，将免疫假区块

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> DESOLID_EFFECT_NO_EFFECT = createTag("desolid_effect_no_effect"); // 打上此标签的方块不受假区块的影响，即使处在假区块中也依然有碰撞、遮挡光照、可以交互

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> DESOLID_EFFECT_IMMUNE = createTag("desolid_effect_immune"); // 打上此标签的实体免疫假区块。用于边境之地原生生物

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_STRUCTURE_ABANDONED_VILLAGE_NORMAL = createTag("has_structure/abandoned_village/normal");
        public static final TagKey<Biome> HAS_STRUCTURE_EXPLORER_BASE_INDEV_COBBLESTONE = createTag("has_structure/explorer_base/indev_cobblestone");
        public static final TagKey<Biome> HAS_STRUCTURE_EXPLORER_BASE_INDEV_WOOD = createTag("has_structure/explorer_base/indev_wood");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(FarLandsTraveler.MODID, name));
        }
    }
}
