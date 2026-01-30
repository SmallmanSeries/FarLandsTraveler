package com.smallmanseries.farlandstraveler.server.worldgen.farlands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;

public record FarLands(
        LevelStem dimension,
        boolean isCorner,
        int dist,
        ChunkGenerator generator
) {
    // 编解码器
    /* json文件格式：
        {
            "dimension" : 生成维度。
            "iscorner" : 填true或false，默认为false。决定该边境之地是边境之墙还是边境之角。其中边境之角会把边境之墙覆盖掉。
            "dist" : 边境之地开始生成的坐标。如果iscorner为false，则当x或z坐标有一个绝对值大于该值，就生成该边境之地。如果iscorner为true，则当x和z坐标的绝对值都大于该值时生成该边境之地。
            "generator" : 生成该边境之地使用的区块生成器（ChunkGenerator）
        }
     */
    public static final Codec<FarLands> CODEC = RecordCodecBuilder.create(
        farLandsInstance -> farLandsInstance.group(
                LevelStem.CODEC.fieldOf("dimension").forGetter(FarLands::dimension),
                Codec.BOOL.optionalFieldOf("iscorner", false).forGetter(FarLands::isCorner),
                Codec.INT.fieldOf("dist").forGetter(FarLands::dist),
                ChunkGenerator.CODEC.fieldOf("generator").forGetter(FarLands::generator)
        ).apply(farLandsInstance, FarLands::new)
    );
}
