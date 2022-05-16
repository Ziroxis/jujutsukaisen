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
import com.example.jujutsukaisen.quest.objectives.ObtainItemObjective;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ObtainSword_01 extends Quest {
    private Objective killObjective = new KillEntityObjective("Kill 5 roppongis", 5, ModEntities.ROPPONGI.get());

    public ObtainSword_01()
    {
        super("obtainsword_01", "Show you're a true swordsman cursed spirits killer!");
        this.addObjective(this.killObjective);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player)
    {
        IEntityStats propsStats = EntityStatsCapability.get(player);
        player.addItem(new ItemStack(ModItems.DEMON_SLAUGHTERER.get()));
        PacketHandler.sendToServer(new CSyncentityStatsPacket(propsStats));

        return true;
    }
}
