package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.blood_manipulation.BloodShurikenAbility;
import com.example.jujutsukaisen.abilities.cursed_speech.StopAbility;
import com.example.jujutsukaisen.abilities.disaster_flames.DisasterFlamesPassive;
import com.example.jujutsukaisen.abilities.disaster_flames.FlameTouchAbility;
import com.example.jujutsukaisen.abilities.disaster_plants.PhotoSynthesisPassive;
import com.example.jujutsukaisen.abilities.disaster_plants.WoodenBallAbility;
import com.example.jujutsukaisen.abilities.disaster_tides.DisasterTidesPassive;
import com.example.jujutsukaisen.abilities.disaster_tides.WaterFlowAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.DashAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.KihonZukiAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.ManjiKickAbility;
import com.example.jujutsukaisen.abilities.heavenly_restriction.ShiranuiGataAbility;
import com.example.jujutsukaisen.abilities.projection_sorcery.FrameSpeedAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModAttributes;
import com.example.jujutsukaisen.init.ModResources;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CRequestSyncWorldDataPacket;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.networking.server.SOpenCursedSpiritAcceptanceScreenPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateHealthPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.awt.*;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class CursedSpiritAcceptanceScreen extends Screen {
    public static final AttributeModifier HEAVENLY_STRENGTH = new AttributeModifier(UUID.fromString("9cd5ec6c-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 3, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_SPEED = new AttributeModifier(UUID.fromString("b7b47dd2-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 0.2, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_JUMP = new AttributeModifier(UUID.fromString("d058ad40-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_HASTE = new AttributeModifier(UUID.fromString("f41bd7ac-dcd7-11ec-9d64-0242ac120002"),
            "Heavenly", 1, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier HEAVENLY_HEALTH = new AttributeModifier(UUID.fromString("ed591554-dce3-11ec-9d64-0242ac120002"),
            "Heavenly", 10, AttributeModifier.Operation.ADDITION);

    //256, 256
    //TODO change the textures to the latest texture
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
            int rng = Beapi.RNG(3);
            switch (rng)
            {
                case 0:
                    props.setClan(ModValues.NONE);
                    props.setTechnique(ModValues.DISASTER_TIDES);
                    props.setRestriction(ModValues.NONE);
                    abilityProps.addUnlockedAbility(WaterFlowAbility.INSTANCE);
                    abilityProps.addUnlockedAbility(DisasterTidesPassive.INSTANCE);
                    props.setCurse(ModValues.WATER);
                    break;
                case 1:
                    props.setClan(ModValues.NONE);
                    props.setTechnique(ModValues.DISASTER_FLAMES);
                    props.setRestriction(ModValues.NONE);
                    abilityProps.addUnlockedAbility(FlameTouchAbility.INSTANCE);
                    abilityProps.addUnlockedAbility(DisasterFlamesPassive.INSTANCE);
                    props.setCurse(ModValues.FIRE);
                    break;
                case 2:
                    props.setClan(ModValues.NONE);
                    props.setTechnique(ModValues.DISASTER_PLANTS);
                    props.setRestriction(ModValues.NONE);
                    abilityProps.addUnlockedAbility(WoodenBallAbility.INSTANCE);
                    abilityProps.addUnlockedAbility(PhotoSynthesisPassive.INSTANCE);
                    props.setCurse(ModValues.PLANTS);
                    break;
            }
            PacketHandler.sendToServer(new CSyncentityStatsPacket(props));
            PacketHandler.sendToServer(new CSyncAbilityDataPacket(abilityProps));
            this.onClose();
        });
        Button declineButton = new Button(posX + 160, posY + 190, 70, 20, new TranslationTextComponent("No.", "Decline"), b ->
        {
            int rng = Beapi.RNG(4);
            switch (rng)
            {
                case 0:
                    props.setClan(ModValues.Kamo);
                    props.setTechnique(ModValues.BLOOD_MANIPULATION);
                    abilityProps.addUnlockedAbility(BloodShurikenAbility.INSTANCE);
                    props.setCurse(ModValues.HUMAN);
                    props.setRestriction(ModValues.NONE);
                    break;
                case 1:
                    props.setClan(ModValues.Inumaki);
                    props.setTechnique(ModValues.CURSED_SPEECH);
                    abilityProps.addUnlockedAbility(StopAbility.INSTANCE);
                    props.setCurse(ModValues.HUMAN);
                    props.setRestriction(ModValues.NONE);
                    break;
                case 2:
                    props.setClan(ModValues.Zenin);
                    props.setTechnique(ModValues.PROJECTION_SORCERY);
                    abilityProps.addUnlockedAbility(FrameSpeedAbility.INSTANCE);
                    props.setRestriction(ModValues.NONE);
                    props.setCurse(ModValues.HUMAN);
                    break;
                case 3:
                    int rng_restriction = Beapi.RNG(2);
                    if (rng_restriction == 0)
                        props.setRestriction(ModValues.RESTRICTION_HEAVENLY);
                    else if (rng_restriction == 1)
                        props.setRestriction(ModValues.RESTRICTION_CONSTITUTION);
                    break;
            }
            if (props.getRestriction().equals(ModValues.RESTRICTION_CONSTITUTION))
            {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
                player.setHealth(10);
                int rng_restriction = Beapi.RNG(3);
                switch (rng_restriction)
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
            }
            else if (props.getRestriction().equals(ModValues.RESTRICTION_HEAVENLY))
            {
                props.setClan(ModValues.Zenin);
                props.setCurse(ModValues.HUMAN);
                props.setTechnique(ModValues.BRUTE_FORCE);
                props.setMaxCursedEnergy(0);
                props.setCursedEnergy(0);
                abilityProps.addUnlockedAbility(ShiranuiGataAbility.INSTANCE);
                abilityProps.addUnlockedAbility(ManjiKickAbility.INSTANCE);
                abilityProps.addUnlockedAbility(KihonZukiAbility.INSTANCE);
                abilityProps.addUnlockedAbility(DashAbility.INSTANCE);
                player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(HEAVENLY_STRENGTH);
                player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(HEAVENLY_SPEED);
                player.getAttribute(ModAttributes.JUMP_HEIGHT.get()).addTransientModifier(HEAVENLY_JUMP);
                player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(HEAVENLY_HASTE);
                player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(HEAVENLY_HEALTH);
                player.setHealth(30);
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

        minecraft.getTextureManager().bind(playerstats);
        GuiUtils.drawTexturedModalRect(matrixStack, guiLeft, guiTop + 20, 0, 0, xSize, ySize, 0);
        drawString(matrixStack, font, TextFormatting.GRAY + "DO YOU ACCEPT THE WEIGHT OF CURSES?", guiLeft + 30, guiTop + 30, Color.GRAY.getRGB());
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
