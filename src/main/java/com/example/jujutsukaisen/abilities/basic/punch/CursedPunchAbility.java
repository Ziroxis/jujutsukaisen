package com.example.jujutsukaisen.abilities.basic.punch;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PunchAbility;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CursedPunchAbility extends PunchAbility {
    public static final CursedPunchAbility INSTANCE = new CursedPunchAbility();

    public CursedPunchAbility()
    {
        super("Cursed Punch", AbilityCategories.AbilityCategory.BASIC);
        this.setDescription("You envelop your next punch with an extra amount of cursed energy");
        this.setCursedEnergyCost(1);
        this.setMaxCooldown(5);
        this.onHitEntityEvent = this::onHitEntity;
    }

    private float onHitEntity(PlayerEntity player, LivingEntity target)
    {
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        target.knockback(1, 2, 2);
        propsEntity.alterCursedEnergy(-10);
        PacketHandler.sendToServer(new CursedEnergySync(propsEntity.returnCursedEnergy()));

        return 5;
    }
}
