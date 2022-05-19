package com.example.jujutsukaisen.items.weapons;

import com.example.jujutsukaisen.init.ModTiers;
import com.example.jujutsukaisen.items.CursedWeapon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

public class BlackBlade extends CursedWeapon {

    /*
    public BlackBlade(Properties properties, float speed) {
        super(ModTiers.WEAPON, 10, speed, properties);
        int damage = 10;
    }
     */

    public BlackBlade()
    {
        super(ModTiers.WEAPON, 10, 0.5f, new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1));
    }
}
