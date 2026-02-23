package com.smallmanseries.farlandstraveler;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // <配置文件正文>
    public static final ModConfigSpec.BooleanValue REMOVE_WORLD_BORDER = BUILDER
            .comment("Remove the world border")
            .define("remove_world_border", true);

    public static final ModConfigSpec.BooleanValue REMOVE_COORDINATE_LIMITS = BUILDER
            .comment("Remove all limits related to coordinates, including air walls and teleport command limitations")
            .comment("[WARNING] Going outside the limits may lead to various errors!!!")
            .define("remove_coordinate_limits", true);

    public static final ModConfigSpec.BooleanValue ENABLE_FAR_LANDS = BUILDER
            .comment("Enable noises to overflow and create the Far Lands.")
            .comment("The side effect is the appearance of the Grand Pillars in the normal world.")
            .define("enable_far_lands", true);

    public static final ModConfigSpec.IntValue FAR_LANDS_DISTANCE = BUILDER
            .comment("The distance between the generated location of the Far Lands and the origin of the world")
            .comment("Actually, it's the location of the first switch to the world generator")
            .defineInRange("far_lands_distance", 12550824, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue REPEATING_FAR_LANDS_DISTANCE = BUILDER
            .comment("The distance between the generated location of the Repeating Far Lands and the origin of the world")
            .comment("Actually, it's the location of the second switch to the world generator")
            .defineInRange("repeating_far_lands_distance", 12560824, 0, Integer.MAX_VALUE);
    // </配置文件正文>

    static final ModConfigSpec SPEC = BUILDER.build();

}
