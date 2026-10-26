package com.smallmanseries.farlandstraveler.mixin.worldgen.biome;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.smallmanseries.farlandstraveler.common.worldgen.biomes.IBiomeSourceExtension;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import org.spongepowered.asm.mixin.*;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(BiomeSource.class)
public abstract class BiomeSourceMixin implements IBiomeSourceExtension {

    @Unique
    protected boolean FLT$injected = false;
    @Mutable
    @Shadow
    @Final
    private Supplier<Set<Holder<Biome>>> possibleBiomes;

    @Shadow
    public abstract Stream<Holder<Biome>> collectPossibleBiomes();

    @Unique
    public void FLT$injectBiomes(BiomeSource farBiomes) {
        if (this.FLT$injected) return;

        this.possibleBiomes = Suppliers.memoize(() -> {
            Set<Holder<Biome>> biomes = this.collectPossibleBiomes().collect(Collectors.toSet());
            biomes.addAll(farBiomes.possibleBiomes());
            return ImmutableSet.copyOf(biomes);
        });

        this.FLT$injected = true;
    }
}
