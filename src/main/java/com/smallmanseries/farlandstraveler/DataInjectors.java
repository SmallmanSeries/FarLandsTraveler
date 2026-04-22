package com.smallmanseries.farlandstraveler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.ramixin.mixson.inline.Mixson;

import java.util.Objects;

/**
 * 基于Mixson的数据文件注入器
 */
public class DataInjectors {
    /**
     * 用于包装密度函数
     * @param initial 原始密度函数
     * @param dist 框选的边界，可以理解为边境之地距离
     * @param path 边境之地对应的密度函数的文件是<命名空间>/density_function/far_lands中的哪个文件
     * @return 一个JSON对象，即包装好的密度函数定义
     */
    private static JsonObject getDensity(JsonElement initial, int dist, String path) {
        JsonObject boxSelect = new JsonObject();
        boxSelect.addProperty("type", "farlandstraveler:box_select");
        boxSelect.addProperty("invert", true);
        boxSelect.addProperty("origin_x", -dist);
        boxSelect.addProperty("origin_y", -25101648);
        boxSelect.addProperty("origin_z", -dist);
        boxSelect.addProperty("extend_x", 2 * dist - 3);
        boxSelect.addProperty("extend_y", 50203297);
        boxSelect.addProperty("extend_z", 2 * dist - 3);
        JsonObject modified = new JsonObject();
        modified.addProperty("type", "minecraft:range_choice");
        modified.add("input", boxSelect);
        modified.addProperty("min_inclusive", 0);
        modified.addProperty("max_exclusive", 1);
        modified.add("when_in_range", initial);
        modified.addProperty("when_out_of_range", "farlandstraveler:far_lands/" + path);
        return modified;
    }

    /**
     * 用于包装噪声设置定义文件中的noise_router中的各个密度函数，使得其能够在指定坐标切换密度函数
     * @param path 噪声设置文件的路径
     * @param name MixsonEvent的名称（随便取）
     */
    public static void noiseRouterInjector(String path, String name) {
        Mixson.registerEvent(0, ResourceLocation.withDefaultNamespace(path).toString(), name, (context) -> {
            // 获取noise_router
            JsonObject noiseRouterOverworld = context.getFile().getAsJsonObject().getAsJsonObject("noise_router");
            int dist = Config.FAR_LANDS_DISTANCE.getAsInt();
            // 基于base_3d_noise的溢出切换final_density
            JsonElement finalDensity = noiseRouterOverworld.get("final_density");
            JsonObject overflowCheck = new JsonObject();
            overflowCheck.addProperty("type", "minecraft:range_choice");
            overflowCheck.addProperty("input", "farlandstraveler:far_lands/far_lands_generation_check");
            overflowCheck.addProperty("min_inclusive", -100);
            overflowCheck.addProperty("max_exclusive", 100);
            overflowCheck.add("when_in_range", finalDensity);
            overflowCheck.addProperty("when_out_of_range", "farlandstraveler:far_lands/final_density");
            JsonObject boxSelect = new JsonObject();
            boxSelect.addProperty("type", "farlandstraveler:box_select");
            boxSelect.addProperty("invert", true);
            boxSelect.addProperty("origin_x", -dist);
            boxSelect.addProperty("origin_y", -25101648);
            boxSelect.addProperty("origin_z", -dist);
            boxSelect.addProperty("extend_x", 2 * dist + 1);
            boxSelect.addProperty("extend_y", 50203297);
            boxSelect.addProperty("extend_z", 2 * dist + 1);
            JsonObject finalDensityModified = new JsonObject();
            finalDensityModified.addProperty("type", "minecraft:range_choice");
            finalDensityModified.add("input", boxSelect);
            finalDensityModified.addProperty("min_inclusive", 0);
            finalDensityModified.addProperty("max_exclusive", 1);
            finalDensityModified.add("when_in_range", overflowCheck);
            finalDensityModified.addProperty("when_out_of_range", "farlandstraveler:far_lands/final_density");

            //应用修改后的noise_router
            noiseRouterOverworld.add("final_density", finalDensityModified);
            noiseRouterOverworld.add("initial_density_without_jaggedness", getDensity(noiseRouterOverworld.get("initial_density_without_jaggedness"), dist, "initial_density_without_jaggedness"));
            noiseRouterOverworld.add("fluid_level_floodedness", getDensity(noiseRouterOverworld.get("fluid_level_floodedness"), dist, "fluid_level_floodedness"));
            noiseRouterOverworld.add("barrier", getDensity(noiseRouterOverworld.get("barrier"), dist, "barrier"));
        }, false);
    }

    /**
     * 用于包装世界预设文件中的生物群系源，使得边境之地能生成自定义生物群系
     * @param path 世界预设文件的路径
     * @param name MixsonEvent的名称（随便取）
     */
    public static void worldPresentInjector(String path, String name) {
        Mixson.registerEvent(0, ResourceLocation.withDefaultNamespace(path).toString(), name, (context) -> {
            JsonObject normalOverworld = context.getFile().getAsJsonObject()
                    .getAsJsonObject("dimensions")
                    .getAsJsonObject(ResourceLocation.withDefaultNamespace("overworld").toString())
                    .getAsJsonObject("generator");
            JsonElement biomeSource = normalOverworld.get("biome_source");

            if (!biomeSource.isJsonNull()) {
                int dist = SectionPos.blockToSectionCoord(Config.FAR_LANDS_DISTANCE.getAsInt());
                JsonObject biomeSourceNew = new JsonObject();
                biomeSourceNew.addProperty("type", "farlandstraveler:box_select");
                biomeSourceNew.addProperty("origin_x", -dist * 4);
                biomeSourceNew.addProperty("origin_y", -6275412);
                biomeSourceNew.addProperty("origin_z", -dist * 4);
                biomeSourceNew.addProperty("extend_x", dist * 8);
                biomeSourceNew.addProperty("extend_y", 12550824);
                biomeSourceNew.addProperty("extend_z", dist * 8);
                biomeSourceNew.add("inside", biomeSource);
                biomeSourceNew.addProperty("outside","farlandstraveler:far_lands");
                normalOverworld.add("biome_source", biomeSourceNew);
            }
        }, false);
    }

    /**
     * 用于包装维度定义文件中的生物群系源，使得边境之地能生成自定义生物群系。其中只有当生物群系源的类型是多噪声型时才会应用。（备用方案，仅在使用其他数据包修改主世界维度时有效，其他情况使用世界预设注入器）
     * @param path 维度定义文件的路径
     * @param name MixsonEvent的名称（随便取）
     */
    public static void dimensionInjector(String path, String name) {
        Mixson.registerEvent(0, ResourceLocation.withDefaultNamespace(path).toString(), name, (context) -> {
            JsonObject normalOverworld = context.getFile().getAsJsonObject()
                    .getAsJsonObject("generator");
            JsonElement biomeSource = normalOverworld.get("biome_source");
            String type = biomeSource.getAsJsonObject().getAsJsonObject("type").getAsString();
            if (!biomeSource.isJsonNull() && type.contains("multi_noise")) {
                int dist = SectionPos.blockToSectionCoord(Config.FAR_LANDS_DISTANCE.getAsInt());
                JsonObject biomeSourceNew = new JsonObject();
                biomeSourceNew.addProperty("type", "farlandstraveler:box_select");
                biomeSourceNew.addProperty("origin_x", -dist * 4);
                biomeSourceNew.addProperty("origin_y", -6275412);
                biomeSourceNew.addProperty("origin_z", -dist * 4);
                biomeSourceNew.addProperty("extend_x", dist * 8);
                biomeSourceNew.addProperty("extend_y", 12550824);
                biomeSourceNew.addProperty("extend_z", dist * 8);
                biomeSourceNew.add("inside", biomeSource);
                biomeSourceNew.addProperty("outside","farlandstraveler:far_lands");
                normalOverworld.add("biome_source", biomeSourceNew);
            }
        }, false);
    }
}
