package com.example.jujutsukaisen.quest.cursed_sword.batto_sword_questline;

import com.example.jujutsukaisen.abilities.basic.punch.CursedPunchAbility;
import com.example.jujutsukaisen.abilities.basic.sword.BattoSwordAbility;
import com.example.jujutsukaisen.abilities.basic.sword.EveningMoonAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.KillEntityObjective;
import com.example.jujutsukaisen.quest.objectives.SharedKillChecks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class BattoSword_01 extends Quest {

    private static final KillEntityObjective.ICheckKill TARGET_CHECK = (player, target, source) ->
    {
        return target.getType() == EntityType.ZOMBIE;
    };
    private Objective killObjective = new KillEntityObjective("Kill 20 zombies", 20, TARGET_CHECK.and(SharedKillChecks.HAS_SWORD));

    public BattoSword_01()
    {
        super("battosword_01", "Get rid of some zombies WITH a sword this time");
        this.addObjective(this.killObjective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        IAbilityData propsAbility = AbilityDataCapability.get(player);
        propsAbility.addUnlockedAbility(BattoSwordAbility.INSTANCE);
        IEntityStats propsStats = EntityStatsCapability.get(player);
        propsStats.alterExperience(200);
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(propsAbility));
        return true;
    }
}
