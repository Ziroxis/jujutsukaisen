package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class PlayerStatsScreen extends Screen {

    //Initialisation of everything needed in the class//


    //256, 256
    private final ResourceLocation playerstats = new ResourceLocation("jujutsukaisen:textures/gui/backgrounds/playerstats.png");

    private final int xSize = 256;
    private final int ySize = 256;

    private int guiLeft;
    private int guiTop;

    ITextComponent skills = new StringTextComponent("skills");


    private Minecraft minecraft;

    public PlayerStatsScreen(Minecraft mc)
    {
        super(new StringTextComponent(""));
        minecraft = mc;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        backgroundRendering(matrixStack);
        renderEntityInInventory(guiLeft + 180, guiTop + 200, 75, (guiLeft + 179) - mouseX, (guiTop + 78) - mouseY, this.minecraft.player);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void init()
    {
        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        Button Skills = new Button(guiLeft + 10, guiTop + 195, 100, 20, skills, b ->
        {
            Minecraft.getInstance().setScreen(new SpellsScreen(Minecraft.getInstance()));
        });
        this.addButton(Skills);
    }


    public void backgroundRendering(MatrixStack matrixStack)
    {

        PlayerEntity player = this.getMinecraft().player;
        IEntityStats props = EntityStatsCapability.get(player);


        String name = player.getName().getString();
        String clan = props.getClan();
        String grade = props.getCurseGrade();

        minecraft.getTextureManager().bind(playerstats);
        GuiUtils.drawTexturedModalRect(matrixStack, guiLeft, guiTop + 20, 0, 0, xSize, ySize, 0);

        //TODO make it more clean without the colors looking messy
        drawString(matrixStack, font, "INFO CARD", guiLeft + 92, guiTop + 30, Color.GRAY.getRGB());
        drawString(matrixStack, font, "Name: " + name, guiLeft + 5, guiTop + 50, 16777215);
        drawString(matrixStack, font, "Clan: " + clan, guiLeft + 5, guiTop + 65, 16777215);
        //TODO grade not showing
        drawString(matrixStack, font, "Grade: " + grade, guiLeft + 5, guiTop + 95, 16777215);

    }

    public static void renderEntityInInventory(int p_228187_0_, int p_228187_1_, int p_228187_2_, float p_228187_3_, float p_228187_4_, LivingEntity p_228187_5_) {
        float f = (float)Math.atan((double)(p_228187_3_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_228187_4_ / 40.0F));
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)p_228187_0_, (float)p_228187_1_, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((float)p_228187_2_, (float)p_228187_2_, (float)p_228187_2_);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        matrixstack.mulPose(quaternion);
        float f2 = p_228187_5_.yBodyRot;
        float f3 = p_228187_5_.yRot;
        float f4 = p_228187_5_.xRot;
        float f5 = p_228187_5_.yHeadRotO;
        float f6 = p_228187_5_.yHeadRot;
        p_228187_5_.yBodyRot = 180.0F + f * 20.0F;
        p_228187_5_.yRot = 180.0F + f * 40.0F;
        p_228187_5_.xRot = -f1 * 20.0F;
        p_228187_5_.yHeadRot = p_228187_5_.yRot;
        p_228187_5_.yHeadRotO = p_228187_5_.yRot;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderermanager.overrideCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderermanager.render(p_228187_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
        });
        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow(true);
        p_228187_5_.yBodyRot = f2;
        p_228187_5_.yRot = f3;
        p_228187_5_.xRot = f4;
        p_228187_5_.yHeadRotO = f5;
        p_228187_5_.yHeadRot = f6;
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

}
