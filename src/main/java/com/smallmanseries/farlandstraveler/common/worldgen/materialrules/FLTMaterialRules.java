package com.smallmanseries.farlandstraveler.common.worldgen.materialrules;

import com.mojang.serialization.MapCodec;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.neoforge.registries.DeferredRegister;

// Todo 26.3及以后的版本，原版自带材料规则功能，本模组的此功能可以删除
public class FLTMaterialRules {
    public static final DeferredRegister<MapCodec<? extends SurfaceRules.RuleSource>> MATERIAL_RULES = DeferredRegister.create(Registries.MATERIAL_RULE, FarLandsTraveler.MODID);

    static {
        MATERIAL_RULES.register("holder", () -> MaterialRuleHolder.CODEC);
    }

}
