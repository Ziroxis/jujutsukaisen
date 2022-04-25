package com.example.jujutsukaisen.entities.projectiles.blood_manipulation;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityProjectileRenderer;
import com.example.jujutsukaisen.init.ModRegistry;
import com.example.jujutsukaisen.models.projectiles.blood_manipulation.BloodMeteoriteModel;
import com.example.jujutsukaisen.models.projectiles.blood_manipulation.BloodShurikenModel;
import com.example.jujutsukaisen.models.projectiles.blood_manipulation.PiercingBloodModel;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BloodProjectiles {

    public static final RegistryObject<EntityType<BloodShurikenProjectile>> BLOOD_SHURIKEN = Beapi.registerEntityType("Blood Shuriken",
            () -> Beapi.createEntityType(BloodShurikenProjectile::new)
                    .sized(0.5f, 0.5f)
                    .build(Main.MODID + ":blood_shuriken"));
    public static final RegistryObject<EntityType<BloodMeteoriteProjectile>> BLOOD_METEORITE = Beapi.registerEntityType("Blood Meteorite",
            () -> Beapi.createEntityType(BloodMeteoriteProjectile::new)
                    .sized(0.5f, 0.5f)
                    .build(Main.MODID + ":blood_meteorite"));
    public static final RegistryObject<EntityType<PiercingBloodProjectile>> PIERCING_BLOOD = Beapi.registerEntityType("Piercing Blood",
            () -> Beapi.createEntityType(PiercingBloodProjectile::new)
                    .sized(2f, 2f)
                    .build(Main.MODID + ":piercing_blood"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(BLOOD_SHURIKEN.get(), new AbilityProjectileRenderer.Factory(new BloodShurikenModel())
                .setTexture("blood_manipulation", "bloodshuriken").setScale(1));
        RenderingRegistry.registerEntityRenderingHandler(BLOOD_METEORITE.get(), new AbilityProjectileRenderer.Factory(new BloodMeteoriteModel())
                .setTexture("blood_manipulation", "bloodmeteorite"));
        RenderingRegistry.registerEntityRenderingHandler(PIERCING_BLOOD.get(), new AbilityProjectileRenderer.Factory(new PiercingBloodModel())
                .setTexture("blood_manipulation", "piercingblood").setScale(2));
    }
}
