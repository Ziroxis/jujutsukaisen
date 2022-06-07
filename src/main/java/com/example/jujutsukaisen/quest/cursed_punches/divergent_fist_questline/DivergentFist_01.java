package com.example.jujutsukaisen.quest.cursed_punches.divergent_fist_questline;

import com.example.jujutsukaisen.abilities.basic.punch.CursedPunchAbility;
import com.example.jujutsukaisen.abilities.basic.punch.DivergentFistAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
import com.example.jujutsukaisen.init.ModEffects;
import com.example.jujutsukaisen.init.ModEntities;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.KillEntityObjective;
import com.example.jujutsukaisen.quest.objectives.SharedKillChecks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;

public class DivergentFist_01 extends Quest {

    private static final KillEntityObjective.ICheckKill TARGET_CHECK = (player, target, source) ->
    {
        return target.getType() == ModEntities.ROPPONGI.get();
    };
    private Objective killObjective = new KillEntityObjective("Kill 20 zombies", 20, TARGET_CHECK.and(SharedKillChecks.HAS_EMPTY_HAND));

    public DivergentFist_01()
    {
        super("divergentfist_01", "Get rid of some roppongis with your bare fist, 20");
        this.addObjective(this.killObjective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        IAbilityData propsAbility = AbilityDataCapability.get(player);
        IEntityStats propsStats = EntityStatsCapability.get(player);
        propsStats.alterExperience(50);
        ExperienceUpEvent eventExperience = new ExperienceUpEvent(player, 50);
        MinecraftForge.EVENT_BUS.post(eventExperience);
        propsAbility.addUnlockedAbility(DivergentFistAbility.INSTANCE);
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(propsAbility));
        return true;
    }
}
