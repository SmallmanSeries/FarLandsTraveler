package com.smallmanseries.farlandstraveler;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // 【主要玩法设置】
    public static final ModConfigSpec.DoubleValue PE_TELEPORT_THRESHOLD = BUILDER
            .comment("When a player has the \"Precision Loss\" Lv.255 effect and their horizontal speed's square exceeds this threshold, a teleportation will be triggered")
            .defineInRange("pe_teleport_threshold", 100, 0, Double.MAX_VALUE);

    // 【世界边界设置】
    public static final ModConfigSpec.BooleanValue REMOVE_WORLD_BORDER = BUILDER
            .comment("Remove the world border")
            .define("remove_world_border", true);

    public static final ModConfigSpec.BooleanValue REMOVE_WORLD_BOUNDARY = BUILDER
            .comment("Remove the air walls at the world boundary (x,z 30000000)")
            .comment("[WARNING] Going outside the world boundary may lead to various errors!!!")
            .define("remove_world_boundary", true);

    public static final ModConfigSpec.BooleanValue REMOVE_COORDINATE_LIMITS = BUILDER
            .comment("Remove all limits related to coordinates, for example, teleport command limitations")
            .define("remove_coordinate_limits", true);

    // 【边境之地设置】
    public static final ModConfigSpec.IntValue FAR_LANDS_DISTANCE = BUILDER
            .comment("The distance between the generated location of the Far Lands and the origin of the world")
            .comment("This setting is used for the determination of various mechanisms and does not affect terrain generation,")
            .defineInRange("far_lands_distance", 12550824, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue STRIPE_LANDS_DISTANCE = BUILDER
            .comment("The distance between the rendered location of the Stripe Lands and the origin of the world")
            .comment("Set to -1 to disable the simulation of Stripe Lands")
            .defineInRange("stripe_lands_distance", 16777216, -1, 33554432);

    public static final ModConfigSpec.BooleanValue ENABLE_SKY_GRID = BUILDER
            .comment("Enable the sky grid to generate in the Fringe Lands.")
            .define("enable_sky_grid", false);

    public static final ModConfigSpec.BooleanValue FORCE_SKY_GRID = BUILDER
            .comment("Force the sky grid to generate even if the density value is clamped before interpolation.")
            .define("force_sky_grid", false);

    // 【结构生成设置】
    public static final ModConfigSpec.BooleanValue GENERATE_OOTS_LABORATORY = BUILDER
            .comment("Enable the generation of The Order Of The Stone Laboratory")
            .define("generate_oots_laboratory", true);

    // 【假区块设置】
    public static final ModConfigSpec.BooleanValue FC_DISABLE_BLOCK_COLLISION = BUILDER
            .comment("Disable block collisions in fake chunks")
            .comment("Unless the entity is immune to fake chunks")
            .define("fc_disable_block_collision", true);

    public static final ModConfigSpec.BooleanValue FC_DISABLE_BLOCK_EFFECT = BUILDER
            .comment("Disable the effects of blocks in fake chunks on entities (such as fire damage, cactus damage, etc.)")
            .comment("Unless the entity is immune to fake chunks")
            .define("fc_disable_block_effect", true);

    public static final ModConfigSpec.BooleanValue FC_DISABLE_FLUID_COLLISION = BUILDER
            .comment("Disable fluid collisions in fake chunks")
            .comment("Unless the entity is immune to fake chunks")
            .define("fc_disable_fluid_collision", true);

    public static final ModConfigSpec.BooleanValue FC_DISABLE_FLUID_FLOWING_BEHAVIOR = BUILDER
            .comment("Prevent fluid from flowing through the edge of fake chunks")
            .define("fc_disable_fluid_flowing_behavior", true);

    public static final ModConfigSpec.BooleanValue FC_DISABLE_BLOCK_INTERACTION = BUILDER
            .comment("Cancel the interaction between players (or other entities such as Endermen) and blocks within fake chunks")
            .comment("Unless they have the proper tool")
            .define("fc_disable_block_interaction", true);

    public static final ModConfigSpec.BooleanValue FC_DISABLE_EXPLOSION_EFFECT = BUILDER
            .comment("Make the blocks within fake chunks immune to explosions and unable to block explosive rays")
            .define("fc_disable_explosion_effect", true);

    public static final ModConfigSpec.BooleanValue FC_DISABLE_LADDER_BEHAVIOR = BUILDER
            .comment("Make ladder-like blocks in fake chunks unable to be climbed")
            .define("fc_disable_ladder_behavior", true);

    public static final ModConfigSpec.BooleanValue FC_DISABLE_PISTON_BEHAVIOR = BUILDER
            .comment("Prevent the piston in fake chunks from functioning properly")
            .define("fc_disable_piston_behavior", true);

    // 【实验性设置】
    public static final ModConfigSpec.BooleanValue ENABLE_FAR_LANDS = BUILDER
            .comment("Enable noises to overflow and create the Far Lands")
            .comment("Now deprecated.")
            .define("enable_far_lands", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
