package com.smallmanseries.farlandstraveler.common.worldgen.features.placements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

/**
 * “每一列”放置修饰器
 * <p>
 * 把当前区块的 256 列全部取出来，各自按高度图算出地表位置。
 * 原版没有这类修饰器：最接近的 {@code count} + {@code in_square} 是随机的，256 次抽取只有约 63% 的列被覆盖到，
 * 想“每一格都长东西”就只能抽上千次碰运气。这里直接枚举，既准确又比反复抽取便宜。
 * <p>
 * <b>只能放在放置链的第一个位置</b>：按传入位置的区块坐标取列，
 * 若前面有 {@code count} 之类的重复型修饰器，同一批列会被重复产出。
 */
public class EveryColumnPlacement extends PlacementModifier {

    public static final MapCodec<EveryColumnPlacement> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    Heightmap.Types.CODEC.fieldOf("heightmap").forGetter(EveryColumnPlacement::heightmap)
            ).apply(inst, EveryColumnPlacement::new)
    );

    private final Heightmap.Types heightmap;

    public EveryColumnPlacement(Heightmap.Types heightmap) {
        this.heightmap = heightmap;
    }

    public static EveryColumnPlacement of(Heightmap.Types heightmap) {
        return new EveryColumnPlacement(heightmap);
    }

    public Heightmap.Types heightmap() {
        return this.heightmap;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        int minX = Math.floorDiv(origin.getX(), 16) * 16;
        int minZ = Math.floorDiv(origin.getZ(), 16) * 16;
        int minY = context.getMinY();
        Stream.Builder<BlockPos> positions = Stream.builder();

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                int x = minX + dx;
                int z = minZ + dz;
                int height = context.getHeight(this.heightmap, x, z);
                if (height > minY) {
                    positions.add(new BlockPos(x, height, z));
                }
            }
        }
        return positions.build();
    }

    @Override
    public PlacementModifierType<?> type() {
        return FLTPlacementModifiers.EVERY_COLUMN.get();
    }
}
