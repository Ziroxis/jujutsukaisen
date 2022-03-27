package com.example.jujutsukaisen.items.weapons;

import com.example.jujutsukaisen.init.ModTiers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class NanamiBlade extends SwordItem {

    public NanamiBlade(Properties properties, int damage, float speed) {
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
