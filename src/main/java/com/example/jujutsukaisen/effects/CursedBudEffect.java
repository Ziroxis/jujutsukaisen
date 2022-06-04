package com.example.jujutsukaisen.effects;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousPunchAbility;
import com.example.jujutsukaisen.api.ability.sorts.DamagedPassiveAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModDamageSource;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class CursedBudEffect extends SpecialEffect {

    public CursedBudEffect() {
        super(EffectType.HARMFUL, Beapi.hexToRGB("#000000").getRGB());
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier)
    {
        if (!(entity.tickCount % 20 == 0))
            return;
        if (!(entity instanceof PlayerEntity))
            return;


        PlayerEntity player = (PlayerEntity) entity;
        IEntityStats propStats = EntityStatsCapability.get(player);
        IAbilityData ablProps = AbilityDataCapability.get(entity);

        for (Ability ability : ablProps.getUnlockedAbilities(AbilityCategories.AbilityCategory.ALL))
        {
            if (ability == null)
                continue;

            try
            {
                if (ability instanceof ContinuousAbility && ability.isContinuous())
                {
                    player.hurt(DamageSource.GENERIC, 3);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        propStats.alterCursedEnergy(-2);

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
