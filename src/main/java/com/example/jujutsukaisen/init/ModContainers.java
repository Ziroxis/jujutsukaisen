package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.containers.ShadowContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Shadow;

public class ModContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
            .create(ForgeRegistries.CONTAINERS, Main.MODID);

    /*
        public static final RegistryObject<ContainerType<ShadowContainer>> SHADOW = CONTAINER_TYPES
            .register("display_case", () -> IForgeContainerType.create(ShadowContainer::new));

     */
}
