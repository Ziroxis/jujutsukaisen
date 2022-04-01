package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.Main;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class SpellsScreen extends Screen {

    //256, 256
    private final ResourceLocation background = new ResourceLocation(Main.MODID, "textures/gui/backgrounds/playerstats.png");

    private final int xSize = 256;
    private final int ySize = 256;

    private int guiLeft;
    private int guiTop;

    ITextComponent back = new StringTextComponent("X");

    private Minecraft minecraft;

    protected SpellsScreen(Minecraft mc)
    {
        super(new StringTextComponent(""));
        minecraft = mc;
    }

    @Override
    public void init()
    {
        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        Button Back = new Button(guiLeft + 230, guiTop + 25, 20, 20, back, b ->
        {
            Minecraft.getInstance().setScreen(new PlayerStatsScreen());
        });
        this.addButton(Back);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        backgroundRendering(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void backgroundRendering(MatrixStack matrixStack)
    {
        minecraft.getTextureManager().bind(background);
        GuiUtils.drawTexturedModalRect(matrixStack, guiLeft , guiTop + 20, 0, 0, xSize, ySize, 0);

        drawString(matrixStack, font, "Work in progress", guiLeft + 80, guiTop + 110, 16777215);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
