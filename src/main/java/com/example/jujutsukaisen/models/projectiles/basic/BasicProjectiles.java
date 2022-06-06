package com.example.jujutsukaisen.models.projectiles.basic;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityProjectileRenderer;
import com.example.jujutsukaisen.entities.projectiles.basic.EveningMoonProjectile;
import com.example.jujutsukaisen.entities.projectiles.blood_manipulation.BloodShurikenProjectile;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BasicProjectiles {

    public static final RegistryObject<EntityType<BloodShurikenProjectile>> EVENING_MOON = Beapi.registerEntityType("Evening Moon",
            () -> Beapi.createEntityType(EveningMoonProjectile::new)
                    .sized(2f, 2f)
                    .build(Main.MODID + ":evening_moon"));


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EVENING_MOON.get(), new AbilityProjectileRenderer.Factory(new EveningMoonModel())
                .setTexture("basic", "eveningmoon").setScale(1));
    }
}
