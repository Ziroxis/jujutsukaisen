package com.example.jujutsukaisen.client.gui.overlays;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class XpBarGuiOverlay {

    private static final ResourceLocation xpBar = new ResourceLocation(Main.MODID + ":textures/gui/overlay/xp_bar.png");
    private static final int tex_width = 165;
    private static final int tex_height = 10;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event)
    {
        Minecraft minecraft = Minecraft.getInstance();

        int posX = minecraft.getWindow().getGuiScaledWidth();
        int posY = minecraft.getWindow().getGuiScaledHeight();

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            IEntityStats props = EntityStatsCapability.get(player);
            minecraft.getTextureManager().bind(xpBar);

            //45, 159 -> Coordinates empty bar
            //45, 140 -> Coordinates full bar
            GuiUtils.drawTexturedModalRect((posX - 175) / 2, posY - 250, 45, 150, tex_width, tex_height, 0);

            float xpRatio = ((float) props.getExperience() /(float) props.getMaxExperience()) ;
            int set_width = (int) (tex_width * xpRatio);
            int move_tex = (tex_width - set_width);
            GuiUtils.drawTexturedModalRect((posX - 175) / 2, posY - 249, 45, 132, set_width, tex_height, 0);


            String level = "Level: " + props.getLevel();
            Beapi.drawStringWithBorder(Minecraft.getInstance().font, event.getMatrixStack(), level, ((posX - 175) / 2), posY - 239, Color.BLUE.getRGB());
            String experience = "Experience: " + props.getExperience() + "/" + props.getMaxExperience();
            Beapi.drawStringWithBorder(Minecraft.getInstance().font, event.getMatrixStack(), experience, ((posX + 30) / 2), posY - 239, Color.BLUE.getRGB());


        }
    }
}
