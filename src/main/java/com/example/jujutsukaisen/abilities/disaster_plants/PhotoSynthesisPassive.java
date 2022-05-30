package com.example.jujutsukaisen.abilities.disaster_plants;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;

public class PhotoSynthesisPassive extends PassiveAbility {

    public static final PhotoSynthesisPassive INSTANCE = new PhotoSynthesisPassive();

    public PhotoSynthesisPassive()
    {
        super("Photosynthesis", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Get regeneration while under the sun");
        this.hideInGUI(false);
        this.duringPassiveEvent = this::duringPassiveEvent;
        this.offDuringPassive = this::offPassiveEvent;
    }

    private void duringPassiveEvent(PlayerEntity player)
    {
        if (!player.level.isDay() && player.level.isClientSide)
            return;

        float f = player.getBrightness();
        BlockPos blockpos = player.getVehicle() instanceof BoatEntity ? (new BlockPos(player.getX(), (double)Math.round(player.getY()), player.getZ())).above() : new BlockPos(player.getX(), (double)Math.round(player.getY()), player.getZ());
        if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && player.level.canSeeSky(blockpos))
        {
            if (!player.hasEffect(ModEffects.REGENERATION.get()))
                player.addEffect(new EffectInstance(ModEffects.REGENERATION.get(), 20, 1));
        }

    }
    private void offPassiveEvent(PlayerEntity player)
    {
        if (player.hasEffect(ModEffects.REGENERATION.get()))
            player.removeEffect(ModEffects.REGENERATION.get());

    }
}
