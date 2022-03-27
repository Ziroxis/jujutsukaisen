package com.example.jujutsukaisen.items.weapons;

import com.example.jujutsukaisen.init.ModTiers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class CursedSpear extends SwordItem {


    public CursedSpear(Properties properties, int damage, float speed) {
        super(ModTiers.WEAPON, damage, speed, properties);
    }

    @Override
    public int getEnchantmentValue()
    {
        return 14;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }
}
