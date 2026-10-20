package com.smallmanseries.farlandstraveler.data.resources.model;

import com.smallmanseries.farlandstraveler.FarLandsTraveler;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

public class FLTModelTemplates {

    // 远古方块的模型模板。根据Wiki，远古方块（草方块、石头）的底面是镜像的。
    public static final ModelTemplate CUBE_ANCIENT = ModelTemplates.create(Identifier.fromNamespaceAndPath(FarLandsTraveler.MODID, "cube_ancient").toString(), TextureSlot.ALL);
}
