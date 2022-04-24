package com.example.jujutsukaisen;

import com.example.jujutsukaisen.api.ability.AbilityArgument;
import com.example.jujutsukaisen.api.ability.AbilityGroupArgument;
import com.example.jujutsukaisen.client.ClientHandler;
import com.example.jujutsukaisen.client.gui.CursedEnergyBar;
import com.example.jujutsukaisen.events.ModEventBusEvents;
import com.example.jujutsukaisen.init.*;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "jujutsukaisen";



    public Main() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new ModCapabilities.Registry());

        ModEntities.ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModAbilities.register(modEventBus);
        ModQuests.QUESTS.register(modEventBus);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModCapabilities.init();
        ArgumentTypes.register("ability", AbilityArgument.class, new ArgumentSerializer<>(AbilityArgument::ability));
        ArgumentTypes.register("group", AbilityGroupArgument.class, new ArgumentSerializer<>(AbilityGroupArgument::abilityGroup));
        MinecraftForge.EVENT_BUS.register(ModEventBusEvents.class);
        ModNetwork.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        ClientHandler.OnSetup();
        ModKeyBinds.init();
        MinecraftForge.EVENT_BUS.register(new CursedEnergyBar());

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {

        }
    }
}
