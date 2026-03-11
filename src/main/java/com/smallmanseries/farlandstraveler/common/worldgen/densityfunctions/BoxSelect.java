package com.smallmanseries.farlandstraveler.common.worldgen.densityfunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
 * 框选一个区域
 * <p>
 *     {
 *     <p>
 *         "type": "farlandstraveler:box_select",
 *         <p>
 *         "invert": 是否反转选区,
 *         <p>
 *         "origin_x": 选区起始方块的x坐标,
 *         <p>
 *         "origin_y": 选区起始方块的y坐标,
 *         <p>
 *         "origin_z": 选区起始方块的z坐标,
 *         <p>
 *         "extend_x": 选区长度,
 *         <p>
 *         "extend_y": 选区高度,
 *         <p>
 *         "extend_z": 选区宽度
 *         <p>
 *     }
 *     <p>
 * 当坐标在选区内时，返回1，否则返回0
 */

public record BoxSelect(boolean invert, int originX, int originY, int originZ, int extendX, int extendY, int extendZ) implements DensityFunction.SimpleFunction {
    private static final Codec<Integer> INPUT_RANGE = Codec.intRange(0, Integer.MAX_VALUE);
    public static final MapCodec<BoxSelect> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.BOOL.fieldOf("invert").forGetter(BoxSelect::invert),
                            Codec.INT.fieldOf("origin_x").forGetter(BoxSelect::originX),
                            Codec.INT.fieldOf("origin_y").forGetter(BoxSelect::originY),
                            Codec.INT.fieldOf("origin_z").forGetter(BoxSelect::originZ),
                            INPUT_RANGE.fieldOf("extend_x").forGetter(BoxSelect::extendX),
                            INPUT_RANGE.fieldOf("extend_y").forGetter(BoxSelect::extendY),
                            INPUT_RANGE.fieldOf("extend_z").forGetter(BoxSelect::extendZ)
                    )
                    .apply(instance, BoxSelect::new)
    );
    public static final KeyDispatchDataCodec<BoxSelect> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    @Override
    public double compute(FunctionContext functionContext) {
        int endX = originX + extendX;
        int endY = originY + extendY;
        int endZ = originZ + extendZ;
        if (functionContext.blockX() >= originX && functionContext.blockX() < endX
         && functionContext.blockY() >= originY && functionContext.blockY() < endY
         && functionContext.blockZ() >= originZ && functionContext.blockZ() < endZ){
            return invert ? 0 : 1;
        }
        return invert ? 1 : 0;
    }

    @Override
    public double minValue() {
        return 0;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
