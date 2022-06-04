package com.example.jujutsukaisen.quest.cursed_sword.continuous_sword_questline;

import com.example.jujutsukaisen.abilities.basic.sword.CursedEnergyContinuousSwordAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.ObtainItemObjective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

public class ContinuousSword_02 extends Quest {

    private Objective objective = new ObtainItemObjective("Prove your kill", 1, Items.BONE);

    public ContinuousSword_02()
    {
        super("cursedsword_02", "Proving your worth by bone and sword");
        this.addObjective(this.objective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        IAbilityData props = AbilityDataCapability.get(player);
        IEntityStats propsStats = EntityStatsCapability.get(player);
        //TODO doesn't give xp fix it
        propsStats.alterExperience(100);
        props.addUnlockedAbility(CursedEnergyContinuousSwordAbility.INSTANCE);
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(props));
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));
        PacketHandler.sendTo(new SSyncEntityStatsPacket(), player);

        return true;
    }
}
