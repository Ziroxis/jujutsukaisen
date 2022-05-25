package com.example.jujutsukaisen.client.gui.overlays;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class LevelOverlay {
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        int posX = mc.getWindow().getGuiScaledWidth();
        int posY = mc.getWindow().getGuiScaledHeight();

        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
        {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            IEntityStats statsProps = EntityStatsCapability.get(player);
            int level = statsProps.getLevel();
            String levelString = level + "";
            String experienceString = statsProps.getExperience() + "";
            GuiUtils.drawTexturedModalRect((posX - 267) / 2, posY - 32, 32, 30, 32, 32, 0);
            Beapi.drawStringWithBorder(mc.font, event.getMatrixStack(), levelString, ((posX - 250) / 2) - mc.font.width(levelString) / 2, posY - 30, Color.WHITE.getRGB());
            Beapi.drawStringWithBorder(mc.font, event.getMatrixStack(), experienceString, ((posX - 250) / 2) - mc.font.width(experienceString) / 2, posY - 40, Color.WHITE.getRGB());

        }
    }
}
