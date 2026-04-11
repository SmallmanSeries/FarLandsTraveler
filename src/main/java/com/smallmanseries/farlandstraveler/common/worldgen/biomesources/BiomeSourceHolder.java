package com.smallmanseries.farlandstraveler.common.worldgen.biomesources;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.common.DataRegister;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.biome.BiomeSource;

public class BiomeSourceHolder {

    public static final Codec<BiomeSource> DIRECT_CODEC = BiomeSource.CODEC;
    public static final Codec<Holder<BiomeSource>> HOLDER_CODEC = RegistryFileCodec.create(DataRegister.BIOME_SOURCE, DIRECT_CODEC);

}
