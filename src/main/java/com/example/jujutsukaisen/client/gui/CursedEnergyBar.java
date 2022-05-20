package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class CursedEnergyBar {

    private final ResourceLocation manaBar = new ResourceLocation(Main.MODID + ":textures/gui/overlay/mana_bars.png");
    private final int tex_width = 9, tex_height = 102, bar_width = 7, bar_height = 100;


    //This is stolen code from the BC mod and that's stolen from the Naruto mod lmfao
    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event) {

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {


            Minecraft minecraft = Minecraft.getInstance();
            ClientPlayerEntity player = Minecraft.getInstance().player;
            IEntityStats props = EntityStatsCapability.get(player);
            minecraft.getTextureManager().bind(manaBar);
            /*
            RenderSystem.pushMatrix();
            RenderSystem.translatef(32, (float) (tex_height - 42), 0);
            RenderSystem.scalef(0.9f, 0.9f, 1);
            GuiUtils.drawTexturedModalRect(0, 0, 10, 44, 115, 46, 0);
            RenderSystem.scaled(1d, 1d, 1d);
            RenderSystem.color3f(50 / 255.0f, 50 / 255.0f, 50 / 255.0f);

            RenderSystem.popMatrix();
            RenderSystem.pushMatrix();
            RenderSystem.translatef(51.8f, (float) (tex_height + 5 - 38.9f), 0);
            RenderSystem.scalef(0.9f, 0.9f, 1);
            GuiUtils.drawTexturedModalRect(0, 0, 32, 6, (props.returnCursedEnergy() * 92) / props.getMaxCursedEnergy(), 38, 0);
            RenderSystem.color3f(1f, 1f, 1f);
            RenderSystem.scaled(1d, 1d, 1d);
            RenderSystem.popMatrix();
             */

            if (props.getMaxCursedEnergy() > 0) {
                Minecraft mc = Minecraft.getInstance();
                int colour_x = ((2 * 8) + 9); // CORRECT FORMULA ((chakra.returncolorChakra() * 8) + 9)
                mc.textureManager.bind(manaBar);
                mc.gui.blit(event.getMatrixStack(), 20, 130, 0, 0, tex_width, tex_height);
                if (props.getMaxCursedEnergy() <= 0) {
                    int set_height = tex_height;
                    mc.gui.blit(event.getMatrixStack(), 20, 130, colour_x, 0, tex_width, set_height); // set_height
                } else {
                    float manaRatio = ((float) props.returnCursedEnergy() / (float) props.getMaxCursedEnergy());
                    int set_height = (int) (tex_height * manaRatio);//(int) (bar_height * chakraratio)
                    int move_tex = (tex_height - set_height);
                    mc.gui.blit(event.getMatrixStack(), 20, 130 + move_tex, colour_x, move_tex, tex_width, set_height); // set_height
                }
            }
        }
    }
}
