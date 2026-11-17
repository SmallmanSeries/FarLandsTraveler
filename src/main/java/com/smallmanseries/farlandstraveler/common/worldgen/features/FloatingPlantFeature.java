package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

/**
 * "浮空植物"地物
 * <p>
 * 一个经过改进的 {@link net.minecraft.world.level.levelgen.feature.SimpleBlockFeature} 地物。
 * 它<b>不检查</b> {@code canSurvive}。这意味着它可以把植物、火把之类需要有支撑的方块直接放到半空中，无视支撑。
 * <p>
 * 此外，该地物放置方块时使用 {@code updateType = 2}（只同步客户端、不触发方块更新）的方式，因此浮空的植物不会在放置后立刻销毁。
 */
public class FloatingPlantFeature extends Feature<SimpleBlockConfiguration> {
    public FloatingPlantFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration config = context.config();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        BlockState state = config.toPlace().getOptionalState(level, context.random(), origin);
        if (state == null) {
            return false;
        }

        // 只往空气里放，避免把地形或其它地物覆盖掉
        if (!level.isStateAtPosition(origin, BlockBehaviour.BlockStateBase::isAir)) {
            return false;
        }

        // 双格植物需要上方也是空气
        if (state.getBlock() instanceof DoublePlantBlock) {
            if (!level.isStateAtPosition(origin.above(), BlockBehaviour.BlockStateBase::isAir)) {
                return false;
            }
            DoublePlantBlock.placeAt(level, state, origin, 2);
            return true;
        }

        level.setBlock(origin, state, 2);

        if (config.scheduleTick()) {
            level.scheduleTick(origin, level.getBlockState(origin).getBlock(), 1);
        }

        return true;
    }
}
