package com.smallmanseries.farlandstraveler.client.particle;

import com.smallmanseries.farlandstraveler.common.particle.PEShockwaveParticleOptions;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;

public class PEShockwaveParticle extends SingleQuadParticle {
    private final Direction direction;

    public PEShockwaveParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, TextureAtlasSprite sprite, float size, Direction direction) {
        super(level, x, y, z, xa, ya, za, sprite);
        this.friction = 0.96F;
        this.hasPhysics = false;
        this.direction = direction;
        this.scale(size);
        this.quadSize = size;
    }

    @Override
    public void extract(QuadParticleRenderState particleTypeRenderState, Camera camera, float partialTickTime) {
        Quaternionf rotation = new Quaternionf();

        switch (this.direction) {
            case UP -> rotation.rotateX((float) -Math.PI * 0.5F);
            case DOWN -> rotation.rotateX((float) Math.PI * 0.5F);
            case WEST -> rotation.rotateY((float) -Math.PI * 0.5F);
            case EAST -> rotation.rotateY((float) Math.PI * 0.5F);
            case NORTH -> rotation.rotateY((float) Math.PI);
            default -> {
            }
        }

        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);
    }

    @Override
    public int getLightCoords(float a) {
        return LightCoordsUtil.withBlock(super.getLightCoords(a), 15);
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public float getQuadSize(float a) {
        float s = ((float) this.age + a) / (float) this.lifetime;
        return this.quadSize * s * s;
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha -= 0.9F / this.lifetime;
        System.out.println(this.alpha);
    }

    public record Provider(SpriteSet sprite) implements ParticleProvider<PEShockwaveParticleOptions> {
        @Override
        public Particle createParticle(
                PEShockwaveParticleOptions options,
                ClientLevel level,
                double x, double y, double z,
                double xa, double ya, double za,
                RandomSource random
        ) {
            PEShockwaveParticle particle = new PEShockwaveParticle(
                    level,
                    x, y, z,
                    xa, ya, za,
                    this.sprite().get(FarLands.isInFarLands(x, y, z, 3) ? 1 : 0, 1),
                    options.size(),
                    options.direction()
            );
            particle.setLifetime(options.life());
            particle.setParticleSpeed(xa, ya, za);

            return particle;
        }
    }
}
