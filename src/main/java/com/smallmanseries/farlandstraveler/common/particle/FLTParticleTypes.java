package com.smallmanseries.farlandstraveler.common.particle;

import com.mojang.serialization.MapCodec;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class FLTParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, FarLandsTraveler.MODID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<PEShockwaveParticleOptions>> PRIMITIVE_ENDER_SHOCKWAVE = register("primitive_ender_shockwave", false, _ -> PEShockwaveParticleOptions.CODEC, _ -> PEShockwaveParticleOptions.STREAM_CODEC);

    private static <T extends ParticleOptions> DeferredHolder<ParticleType<?>, ParticleType<T>> register(String name, boolean overrideLimiter, final Function<ParticleType<T>, MapCodec<T>> codec, final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec) {
        return PARTICLE_TYPES.register(name, () -> new ParticleType<T>(overrideLimiter) {
            public MapCodec<T> codec() {
                return codec.apply(this);
            }

            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return streamCodec.apply(this);
            }
        });
    }
}
