package com.example.jujutsukaisen.entities.npc;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.SSyncQuestDataPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

//TODO have to finish this shit
public class GradeCheckerEntity extends Quester {
    public GradeCheckerEntity(EntityType type, World world)
    {
        super(type, world);
    }

    boolean acceptanceGradeChecker = false;
    boolean talkedBefore = false;
    int tickCountOne = 0;
    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand)
    {
        if (hand != Hand.MAIN_HAND)
            return ActionResultType.PASS;
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if (player.level.isClientSide)
            return ActionResultType.PASS;

        if (!talkedBefore)
        {
            player.sendMessage(new StringTextComponent("Hey kid, I'm the dude that checks the grades"), player.getUUID());
            player.sendMessage(new StringTextComponent("If you wanna get a curse grade update come to me! Just so you know"), player.getUUID());
            talkedBefore = true;
            return ActionResultType.PASS;
        }

        if (statsProps.getCurseGrade().equals(ModValues.NONE))
        {
            player.sendMessage(new StringTextComponent("For now I'll give ya grade 4, alright?"), player.getUUID());
            statsProps.setCurseGrade(ModValues.grade_4);
            PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), statsProps), player);
            return ActionResultType.PASS;
        }
        if (talkedBefore && ((this.tickCount - tickCountOne) >= 24000))
        {
            player.sendMessage(new StringTextComponent("Lemme check your grade real quick."), player.getUUID());
            int level = statsProps.getLevel();
            int rng = Beapi.RNG(2);
            if (rng == 0)
            {
                player.sendMessage(new StringTextComponent("I'm sorry but you're just... Not cut for a higher grade yet."), player.getUUID());

            }
            if (level < 5 && level > 0)
            {
                player.sendMessage(new StringTextComponent("For now I'll give ya grade 3, alright?"), player.getUUID());
                statsProps.setCurseGrade(ModValues.grade_3);
            }
            if (level < 15 && level > 5)
            {
                player.sendMessage(new StringTextComponent("For now I'll give ya grade 2, alright?"), player.getUUID());
                statsProps.setCurseGrade(ModValues.grade_2);

            }
            if (level < 40 && level > 15)
            {
                player.sendMessage(new StringTextComponent("For now I'll give ya grade 1, alright?"), player.getUUID());
                statsProps.setCurseGrade(ModValues.grade_1);

            }
            if (level < 60 && level > 40)
            {
                player.sendMessage(new StringTextComponent("For now I'll give ya semi special grade, alright?"), player.getUUID());
                statsProps.setCurseGrade(ModValues.semi_special_grade);

            }
            if (level <= 70)
            {
                player.sendMessage(new StringTextComponent("For now I'll give ya special grade, alright?"), player.getUUID());
                statsProps.setCurseGrade(ModValues.special_grade);
            }
            tickCountOne = this.tickCount;
        }
        else
            player.sendMessage(new StringTextComponent("I just rated you... Come back tomorrow ay?"), player.getUUID());



        PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), statsProps), player);
        return ActionResultType.PASS;
    }
}
