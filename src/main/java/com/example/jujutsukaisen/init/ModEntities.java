package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.curses.LizardEntity;
import com.example.jujutsukaisen.entities.curses.RoppongiEntity;
import com.example.jujutsukaisen.entities.curses.SmallPoxEntity;
import com.example.jujutsukaisen.entities.npc.SenseiEntity;
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
    public static final RegistryObject<EntityType<SmallPoxEntity>> SMALL_POX = ENTITIES
            .register("smallpox_entity",
                    () -> EntityType.Builder.of(SmallPoxEntity::new, EntityClassification.CREATURE)
                            .sized(4f, 5f)
                            .fireImmune()
                            .setTrackingRange(15)
                            .build(new ResourceLocation(Main.MODID, "smallpox_entity").toString()));
    public static final RegistryObject<EntityType<LizardEntity>> LIZARD = ENTITIES
            .register("lizard_entity",
                    () -> EntityType.Builder.of(LizardEntity::new, EntityClassification.CREATURE)
                            .sized(1f, 2f)
                            .fireImmune()
                            .setTrackingRange(15)
                            .build(new ResourceLocation(Main.MODID, "lizard_entity").toString()));
    public static final RegistryObject<EntityType<SenseiEntity>> SENSEI = ENTITIES
            .register("sensei_entity",
                    () -> EntityType.Builder.of(SenseiEntity::new, EntityClassification.CREATURE)
                            .sized(1f, 2f)
                            .setTrackingRange(5)
                            .build(new ResourceLocation(Main.MODID, "sensei_entity").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
