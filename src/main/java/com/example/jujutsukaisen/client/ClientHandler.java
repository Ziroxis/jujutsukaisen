package com.example.jujutsukaisen.client;

import com.example.jujutsukaisen.client.renderer.entities.curses.*;
import com.example.jujutsukaisen.client.renderer.entities.npc.MaidQuestRenderer;
import com.example.jujutsukaisen.client.renderer.entities.npc.PunchSenseiRenderer;
import com.example.jujutsukaisen.client.renderer.entities.npc.SwordSenseiRenderer;
import com.example.jujutsukaisen.init.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void OnSetup()
    {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ROPPONGI.get(), new RoppongiRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SMALL_POX.get(), new SmallPoxRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.LIZARD.get(), new LizardRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.PUNCH_SENSEI.get(), new PunchSenseiRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SWORD_SENSEI.get(), new SwordSenseiRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAID_QUESTER.get(), new MaidQuestRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GRASS_HOPPER.get(), new GrassHopperRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.POSSESED_PUPPET.get(), new PossesedPuppetRenderer.Factory());

        Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        /*
        ClientHandler.addPlayerLayers(playerSkinMap.get("default"));
        ClientHandler.addPlayerLayers(playerSkinMap.get("slim"));

         */

    }
}
