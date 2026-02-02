package com.smallmanseries.farlandstraveler.common.worldgen.farlands;

import com.smallmanseries.farlandstraveler.common.DataRegister;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public class FarLandsAccess implements RegistryAccess {

    //获取边境之地定义文件，如果获取不到，就崩溃！！！！！！！！！！！！！！！！啊啊啊啊啊啊啊啊啊啊！！！！！
    public FarLands getFarLands(ResourceKey<FarLands> farLandsResourceKey){
        return lookupOrThrow(DataRegister.FAR_LANDS).getValueOrThrow(farLandsResourceKey);
    }



    @Override
    public <E> @NotNull Optional<Registry<E>> lookup(@NotNull ResourceKey<? extends Registry<? extends E>> resourceKey) {
        return Optional.empty();
    }

    @Override
    public @NotNull Stream<RegistryEntry<?>> registries() {
        return Stream.empty();
    }
}
