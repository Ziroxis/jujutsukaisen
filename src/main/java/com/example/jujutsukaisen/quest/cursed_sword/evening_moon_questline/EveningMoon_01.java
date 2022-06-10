package com.example.jujutsukaisen.quest.cursed_sword.evening_moon_questline;

import com.example.jujutsukaisen.abilities.basic.sword.CursedEnergyContinuousSwordAbility;
import com.example.jujutsukaisen.abilities.basic.sword.EveningMoonAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
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

public class EveningMoon_01 extends Quest {

    private static final KillEntityObjective.ICheckKill TARGET_CHECK = (player, target, source) ->
    {
        return target.getType() == ModEntities.ROPPONGI.get();
    };
    private Objective killObjective = new KillEntityObjective("Kill 20 roppongis", 20, TARGET_CHECK.and(SharedKillChecks.HAS_SWORD));

    public EveningMoon_01()
    {
        super("eveningmoon_01", "Get rid of some roppongi with a sword");
        this.addObjective(this.killObjective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        IAbilityData propsAbility = AbilityDataCapability.get(player);
        IEntityStats propsStats = EntityStatsCapability.get(player);
        propsStats.alterExperience(250);
        ExperienceUpEvent eventExperience = new ExperienceUpEvent(player, 250);
        MinecraftForge.EVENT_BUS.post(eventExperience);
        propsAbility.addUnlockedAbility(EveningMoonAbility.INSTANCE);
        PacketHandler.sendToServer(new CSyncAbilityDataPacket(propsAbility));
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));
        return true;
    }}
