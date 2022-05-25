package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodShurikenAbility;
import com.example.jujutsukaisen.abilities.cursed_speech.StopAbility;
import com.example.jujutsukaisen.abilities.disaster_flames.DisasterFlamesPassive;
import com.example.jujutsukaisen.abilities.disaster_flames.FlameTouchAbility;
import com.example.jujutsukaisen.abilities.disaster_tides.DisasterTidesPassive;
import com.example.jujutsukaisen.abilities.disaster_tides.WaterFlowAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameSpeedAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModResources;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CRequestSyncWorldDataPacket;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.networking.server.SOpenCursedSpiritAcceptanceScreenPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class CursedSpiritAcceptanceScreen extends Screen {

    //256, 256
    private final ResourceLocation playerstats = ModResources.STATS;

    private final int xSize = 256;
    private final int ySize = 256;

    private int guiLeft;
    private int guiTop;

    ITextComponent accept = new StringTextComponent("accept");
    ITextComponent decline = new StringTextComponent("decline");



    private final PlayerEntity player;


    public CursedSpiritAcceptanceScreen() {
        super(new StringTextComponent(""));
        this.player = Minecraft.getInstance().player;
    }

    @Override
    public void init()
    {
        PacketHandler.sendToServer(new CRequestSyncWorldDataPacket());
        IEntityStats props = EntityStatsCapability.get(player);
        IAbilityData abilityProps = AbilityDataCapability.get(player);

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        int posX = ((this.width - 256) / 2) - 110;
        int posY = (this.height - 256) / 2;

        posX += 120;
        Button acceptanceButton = new Button(posX, posY + 190, 70, 20, new TranslationTextComponent("Yes.", "Accept"), b ->
        {
            int rng = Beapi.RNG(2);
            switch (rng)
            {
                case 0:
                    props.setClan(ModValues.NONE);
                    props.setTechnique(ModValues.DISASTER_TIDES);
                    abilityProps.addUnlockedAbility(WaterFlowAbility.INSTANCE);
                    abilityProps.addUnlockedAbility(DisasterTidesPassive.INSTANCE);
                    props.setCurse(ModValues.WATER);
                    break;
                case 1:
                    props.setClan(ModValues.NONE);
                    props.setTechnique(ModValues.DISASTER_FLAMES);
                    abilityProps.addUnlockedAbility(FlameTouchAbility.INSTANCE);
                    abilityProps.addUnlockedAbility(DisasterFlamesPassive.INSTANCE);
                    props.setCurse(ModValues.FIRE);
                    break;
            }
            PacketHandler.sendToServer(new CSyncentityStatsPacket(props));
            PacketHandler.sendToServer(new CSyncAbilityDataPacket(abilityProps));
            this.onClose();
        });
        Button declineButton = new Button(posX + 160, posY + 190, 70, 20, new TranslationTextComponent("No.", "Decline"), b ->
        {
            int rng = Beapi.RNG(3);
            switch (rng)
            {
                case 0:
                    props.setClan(ModValues.Kamo);
                    props.setTechnique(ModValues.BLOOD_MANIPULATION);
                    abilityProps.addUnlockedAbility(BloodShurikenAbility.INSTANCE);
                    props.setCurse(ModValues.HUMAN);
                    break;
                case 1:
                    props.setClan(ModValues.Inumaki);
                    props.setTechnique(ModValues.CURSED_SPEECH);
                    abilityProps.addUnlockedAbility(StopAbility.INSTANCE);
                    props.setCurse(ModValues.HUMAN);
                    break;
                case 2:
                    props.setClan(ModValues.Zenin);
                    props.setTechnique(ModValues.PROJECTION_SORCERY);
                    abilityProps.addUnlockedAbility(FrameSpeedAbility.INSTANCE);
                    props.setCurse(ModValues.HUMAN);
                    break;
            }
            PacketHandler.sendToServer(new CSyncentityStatsPacket(props));
            PacketHandler.sendToServer(new CSyncAbilityDataPacket(abilityProps));
            this.onClose();
        });
        this.addButton(acceptanceButton);
        this.addButton(declineButton);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        backgroundRendering(matrixStack);
        //renderEntityInInventory(guiLeft + 180, guiTop + 200, 75, (guiLeft + 179) - mouseX, (guiTop + 78) - mouseY, this.minecraft.player);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void backgroundRendering(MatrixStack matrixStack)
    {
        PlayerEntity player = this.getMinecraft().player;

        minecraft.getTextureManager().bind(playerstats);
        GuiUtils.drawTexturedModalRect(matrixStack, guiLeft, guiTop + 20, 0, 0, xSize, ySize, 0);
        drawString(matrixStack, font, TextFormatting.GRAY + "DO YOU ACCEPT THE WEIGHT OF CURSES?", guiLeft + 42, guiTop + 30, Color.GRAY.getRGB());
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    public static void open()
    {
        Minecraft.getInstance().setScreen(new CursedSpiritAcceptanceScreen());
    }
}
