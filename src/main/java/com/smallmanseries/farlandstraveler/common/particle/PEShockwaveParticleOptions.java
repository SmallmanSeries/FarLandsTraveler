package com.smallmanseries.farlandstraveler.common.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PEShockwaveParticleOptions(
        Direction direction,
        int life,
        float size
) implements ParticleOptions {
    public static final MapCodec<PEShockwaveParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    Direction.CODEC.fieldOf("direction").forGetter(PEShockwaveParticleOptions::direction),
                    Codec.INT.fieldOf("life").forGetter(PEShockwaveParticleOptions::life),
                    Codec.FLOAT.fieldOf("size").forGetter(PEShockwaveParticleOptions::size)
            ).apply(inst, PEShockwaveParticleOptions::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PEShockwaveParticleOptions> STREAM_CODEC = StreamCodec.composite(
            Direction.STREAM_CODEC, PEShockwaveParticleOptions::direction,
            ByteBufCodecs.VAR_INT, PEShockwaveParticleOptions::life,
            ByteBufCodecs.FLOAT, PEShockwaveParticleOptions::size,
            PEShockwaveParticleOptions::new
    );

    @Override
    public ParticleType<?> getType() {
        return FLTParticleTypes.PRIMITIVE_ENDER_SHOCKWAVE.get();
    }
}
