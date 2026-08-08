package com.smallmanseries.farlandstraveler.common.entity;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLTEntityTypes {

    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(FarLandsTraveler.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<PrimitiveEndermanEntity>> PRIMITIVE_ENDERMAN = ENTITY_TYPES.register("primitive_enderman", (id) -> EntityType.Builder.of(PrimitiveEndermanEntity::new, MobCategory.MONSTER).sized(0.6F, 2.9F).eyeHeight(2.55F).passengerAttachments(2.80625F).clientTrackingRange(8).notInPeaceful().build(ResourceKey.create(Registries.ENTITY_TYPE, id)));
    public static final DeferredHolder<EntityType<?>, EntityType<TestEntity>> TEST_ENTITY = ENTITY_TYPES.register("test_entity", (id) -> EntityType.Builder.of(TestEntity::new, MobCategory.MISC).sized(0.6F, 1.95F).eyeHeight(1.74F).passengerAttachments(2.0125F).ridingOffset(-0.7F).clientTrackingRange(8).build(ResourceKey.create(Registries.ENTITY_TYPE, id)));

}
