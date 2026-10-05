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

/**
 * 原始末影冲击波粒子的设置
 * @param direction 冲击波面向的方向
 * @param life 冲击波的寿命，影响冲击波的扩散时间
 * @param size 冲击波扩散的大小。冲击波会从0逐渐长到这么大，消耗的时间是<code>life</code>指定的时间。
 * @see com.smallmanseries.farlandstraveler.client.particle.PEShockwaveParticle
 */
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
