package com.example.jujutsukaisen.items.weapons;

import com.example.jujutsukaisen.init.ModTiers;
import com.example.jujutsukaisen.items.CursedWeapon;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class CursedSpear extends CursedWeapon {


    public CursedSpear(Properties properties, int damage, float speed) {
        super(ModTiers.WEAPON, damage, speed, properties);
    }

}
