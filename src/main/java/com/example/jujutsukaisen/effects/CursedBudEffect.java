package com.example.jujutsukaisen.effects;

import com.example.jujutsukaisen.api.Beapi;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class CursedBudEffect extends SpecialEffect {

    public CursedBudEffect() {
        super(EffectType.HARMFUL, Beapi.hexToRGB("#000000").getRGB());

    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier)
    {
        //Shit happens here for the cursed bud
        //TODO make the effect
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
    public boolean isDurationEffectTick(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return true;
    }

    public float[] getOverlayColor()
    {
        return new float[] { 0.0f, 0.0f, 0.0f, 0f };
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
        return false;
    }

    @Override
    public ResourceLocation getResourceLocation(int duration) {
        return null;
    }
}
