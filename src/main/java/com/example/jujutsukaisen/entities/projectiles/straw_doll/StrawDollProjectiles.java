package com.example.jujutsukaisen.entities.projectiles.straw_doll;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityProjectileRenderer;
import com.example.jujutsukaisen.models.projectiles.straw_doll.GiantNailModel;
import com.example.jujutsukaisen.models.projectiles.straw_doll.SmallNailModel;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StrawDollProjectiles {

    public static final RegistryObject<EntityType<SmallNailProjectile>> SMALL_NAIL = Beapi.registerEntityType("Small Nail",
            () -> Beapi.createEntityType(SmallNailProjectile::new)
                    .sized(0.5f, 0.5f)
                    .build(Main.MODID + ":small_nail"));

    public static final RegistryObject<EntityType<GiantNailProjectile>> GIANT_NAIL = Beapi.registerEntityType("Giant Nail",
            () -> Beapi.createEntityType(GiantNailProjectile::new)
                    .sized(1f, 1f)
                    .build(Main.MODID + ":giant_nail"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(SMALL_NAIL.get(), new AbilityProjectileRenderer.Factory(new SmallNailModel())
                .setTexture("straw_doll", "smallnail").setScale(1));
        RenderingRegistry.registerEntityRenderingHandler(GIANT_NAIL.get(), new AbilityProjectileRenderer.Factory(new GiantNailModel())
                .setTexture("straw_doll", "giantnail").setScale(1));

    }
}
