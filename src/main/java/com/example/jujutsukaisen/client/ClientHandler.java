package com.example.jujutsukaisen.client;

import com.example.jujutsukaisen.client.renderer.entities.curses.RoppongiRenderer;
import com.example.jujutsukaisen.init.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void OnSetup()
    {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ROPPONGI.get(), new RoppongiRenderer.Factory());

    }
}
