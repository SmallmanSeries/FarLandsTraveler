package com.smallmanseries.farlandstraveler.client.render.entity;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class FLTLayers {
    public static final ModelLayerLocation TEST_ENTITY = register("test_entity", "main");

    private static ModelLayerLocation register(String name, String layer) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, name), layer);
    }
}
