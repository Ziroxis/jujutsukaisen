package com.example.jujutsukaisen.data.quest.objectives;

import net.minecraft.item.ItemStack;

public interface IObtainItemObjective
{
    boolean checkItem(ItemStack itemStack);
}
