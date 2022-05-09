package com.example.jujutsukaisen.entities.projectiles.disaster_tides;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.disaster_tides.CursedFishAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityProjectileRenderer;
import com.example.jujutsukaisen.models.projectiles.blood_manipulation.BloodMeteoriteModel;
import com.example.jujutsukaisen.models.projectiles.blood_manipulation.BloodShurikenModel;
import com.example.jujutsukaisen.models.projectiles.blood_manipulation.PiercingBloodModel;
import com.example.jujutsukaisen.models.projectiles.disaster_tide.CursedFishModel;
import com.example.jujutsukaisen.models.projectiles.disaster_tide.CursedSharkModel;
import com.example.jujutsukaisen.models.projectiles.disaster_tide.WaterChargeModel;
import com.example.jujutsukaisen.models.projectiles.disaster_tide.WaterFlowModel;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DisasterTidesProjectiles  {

    public static final RegistryObject<EntityType<CursedFishProjectile>> CURSED_FISH_PROJECTILE = Beapi.registerEntityType("Cursed Fish Projectile",
            () -> Beapi.createEntityType(CursedFishProjectile::new)
                    .sized(0.5f, 0.5f)
                    .build(Main.MODID + ":cursed_fish_projectile"));
    public static final RegistryObject<EntityType<CursedSharkProjectile>> CURSED_SHARK_PROJECTILE = Beapi.registerEntityType("Cursed Shark Projectile",
            () -> Beapi.createEntityType(CursedSharkProjectile::new)
                    .sized(2f, 2f)
                    .build(Main.MODID + ":cursed_shark_projectile"));
    public static final RegistryObject<EntityType<WaterChargeProjectile>> WATER_CHARGE = Beapi.registerEntityType("Water Charge",
            () -> Beapi.createEntityType(WaterChargeProjectile::new)
                    .sized(1f, 1f)
                    .build(Main.MODID + ":water_charge"));
    public static final RegistryObject<EntityType<WaterFlowProjectile>> WATER_FLOW = Beapi.registerEntityType("Water Flow",
            () -> Beapi.createEntityType(WaterFlowProjectile::new)
                    .sized(1f, 1f)
                    .build(Main.MODID + ":water_flow"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(CURSED_FISH_PROJECTILE.get(), new AbilityProjectileRenderer.Factory(new CursedFishModel())
                .setTexture("disaster_tide", "cursedfish").setScale(1));
        RenderingRegistry.registerEntityRenderingHandler(CURSED_SHARK_PROJECTILE.get(), new AbilityProjectileRenderer.Factory(new CursedSharkModel())
                .setTexture("disaster_tide", "cursedshark").setScale(1));
        RenderingRegistry.registerEntityRenderingHandler(WATER_CHARGE.get(), new AbilityProjectileRenderer.Factory(new WaterChargeModel())
                .setTexture("disaster_tide", "watercharge").setScale(1));
        RenderingRegistry.registerEntityRenderingHandler(WATER_FLOW.get(), new AbilityProjectileRenderer.Factory(new WaterFlowModel())
                .setTexture("disaster_tide", "waterflow").setScale(1));
    }
}
