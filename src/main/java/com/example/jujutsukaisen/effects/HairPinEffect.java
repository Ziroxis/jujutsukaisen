package com.example.jujutsukaisen.effects;

import com.example.jujutsukaisen.api.Beapi;
import net.minecraft.block.Block;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class HairPinEffect extends SpecialEffect{
    public HairPinEffect() {
        super(EffectType.HARMFUL, Beapi.hexToRGB("#000000").getRGB());
    }

    @Override
    public float[] getOverlayColor() {
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
