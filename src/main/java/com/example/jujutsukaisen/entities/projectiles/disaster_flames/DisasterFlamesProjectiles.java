package com.example.jujutsukaisen.entities.projectiles.disaster_flames;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityProjectileRenderer;
import com.example.jujutsukaisen.models.projectiles.disaster_flames.EmberInsectModel;
import com.example.jujutsukaisen.models.projectiles.disaster_flames.FlameArrowModel;
import com.example.jujutsukaisen.models.projectiles.disaster_flames.FlameBallModel;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DisasterFlamesProjectiles {

    public static final RegistryObject<EntityType<EmberInsectProjectile>> EMBER_INSECT = Beapi.registerEntityType("Ember Insect",
            () -> Beapi.createEntityType(EmberInsectProjectile::new)
                    .sized(0.5f, 0.5f)
                    .build(Main.MODID + ":ember_insect"));
    public static final RegistryObject<EntityType<FlameArrowProjectile>> FLAME_ARROW = Beapi.registerEntityType("Flame Arrow",
            () -> Beapi.createEntityType(FlameArrowProjectile::new)
                    .sized(0.5f, 0.5f)
                    .build(Main.MODID + ":flame_arrow"));
    public static final RegistryObject<EntityType<FlameBallProjectile>> FLAME_BALL = Beapi.registerEntityType("Flame Ball",
            () -> Beapi.createEntityType(FlameBallProjectile::new)
                    .sized(2f, 2f)
                    .build(Main.MODID + ":flame_ball"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EMBER_INSECT.get(), new AbilityProjectileRenderer.Factory(new EmberInsectModel())
                .setTexture("disaster_flames", "emberinsect").setScale(1));
        RenderingRegistry.registerEntityRenderingHandler(FLAME_ARROW.get(), new AbilityProjectileRenderer.Factory(new FlameArrowModel())
                .setTexture("disaster_flames", "flamearrow"));
        RenderingRegistry.registerEntityRenderingHandler(FLAME_BALL.get(), new AbilityProjectileRenderer.Factory(new FlameBallModel())
                .setTexture("disaster_flames", "flameball").setScale(2));
    }
}
