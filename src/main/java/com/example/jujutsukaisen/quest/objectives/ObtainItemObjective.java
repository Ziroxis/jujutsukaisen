package com.example.jujutsukaisen.quest.objectives;

import com.example.jujutsukaisen.data.quest.objectives.IObtainItemObjective;
import com.example.jujutsukaisen.quest.Objective;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class ObtainItemObjective extends Objective implements IObtainItemObjective
{
    private Predicate<ItemStack> check = (itemStack) -> false;

    public ObtainItemObjective(String title, int count, Item itemTarget)
    {
        super(title);
        this.setMaxProgress(count);
        this.check = (itemStack) -> itemStack.getItem() == itemTarget;
    }

    public ObtainItemObjective(String title, int count, Predicate<ItemStack> check)
    {
        super(title);
        this.setMaxProgress(count);
        this.check = check;
    }

    @Override
    public boolean checkItem(ItemStack stack)
    {
        return this.check.test(stack);
    }
}
