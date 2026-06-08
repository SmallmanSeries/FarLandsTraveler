package com.smallmanseries.farlandstraveler.common;

import com.google.common.collect.Lists;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.List;

@EventBusSubscriber(modid = FarLandsTraveler.MODID)
public class EventHandler {
    // 注册边境之地数据驱动文件
    @SubscribeEvent
    public static void registerData(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(DataRegister.FAR_LANDS, FarLands.CODEC);
        event.dataPackRegistry(DataRegister.BIOME_SOURCE, BiomeSource.CODEC);
    }

    // 当活塞移动的方块跨过假区块边界时，取消本次移动。
    // Todo 该机制尚不完善，未来进行修改
    @SubscribeEvent
    public static void cancelPistonMove(PistonEvent.Pre event) {
        if(event.getLevel().isClientSide()){
            return;
        }
        PistonStructureResolver resolver = event.getStructureHelper();
        if (resolver != null) {
            resolver.resolve();
            List<BlockPos> blockList = Lists.newArrayList();
            blockList.addAll(resolver.getToPush());
            blockList.addAll(resolver.getToDestroy());
            if (FakeChunk.isInFakeChunk(event.getLevel(), event.getPos())) {
                for (BlockPos blockpos : blockList) {
                    if (!FakeChunk.isInFakeChunk(event.getLevel(), blockpos)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            } else {
                for (BlockPos blockpos : blockList) {
                    if (FakeChunk.isInFakeChunk(event.getLevel(), blockpos)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }
}
