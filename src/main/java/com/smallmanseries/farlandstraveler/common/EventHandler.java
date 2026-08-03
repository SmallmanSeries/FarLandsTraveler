package com.smallmanseries.farlandstraveler.common;

import com.google.common.collect.Lists;
import com.smallmanseries.farlandstraveler.Config;
import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import com.smallmanseries.farlandstraveler.common.distance_phenomenon.FakeChunk;
import com.smallmanseries.farlandstraveler.common.effect.FLTMobEffects;
import com.smallmanseries.farlandstraveler.common.entity.FLTEntityTypes;
import com.smallmanseries.farlandstraveler.common.entity.PrimitiveEndermanEntity;
import com.smallmanseries.farlandstraveler.common.item.FLTItems;
import com.smallmanseries.farlandstraveler.common.worldgen.farlands.FarLands;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = FarLandsTraveler.MODID)
public class EventHandler {
    // 注册实体属性
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FLTEntityTypes.PRIMITIVE_ENDERMAN.get(), PrimitiveEndermanEntity.createAttributes().build());
    }

    // 注册边境之地数据驱动文件
    @SubscribeEvent
    public static void registerData(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(DataRegister.FAR_LANDS, FarLands.CODEC);
        event.dataPackRegistry(DataRegister.BIOME_SOURCE, BiomeSource.CODEC);
        // Todo 26.3及以后的版本，原版自带材料规则功能，本模组的此功能可以删除
        event.dataPackRegistry(DataRegister.MATERIAL_RULE, SurfaceRules.RuleSource.CODEC);
    }

    // 当活塞移动的方块跨过假区块边界时，取消本次移动。
    // Todo 该机制尚不完善，未来进行修改
    @SubscribeEvent
    public static void cancelPistonMove(PistonEvent.Pre event) {
        if (!Config.FC_DISABLE_PISTON_BEHAVIOR.getAsBoolean() || event.getLevel().isClientSide()) {
            return;
        }
        PistonStructureResolver resolver = event.getStructureHelper();
        if (resolver != null) {
            resolver.resolve();
            List<BlockPos> blockList = Lists.newArrayList();
            blockList.addAll(resolver.getToPush());
            blockList.addAll(resolver.getToDestroy());
            LevelAccessor level = event.getLevel();
            if (FakeChunk.isInFakeChunk(level, event.getPos())) {
                for (BlockPos blockpos : blockList) {
                    if (!FakeChunk.isInFakeChunk(level, blockpos)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            } else {
                for (BlockPos blockpos : blockList) {
                    if (FakeChunk.isInFakeChunk(level, blockpos)) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }

    // 检测爆炸，为一些特殊的爆炸影响到的实体添加效果。
    @SubscribeEvent
    public static void addEffectsWhenExplode(ExplosionEvent.Detonate event) {
        Entity source = event.getExplosion().getDirectSourceEntity();
        if (source == null) {
            return;
        }

        // 爆炸源是【原始末影核心】物品实体，为所有影响到的实体添加效果。如果爆炸源和实体都在正常世界，添加精度丢失效果。如果爆炸源和实体都在边境之地，添加穿墙效果。否则什么也不做。
        if (source instanceof ItemEntity itemEntity && itemEntity.getItem().is(FLTItems.PRIMITIVE_ENDER_CORE)) {

            // 判断爆炸源是否在边境之地
            boolean sourceFar = FarLands.isInFarLands(source.getX(), source.getY(), source.getZ(), 3);

            for (Entity entity : event.getAffectedEntities()) {
                if (entity instanceof LivingEntity living) {

                    // 判断受影响的实体是否在边境之地
                    if (FarLands.isInFarLands(living.getX(), living.getY(), living.getZ(), 3)) {
                        if (sourceFar) {
                            // Todo 穿墙效果
                        }
                    } else if (!sourceFar) {

                        // 如果玩家身上没有精度丢失效果，就为玩家添加时长50秒的255级的精度丢失效果；否则将玩家的精度丢失效果等级增加到255级，时长增加50秒。
                        if (living.hasEffect(FLTMobEffects.PRECISION_LOSS)) {
                            living.addEffect(new MobEffectInstance(FLTMobEffects.PRECISION_LOSS, Objects.requireNonNull(living.getEffect(FLTMobEffects.PRECISION_LOSS)).getDuration() + 1000, 255, false, false, true));
                        } else {
                            living.addEffect(new MobEffectInstance(FLTMobEffects.PRECISION_LOSS, 1000, 255, false, false, true));
                        }
                    }
                }
            }
        }

    }
}
