package com.example.jujutsukaisen.abilities.basic;

import com.example.jujutsukaisen.api.ability.Api;
import com.example.jujutsukaisen.api.ability.PunchAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CursedEnergyPunchAbility extends PunchAbility {

    public static final CursedEnergyPunchAbility INSTANCE = new CursedEnergyPunchAbility();

    public CursedEnergyPunchAbility()
    {
        super("Cursed punch", Api.AbilityCategory.BASIC);
        this.setDescription("The user envelops his next punch with a big amount of cursed energy");
        this.setMaxCooldown(5);
        this.setCursedEnergyCost(1);
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
