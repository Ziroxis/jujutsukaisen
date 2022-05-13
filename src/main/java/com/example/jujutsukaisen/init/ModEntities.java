package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.curses.*;
import com.example.jujutsukaisen.entities.npc.PunchSenseiEntity;
import com.example.jujutsukaisen.entities.npc.SwordSenseiEntity;
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
    public static final RegistryObject<EntityType<PunchSenseiEntity>> PUNCH_SENSEI = ENTITIES
            .register("punchsensei_entity",
                    () -> EntityType.Builder.of(PunchSenseiEntity::new, EntityClassification.CREATURE)
                            .sized(1f, 2f)
                            .setTrackingRange(5)
                            .build(new ResourceLocation(Main.MODID, "punchsensei_entity").toString()));
    public static final RegistryObject<EntityType<SwordSenseiEntity>> SWORD_SENSEI = ENTITIES
            .register("swordsensei_entity",
                    () -> EntityType.Builder.of(SwordSenseiEntity::new, EntityClassification.CREATURE)
                            .sized(1f, 2f)
                            .setTrackingRange(5)
                            .build(new ResourceLocation(Main.MODID, "swordsensei_entity").toString()));
    public static final RegistryObject<EntityType<GrassHopperEntity>> GRASS_HOPPER = ENTITIES
            .register("grasshopper_entity",
                    () -> EntityType.Builder.of(GrassHopperEntity::new, EntityClassification.CREATURE)
                            .sized(2f, 2f)
                            .setTrackingRange(2)
                            .build(new ResourceLocation(Main.MODID, "grasshopper_entity").toString()));
    public static final RegistryObject<EntityType<PossesedPuppetEntity>> POSSESED_PUPPET = ENTITIES
            .register("possesedpuppet_entity",
                    () -> EntityType.Builder.of(PossesedPuppetEntity::new, EntityClassification.CREATURE)
                            .sized(2f, 2f)
                            .setTrackingRange(2)
                            .build(new ResourceLocation(Main.MODID, "possesedpuppet_entity").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
