package com.example.jujutsukaisen.effects.sleep;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.effects.SpecialEffect;
import com.example.jujutsukaisen.init.ModAttributes;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeMod;

public class SleepEffect extends SpecialEffect {

    public SleepEffect()
    {
        super(EffectType.HARMFUL, Beapi.hexToRGB("#000000").getRGB());
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "2727e176-e9e8-4523-92f8-22619308d9ee", -256, AttributeModifier.Operation.ADDITION)
                .addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "323ffb58-0b57-434e-bdfc-354670e22d5f", -256, AttributeModifier.Operation.ADDITION)
                .addAttributeModifier(ModAttributes.JUMP_HEIGHT.get(), "e8cd65cb-2768-4fd8-aa54-bdcda029aaff", -256, AttributeModifier.Operation.ADDITION)
                .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "f8b2474d-4cdb-42b0-a868-327443a2a505", 100, AttributeModifier.Operation.ADDITION)
                .addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "096ae850-ca45-11ec-9d64-0242ac120002", -200, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean shouldRender(EffectInstance effect)
    {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect)
    {
        return false;
    }

    @Override
    public float[] getOverlayColor()
    {
        return new float[] { 0.0f, 0.0f, 0.0f, 1f };
    }

    @Override
    public boolean hasBodyOverlayColor() {
        return false;
    }

    @Override
    public Block getBlockOverlay() {
        return null;
    }

    @Override
    public boolean isBlockingMovement() {
        return true;
    }

    @Override
    public ResourceLocation getResourceLocation(int duration) {
        return null;
    }


}
