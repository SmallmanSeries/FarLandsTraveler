package com.smallmanseries.farlandstraveler.common.worldgen.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.stream.Stream;

/**
 * “旋转与镜像”地物的配置
 * <p>
 * 把一个已有地物在放置时写入的每一个方块，先做空间变换、再写进世界，从而得到
 * “倒过来的树”“转了 90 度的结构”这类变体。原地物本身完全不需要改动，兼容性最好，
 * 后续的生物群系（以及附属模组、数据包）都可以直接复用这个地物。
 * <p>
 * 变换以放置原点为基准点，顺序为：先按坐标轴镜像，再绕 Y 轴顺时针旋转。
 *
 * @param feature  被变换的地物
 * @param mirrorX  是否沿 X 轴镜像（X 坐标取反）
 * @param mirrorY  是否沿 Y 轴镜像（Y 坐标取反，用于生成“倒树”）
 * @param mirrorZ  是否沿 Z 轴镜像（Z 坐标取反）
 * @param rotation 绕 Y 轴的顺时针旋转角度，只支持 0 / 90 / 180 / 270
 */
public record TransformedConfiguration(
        Holder<ConfiguredFeature<?, ?>> feature,
        boolean mirrorX,
        boolean mirrorY,
        boolean mirrorZ,
        int rotation
) implements FeatureConfiguration {
    public static final Codec<TransformedConfiguration> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                    ConfiguredFeature.CODEC.fieldOf("feature").forGetter(TransformedConfiguration::feature),
                    Codec.BOOL.optionalFieldOf("mirror_x", false).forGetter(TransformedConfiguration::mirrorX),
                    Codec.BOOL.optionalFieldOf("mirror_y", false).forGetter(TransformedConfiguration::mirrorY),
                    Codec.BOOL.optionalFieldOf("mirror_z", false).forGetter(TransformedConfiguration::mirrorZ),
                    Codec.intRange(0, 270).optionalFieldOf("rotation", 0).forGetter(TransformedConfiguration::rotation)
            ).apply(inst, TransformedConfiguration::new)
    );

    /**
     * 把一个位于“原地物坐标系”中的方块坐标变换到“世界坐标系”
     *
     * @param origin 放置原点，变换的基准点
     * @param pos    原地物将要写入的坐标
     * @return 变换后真正写入世界的坐标
     */
    public BlockPos transform(BlockPos origin, BlockPos pos) {
        int dx = pos.getX() - origin.getX();
        int dy = pos.getY() - origin.getY();
        int dz = pos.getZ() - origin.getZ();

        // 第一步：按坐标轴镜像
        if (this.mirrorX) {
            dx = -dx;
        }
        if (this.mirrorY) {
            dy = -dy;
        }
        if (this.mirrorZ) {
            dz = -dz;
        }

        // 第二步：绕 Y 轴顺时针旋转
        switch (this.normalizedRotation()) {
            case 90 -> {
                int tmp = dx;
                dx = -dz;
                dz = tmp;
            }
            case 180 -> {
                dx = -dx;
                dz = -dz;
            }
            case 270 -> {
                int tmp = dx;
                dx = dz;
                dz = -tmp;
            }
            default -> {
            }
        }

        return new BlockPos(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
    }

    // 把任意角度取整到 0 / 90 / 180 / 270
    private int normalizedRotation() {
        return Math.floorMod(this.rotation, 360) / 90 * 90;
    }

    @Override
    public Stream<Holder<ConfiguredFeature<?, ?>>> getSubFeatures() {
        return Stream.of(this.feature);
    }
}
