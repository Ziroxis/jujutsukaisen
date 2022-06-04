package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.api.ItemsHelper;
import net.minecraft.item.ItemStack;

public class SharedKillChecks
{
    public static final KillEntityObjective.ICheckKill EXISTS = ((player, target, source) ->
    {
       return player.isAlive();
    });

    public static final KillEntityObjective.ICheckKill HAS_SWORD = (player, target, source) ->
    {
        ItemStack heldItem = player.getMainHandItem();
        return ItemsHelper.isSword(heldItem);
    };

    public static final KillEntityObjective.ICheckKill HAS_EMPTY_HAND = (player, target, source) ->
    {
        ItemStack heldItem = player.getMainHandItem();
        return heldItem.isEmpty();
    };
}
