package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.curses.RoppongiEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);


    public static final RegistryObject<EntityType<RoppongiEntity>> ROPPONGI = ENTITIES
            .register("roppongi_entity",
                    () -> EntityType.Builder.of(RoppongiEntity::new, EntityClassification.CREATURE)
                            .sized(1f, 2f)
                            .fireImmune()
                            .setTrackingRange(10)
                            .build(new ResourceLocation(Main.MODID, "roppongi_entity").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
