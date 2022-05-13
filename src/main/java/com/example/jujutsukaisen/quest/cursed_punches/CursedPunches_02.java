package com.example.jujutsukaisen.quest.cursed_punches;

import com.example.jujutsukaisen.abilities.basic.CursedEnergyContinuousPunchAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.ObtainItemObjective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

public class CursedPunches_02 extends Quest {

    private Objective objective = new ObtainItemObjective("Prove your kill", 1, Items.BONE);

    public CursedPunches_02()
    {
        super("cursedpunches_02", "Trial: Collector");
        this.addObjective(this.objective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        player.giveExperienceLevels(10);
        IAbilityData props = AbilityDataCapability.get(player);
        IEntityStats propsStats = EntityStatsCapability.get(player);
        props.addUnlockedAbility(CursedEnergyContinuousPunchAbility.INSTANCE);
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(props));
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));

        return true;
    }
}
