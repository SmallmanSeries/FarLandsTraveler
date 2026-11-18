package com.smallmanseries.farlandstraveler.common.entity;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.worldgen.FLTBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

/**
 * Herobrine 实体生成器（接入原版CustomSpawner）
 * <p>
 * 每隔约半个游戏日，在每一名玩家的16 格外、48 格内的无叶树林地面，生成一名 Herobrine。生成前会先把该位置 48 格内的其他 Herobrine 清除，保证同一区域只会看到一个。
 * <p>
 * 其他消失情形由 {@link HerobrineEntity} 自己负责，这里只管生成。
 */
public final class HerobrineSpawner implements CustomSpawner {
    /**
     * 生成间隔：半个游戏日
     */
    public static final int SPAWN_INTERVAL_TICKS = 4800;
    /**
     * 生成点与玩家的最近距离
     */
    public static final double MIN_SPAWN_DISTANCE = 16.0;
    /**
     * 生成点与玩家的最远距离
     */
    public static final double MAX_SPAWN_DISTANCE = 48.0;
    /**
     * 每次生成尝试次数
     */
    private static final int MAX_ATTEMPTS = 16;
    private int tickDelay;

    public HerobrineSpawner() {
        this.tickDelay = SPAWN_INTERVAL_TICKS;
    }

    @Override
    public void tick(ServerLevel serverLevel, boolean b) {
        if (--this.tickDelay <= 0) {
            this.tickDelay = SPAWN_INTERVAL_TICKS;
            MinecraftServer server = serverLevel.getServer();
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                trySpawnNear(player);
            }
        }
    }

    private void trySpawnNear(ServerPlayer player) {
        ServerLevel level = player.level();
        if (!level.dimension().equals(Level.OVERWORLD)) {
            return;
        }

        RandomSource random = level.getRandom();
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            // 在玩家周围的一个圆环上取点
            double angle = random.nextDouble() * Math.PI * 2.0;
            double distance = MIN_SPAWN_DISTANCE
                    + random.nextDouble() * (MAX_SPAWN_DISTANCE - MIN_SPAWN_DISTANCE);
            int x = Mth.floor(player.getX() + Math.cos(angle) * distance);
            int z = Mth.floor(player.getZ() + Math.sin(angle) * distance);
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos pos = new BlockPos(x, y, z);

            // 只在无叶树林生成
            if (!level.getBiome(pos).is(FLTBiomes.LEAFLESS_FOREST)) {
                continue;
            }

            // 必须生成在可支撑的方块上，且占据的两格为空气
            if (!level.isEmptyBlock(pos) || !level.isEmptyBlock(pos.above())) {
                continue;
            }
            if (!level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)) {
                continue;
            }

            spawn(level, pos);
            return;
        }
    }

    private void spawn(ServerLevel level, BlockPos pos) {

        // 调试代码
        FarLandsTraveler.LOGGER.info("Herobrine已生成于：" + pos);

        // 生成时 48 格范围内已经有另一个 Herobrine，就让它消失
        AABB area = new AABB(pos).inflate(MAX_SPAWN_DISTANCE);
        for (HerobrineEntity other : level.getEntitiesOfClass(HerobrineEntity.class, area)) {
            other.vanish();
        }

        HerobrineEntity herobrine = FLTEntityTypes.HEROBRINE.get().create(level, EntitySpawnReason.NATURAL);
        if (herobrine == null) {
            return;
        }
        herobrine.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(herobrine);
    }
}
