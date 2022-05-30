package com.example.jujutsukaisen.abilities.disaster_plants;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ChargeableAbility;
import com.example.jujutsukaisen.entities.projectiles.blood_manipulation.PiercingBloodProjectile;
import com.example.jujutsukaisen.entities.projectiles.disaster_plants.EnergyBlastProjectile;
import com.example.jujutsukaisen.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class EnergyAbsorptionAbility extends ChargeableAbility {

    public static final EnergyAbsorptionAbility INSTANCE = new EnergyAbsorptionAbility();
    private boolean cancelled = false;

    public EnergyAbsorptionAbility()
    {
        super("Energy Absorption", AbilityCategories.AbilityCategory.TECHNIQUE);
        this.setDescription("Absorb energy to release it all at once");
        this.setMaxCooldown(20);
        this.setMaxChargeTime(15);
        this.setCursedEnergyCost(30);
        this.setCancelable();

        this.onStartChargingEvent = this::onStartChargingEvent;
        this.onEndChargingEvent = this::onEndChargingEvent;
        this.duringChargingEvent = this::duringChargingEvent;

    }
    private boolean onStartChargingEvent(PlayerEntity player)
    {
        this.cancelled = false;
        return true;
    }

    private void duringChargingEvent(PlayerEntity player, int chargeTimer)
    {
        if (player.invulnerableTime > 0)
        {
            this.cancelled = true;
            this.stopCharging(player);
        }

        player.addEffect(new EffectInstance(ModEffects.MOVEMENT_BLOCKED.get(), 200, 10));
    }

    private boolean onEndChargingEvent(PlayerEntity player)
    {
        if (this.cancelled)
            return true;


        int charge = this.getMaxChargeTime() - this.getChargeTime();

        if (charge < 20 * 10)
            return false;

        EnergyBlastProjectile projectile = new EnergyBlastProjectile(player.level, player);
        projectile.setDamage(charge / 10f);
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0, 1, 0.1f);
        player.level.addFreshEntity(projectile);
        player.removeEffect(ModEffects.MOVEMENT_BLOCKED.get());

        int cooldown = (int) Math.round(charge / 20.0) + 5;
        this.setMaxCooldown(cooldown);
        return true;
    }

}
