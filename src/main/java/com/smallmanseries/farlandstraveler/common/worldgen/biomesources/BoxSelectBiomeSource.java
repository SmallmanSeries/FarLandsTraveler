package com.smallmanseries.farlandstraveler.common.worldgen.biomesources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.stream.Stream;

public class BoxSelectBiomeSource extends BiomeSource {
    private static final Codec<Integer> INPUT_RANGE = Codec.intRange(0, Integer.MAX_VALUE);
    public static final MapCodec<BoxSelectBiomeSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.INT.fieldOf("origin_x").forGetter(instance1 -> instance1.originX),
                            Codec.INT.fieldOf("origin_y").forGetter(instance11 -> instance11.originY),
                            Codec.INT.fieldOf("origin_z").forGetter(instance12 -> instance12.originZ),
                            INPUT_RANGE.fieldOf("extend_x").forGetter(instance13 -> instance13.extendX),
                            INPUT_RANGE.fieldOf("extend_y").forGetter(instance14 -> instance14.extendY),
                            INPUT_RANGE.fieldOf("extend_z").forGetter(instance15 -> instance15.extendZ),
                            BiomeSourceHolder.HOLDER_CODEC.fieldOf("inside").forGetter(instance2 -> instance2.inside),
                            BiomeSourceHolder.HOLDER_CODEC.fieldOf("outside").forGetter(instance3 -> instance3.outside)
                    )
                    .apply(instance, BoxSelectBiomeSource::new)
    );

    private final int originX;
    private final int originY;
    private final int originZ;
    private final int extendX;
    private final int extendY;
    private final int extendZ;
    private final Holder<BiomeSource> inside;
    private final Holder<BiomeSource> outside;

    public BoxSelectBiomeSource(int originX, int originY, int originZ, int extendX, int extendY, int extendZ, Holder<BiomeSource> inside, Holder<BiomeSource> outside) {
        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;
        this.extendX = extendX;
        this.extendY = extendY;
        this.extendZ = extendZ;
        this.inside = inside;
        this.outside = outside;
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.concat(this.inside.value().collectPossibleBiomes(), this.outside.value().collectPossibleBiomes());
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        int endX = originX + extendX;
        int endY = originY + extendY;
        int endZ = originZ + extendZ;
        if (x >= originX && x < endX
                && y >= originY && y < endY
                && z >= originZ && z < endZ){
            return this.inside.value().getNoiseBiome(x, y, z, sampler);
        }
        return this.outside.value().getNoiseBiome(x, y, z, sampler);
    }

    @Override
    public void addDebugInfo(List<String> info, BlockPos pos, Climate.Sampler sampler) {
        int endX = originX + extendX;
        int endY = originY + extendY;
        int endZ = originZ + extendZ;
        if (pos.getX() >= originX && pos.getX() < endX
                && pos.getY() >= originY && pos.getY() < endY
                && pos.getZ() >= originZ && pos.getZ() < endZ){
            this.inside.value().addDebugInfo(info, pos, sampler);
        } else {
            this.outside.value().addDebugInfo(info, pos, sampler);
        }
    }
}
