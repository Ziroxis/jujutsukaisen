package com.example.jujutsukaisen.abilities.basic;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousPunchAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CursedEnergyContinuousPunchAbility extends ContinuousPunchAbility {

    public static final CursedEnergyContinuousPunchAbility INSTANCE = new CursedEnergyContinuousPunchAbility();

    public CursedEnergyContinuousPunchAbility()
    {
        super("Cursed punch", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("The user envelops his next punches with an amount of cursed energy");
        this.setMaxCooldown(5);
        this.setCursedEnergyCost(2);
        this.onHitEntityEvent = this::onHitEntity;

    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        propsEntity.alterCursedEnergy(-10);
        PacketHandler.sendToServer(new CursedEnergySync(propsEntity.returnCursedEnergy()));

        return 10;
    }
}
