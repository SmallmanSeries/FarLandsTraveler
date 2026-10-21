package com.smallmanseries.farlandstraveler.data.generators;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.particle.FLTParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class FLTParticleGen extends ParticleDescriptionProvider {
    public FLTParticleGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(FLTParticleTypes.PRIMITIVE_ENDER_SHOCKWAVE.get(),
                Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "primitive_ender_shockwave"),
                Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "primitive_ender_shockwave_far"));
    }
}
