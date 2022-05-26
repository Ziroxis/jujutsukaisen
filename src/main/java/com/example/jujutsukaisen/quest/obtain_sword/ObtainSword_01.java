package com.example.jujutsukaisen.quest.obtain_sword;

import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModEntities;
import com.example.jujutsukaisen.init.ModItems;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CSyncentityStatsPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.example.jujutsukaisen.quest.objectives.KillEntityObjective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ObtainSword_01 extends Quest {
    private static final KillEntityObjective.ICheckKill TARGET_CHECK = (player, target, source) ->
    {
        return target.getType() == EntityType.CREEPER;
    };
    private Objective killObjective = new KillEntityObjective("Kill 5 creepers", 5, TARGET_CHECK);

    public ObtainSword_01()
    {
        super("obtainsword_01", "Get rid of some creepers");
        this.addObjective(this.killObjective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        IEntityStats propsStats = EntityStatsCapability.get(player);
        if (!this.addQuestItem(player, ModItems.DEMON_SLAUGHTERER.get(), 1))
            return false;
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));

        return true;
    }
}
