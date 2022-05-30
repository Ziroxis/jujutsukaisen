package com.example.jujutsukaisen.entities.projectiles.disaster_plants;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.AbilityProjectileRenderer;
import com.example.jujutsukaisen.models.projectiles.disaster_plants.CursedBudsModel;
import com.example.jujutsukaisen.models.projectiles.disaster_plants.EnergyBlastModel;
import com.example.jujutsukaisen.models.projectiles.disaster_plants.WoodenBallModel;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DisasterPlantsProjectiles {

    public static final RegistryObject<EntityType<CursedBudsProjectile>> CURSED_BUDS = Beapi.registerEntityType("Cursed Buds",
            () -> Beapi.createEntityType(CursedBudsProjectile::new)
                    .sized(1f, 1f)
                    .build(Main.MODID + ":cursed_buds"));

    public static final RegistryObject<EntityType<EnergyBlastProjectile>> ENERGY_BLAST = Beapi.registerEntityType("Energy Blast",
            () -> Beapi.createEntityType(EnergyBlastProjectile::new)
                    .sized(1f, 1f)
                    .build(Main.MODID + ":energy_blast"));

    public static final RegistryObject<EntityType<WoodenBallProjectile>> WOODEN_BALL = Beapi.registerEntityType("Wooden Bal",
            () -> Beapi.createEntityType(WoodenBallProjectile::new)
                    .sized(2f, 2f)
                    .build(Main.MODID + ":wooden_ball"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(CURSED_BUDS.get(), new AbilityProjectileRenderer.Factory(new CursedBudsModel())
                .setTexture("disaster_plants", "cursedbuds"));
        RenderingRegistry.registerEntityRenderingHandler(ENERGY_BLAST.get(), new AbilityProjectileRenderer.Factory(new EnergyBlastModel())
                .setTexture("disaster_plants", "energyblast"));
        RenderingRegistry.registerEntityRenderingHandler(WOODEN_BALL.get(), new AbilityProjectileRenderer.Factory(new WoodenBallModel())
                .setTexture("disaster_plants", "woodenball"));
    }
}
