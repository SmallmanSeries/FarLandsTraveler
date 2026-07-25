package com.smallmanseries.farlandstraveler.common.worldgen.materialrules;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.smallmanseries.farlandstraveler.common.DataRegister;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

// Todo 26.3及以后的版本，原版自带材料规则功能，本模组的此功能可以删除
public class MaterialRuleHolder extends SurfaceRules implements SurfaceRules.RuleSource {
    public static final Codec<RuleSource> DIRECT_CODEC = RuleSource.CODEC;
    public static final Codec<Holder<RuleSource>> HOLDER_CODEC = RegistryFileCodec.create(DataRegister.MATERIAL_RULE, DIRECT_CODEC);
    private final Holder<RuleSource> rule;

    public static final MapCodec<MaterialRuleHolder> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    HOLDER_CODEC.fieldOf("rule").forGetter(inst -> inst.rule)
            ).apply(instance, MaterialRuleHolder::new)
    );

    public MaterialRuleHolder(Holder<RuleSource> rule) {
        this.rule = rule;
    }

    @Override
    public KeyDispatchDataCodec<? extends RuleSource> codec() {
        return KeyDispatchDataCodec.of(CODEC);
    }

    @Override
    public SurfaceRule apply(Context context) {
        return rule.value().apply(context);
    }
}
