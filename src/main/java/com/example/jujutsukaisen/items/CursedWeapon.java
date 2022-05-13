package com.example.jujutsukaisen.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class CursedWeapon extends SwordItem {
    public CursedWeapon(IItemTier itemTier, int damage, float attackSpeed, Properties properties) {
        super(itemTier, damage, attackSpeed, properties);
    }

    @Override
    public int getEnchantmentValue()
    {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
