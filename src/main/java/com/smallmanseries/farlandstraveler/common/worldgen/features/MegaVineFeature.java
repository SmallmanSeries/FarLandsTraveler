package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.MegaVineConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MegaVineFeature extends Feature<MegaVineConfiguration> {
    public MegaVineFeature(Codec<MegaVineConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MegaVineConfiguration> context) {
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        MegaVineConfiguration config = context.config();

        // 检查生成高度，如果高于254就生成失败
        if (origin.getY() > 254) {
            return false;
        }

        // 偏移原点至边境之地边缘
        int farLandsDist = Config.FAR_LANDS_DISTANCE.get();
        int clampedX = Math.clamp(origin.getX(), -farLandsDist, farLandsDist - 4);
        int clampedZ = Math.clamp(origin.getZ(), -farLandsDist, farLandsDist - 4);
        if (clampedX != origin.getX() || clampedZ != origin.getZ()) {
            origin = new BlockPos(clampedX, origin.getY(), clampedZ);
        }

        // 检查生成位置是否未被占用
        if (!level.isEmptyBlock(origin)) {
            return false;
        }

        // 确定方向
        for (Direction direction : Direction.values()) {
            if (direction.getAxis() != Direction.Axis.Y && MultifaceBlock.canAttachTo(level, origin, direction)) {
                origin = origin.relative(direction);
                BlockPos origin2 = origin;
                Direction.Axis axis = direction.getClockWise().getAxis();

                // 向左向右各检查16个方块的距离，选中找到的第一个可被替换的方块，作为两个原点。
                for (int i = -1; i > -16; i--) {
                    origin = origin.relative(axis, i);
                    if (level.getBlockState(origin).canBeReplaced()) {
                        break;
                    }
                }
                for (int i = 1; i < 16; i++) {
                    origin2 = origin2.relative(axis, i);
                    if (level.getBlockState(origin2).canBeReplaced()) {
                        break;
                    }
                }

                // 如果两个原点挨得太近（5格以内），则生成失败。
                if (origin.closerThan(origin2, 5)) {
                    return false;
                }

                // 将两个原点向下偏移，以使其尽可能贴近地面
                while (origin.getY() >= level.getSeaLevel() && !config.canPlaceOn().test(level, origin.below())) {
                    origin = origin.below();
                }
                while (origin2.getY() >= level.getSeaLevel() && !config.canPlaceOn().test(level, origin2.below())) {
                    origin2 = origin2.below();
                }

                // 如果任意一个原点低于海平面，则生成失败
                if (origin.getY() <= level.getSeaLevel() || origin2.getY() <= level.getSeaLevel()) {
                    return false;
                }

                // 如果两个原点的高度差太大（超过5格），则生成失败
                if (Math.abs(origin.getY() - origin2.getY()) >= 5) {
                    return false;
                }

                // 在两个原点处放置方块
                this.safeSetBlock(level, origin, config.vineBlock(), BlockBehaviour.BlockStateBase::canBeReplaced);
                this.safeSetBlock(level, origin2, config.vineBlock(), BlockBehaviour.BlockStateBase::canBeReplaced);

                // 生成一条连接两个原点的悬链线（抛物线近似）
                origin = origin.relative(direction.getOpposite());
                origin2 = origin2.relative(direction.getOpposite());
                int dist = Math.abs(axis == Direction.Axis.X ? origin.getX() - origin2.getX() : origin.getZ() - origin2.getZ());
                double sag = Math.max(2.0, dist / 4.0);
                for (int i = 0; i <= dist; i++) {
                    double t = (double) i / dist;
                    double yLinear = origin.getY() + (origin2.getY() - origin.getY()) * t;
                    double sagFactor = sag * (4.0 * t * (1.0 - t)); // 4t(1-t) 在 t=0.5 达到1
                    double y = yLinear - sagFactor;

                    // 放置藤蔓方块
                    this.safeSetBlock(level, origin.relative(axis, i).relative(Direction.Axis.Y, (int) Math.round(y - origin.getY())), config.vineBlock(), BlockBehaviour.BlockStateBase::canBeReplaced);

                    // 放置悬垂藤蔓
                    if (random.nextInt(2) == 1) {
                        for (int j = random.nextInt(3); j > 0; j--) {
                            this.safeSetBlock(level, origin.relative(axis, i).relative(Direction.Axis.Y, (int) Math.round(y - origin.getY()) - j), config.vineBlock(), BlockBehaviour.BlockStateBase::canBeReplaced);
                        }
                    }
                }

                return true;
            }
        }
        return false;
    }
}
