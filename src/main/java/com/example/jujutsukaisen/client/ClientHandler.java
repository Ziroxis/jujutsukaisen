package com.example.jujutsukaisen.client;

import com.example.jujutsukaisen.client.renderer.entities.curses.LizardRenderer;
import com.example.jujutsukaisen.client.renderer.entities.curses.RoppongiRenderer;
import com.example.jujutsukaisen.client.renderer.entities.curses.SmallPoxRenderer;
import com.example.jujutsukaisen.client.renderer.entities.npc.SenseiRenderer;
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
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SENSEI.get(), new SenseiRenderer.Factory());

        Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        ClientHandler.addPlayerLayers(playerSkinMap.get("default"));
        ClientHandler.addPlayerLayers(playerSkinMap.get("slim"));

    }

    @OnlyIn(Dist.CLIENT)
    public static void addPlayerLayers(PlayerRenderer renderer)
    {
        List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> layers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, renderer, "field_177097_h");
        if(layers != null)
        {
            /*
            layers.add(new GrimoireLayer<>(renderer));
            layers.add(new TGLayer<>(renderer));
            layers.add(new BlackModeLayer<>(renderer));
            layers.add(new BlackCocoonLayer<>(renderer));
            layers.add(new SlashBladesLayer<>(renderer));
            */
        }
    }

}
