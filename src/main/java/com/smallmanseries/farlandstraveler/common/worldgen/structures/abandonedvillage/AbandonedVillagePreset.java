package com.smallmanseries.farlandstraveler.common.worldgen.structures.abandonedvillage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 用于存储村庄建筑方块替换列表（即预设）的数据类
 *
 * @param blockCobble        除教堂、铁匠铺外的圆石（随生物群系变化，例如在冰原沙漠是砂岩）
 * @param blockCobble2       教堂、铁匠铺的圆石（不随生物群系变化）
 * @param blockStairsCobble  圆石楼梯，一般用于房子前面的楼梯
 * @param blockStairsWood    木楼梯，一般用于屋顶和房屋内部的椅子
 * @param blockPlanks        木板
 * @param blockLog           原木
 * @param blockFence         栅栏
 * @param blockDoor          门
 * @param blockPressurePlate 木制压力板，用于部分房屋中的桌子。虽然压力板生成的很少，但它是橡木质的，如果边境之地中没有橡木的话也要更换
 * @param blockLadder        梯子。虽然原版只有一种梯子，但那很明显是橡木质的梯子。如果边境之地中没有橡木的话，这个梯子也要更换
 * @param blockGravel        沙砾，用于道路
 */
public record AbandonedVillagePreset(
        BlockState blockCobble,
        BlockState blockCobble2,
        BlockState blockStairsCobble,
        BlockState blockStairsWood,
        BlockState blockPlanks,
        BlockState blockLog,
        BlockState blockFence,
        BlockState blockDoor,
        BlockState blockPressurePlate,
        BlockState blockLadder,
        BlockState blockGravel
) {
    public static final Codec<AbandonedVillagePreset> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockState.CODEC.optionalFieldOf("cobblestone", Blocks.COBBLESTONE.defaultBlockState()).forGetter(AbandonedVillagePreset::blockCobble),
                    BlockState.CODEC.optionalFieldOf("cobblestone_2", Blocks.COBBLESTONE.defaultBlockState()).forGetter(AbandonedVillagePreset::blockCobble2),
                    BlockState.CODEC.optionalFieldOf("cobblestone_stairs", Blocks.COBBLESTONE_STAIRS.defaultBlockState()).forGetter(AbandonedVillagePreset::blockStairsCobble),
                    BlockState.CODEC.optionalFieldOf("wood_stairs", Blocks.OAK_STAIRS.defaultBlockState()).forGetter(AbandonedVillagePreset::blockStairsWood),
                    BlockState.CODEC.optionalFieldOf("wood_planks", Blocks.OAK_PLANKS.defaultBlockState()).forGetter(AbandonedVillagePreset::blockPlanks),
                    BlockState.CODEC.optionalFieldOf("log", Blocks.OAK_LOG.defaultBlockState()).forGetter(AbandonedVillagePreset::blockLog),
                    BlockState.CODEC.optionalFieldOf("fence", Blocks.OAK_FENCE.defaultBlockState()).forGetter(AbandonedVillagePreset::blockFence),
                    BlockState.CODEC.optionalFieldOf("door", Blocks.OAK_DOOR.defaultBlockState()).forGetter(AbandonedVillagePreset::blockDoor),
                    BlockState.CODEC.optionalFieldOf("pressure_plate", Blocks.OAK_PRESSURE_PLATE.defaultBlockState()).forGetter(AbandonedVillagePreset::blockPressurePlate),
                    BlockState.CODEC.optionalFieldOf("ladder", Blocks.LADDER.defaultBlockState()).forGetter(AbandonedVillagePreset::blockLadder),
                    BlockState.CODEC.optionalFieldOf("gravel", Blocks.GRAVEL.defaultBlockState()).forGetter(AbandonedVillagePreset::blockGravel)
            ).apply(instance, AbandonedVillagePreset::new)
    );

    public static AbandonedVillagePreset createDefault() {
        return new AbandonedVillagePreset(
                Blocks.COBBLESTONE.defaultBlockState(),
                Blocks.COBBLESTONE.defaultBlockState(),
                Blocks.COBBLESTONE_STAIRS.defaultBlockState(),
                Blocks.OAK_STAIRS.defaultBlockState(),
                Blocks.OAK_PLANKS.defaultBlockState(),
                Blocks.OAK_LOG.defaultBlockState(),
                Blocks.OAK_FENCE.defaultBlockState(),
                Blocks.OAK_DOOR.defaultBlockState(),
                Blocks.OAK_PRESSURE_PLATE.defaultBlockState(),
                Blocks.LADDER.defaultBlockState(),
                Blocks.GRAVEL.defaultBlockState()
        );
    }
}