package com.example.jujutsukaisen.abilities.reverse_cursed_energy;

import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.sorts.PassiveAbility;
import com.example.jujutsukaisen.api.data.IExtraUpdateData;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModEffects;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SSyncAbilityDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;

public class RejuvinationAbility extends PassiveAbility {

    public static final RejuvinationAbility INSTANCE = new RejuvinationAbility();


    public RejuvinationAbility()
    {
        super("Rejuvination", AbilityCategories.AbilityCategory.REVERSED);
        this.setMaxCooldown(1620);
        this.hideInGUI(false);
        this.duringPassiveEvent = this::duringPassiveEvent;
    }

    private void duringPassiveEvent(PlayerEntity player)
    {
        if (player.getHealth() <= 1 && !this.isOnCooldown())
        {
            IEntityStats statsProps = EntityStatsCapability.get(player);
            IAbilityData abilityProps = AbilityDataCapability.get(player);
            player.setHealth(15);
            statsProps.setCursedEnergy(statsProps.returnCursedEnergy() / 3);
            this.setState(State.COOLDOWN);
            PacketHandler.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityProps), player);
            PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), statsProps), player);
            return;
        }
    }


}
