package com.smallmanseries.farlandstraveler.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.smallmanseries.farlandstraveler.common.worldgen.features.configurations.TransformedConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * “旋转与镜像”地物
 * <p>
 * 一个虚拟地物，本质上是一个包装器，用于把输入的地物进行旋转与镜像，之后放置这个修改后的地物
 */
public class TransformedFeature extends Feature<TransformedConfiguration> {
    public TransformedFeature(Codec<TransformedConfiguration> codec) {
        super(codec);
    }

    /**
     * 创建一个“坐标系被变换过”的 {@link WorldGenLevel} 视图
     * <p>
     * 这个视图把每一个带方块坐标的调用都先做变换再转交给真实的存档，所以被包裹的地物
     * 完全感知不到变换的存在：它照常查、照常写，最终落进世界里的结果就是被旋转 / 镜像过的。
     * 读和写走同一套变换，才不会出现“地物以为自己写在了上方、其实写到了下方”这类错位。
     *
     * @param level  真实存档
     * @param config 变换配置
     * @param origin 放置原点
     * @return 变换后的世界视图
     */
    private static WorldGenLevel transformingLevel(WorldGenLevel level, TransformedConfiguration config, BlockPos origin) {
        return (WorldGenLevel) Proxy.newProxyInstance(
                WorldGenLevel.class.getClassLoader(),
                new Class<?>[]{WorldGenLevel.class},
                (_, method, args) -> {
                    // 入参：把所有方块坐标先变换过去
                    Object[] mappedArgs = args;
                    if (args != null) {
                        mappedArgs = args.clone();
                        for (int i = 0; i < mappedArgs.length; i++) {
                            if (mappedArgs[i] instanceof BlockPos pos) {
                                mappedArgs[i] = config.transform(origin, pos);
                            }
                        }
                    }

                    Object result;
                    try {
                        result = method.invoke(level, mappedArgs);
                    } catch (InvocationTargetException e) {
                        // 把被包裹地物抛出的异常原样抛出，不要吞进反射异常的壳里
                        throw e.getCause();
                    }

                    // 返回值：方块坐标同理要变换回来
                    if (result instanceof BlockPos pos) {
                        result = config.transform(origin, pos);
                    }
                    return result;
                }
        );
    }

    @Override
    public boolean place(FeaturePlaceContext<TransformedConfiguration> context) {
        TransformedConfiguration config = context.config();
        BlockPos origin = context.origin();

        WorldGenLevel level = transformingLevel(context.level(), config, origin);

        return config.feature().value().place(level, context.chunkGenerator(), context.random(), origin);
    }
}
