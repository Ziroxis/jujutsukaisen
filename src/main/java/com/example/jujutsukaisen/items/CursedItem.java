package com.example.jujutsukaisen.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public abstract class CursedItem extends Item {
    public CursedItem() {
        super(new Properties().tab(ItemGroup.TAB_MISC).stacksTo(1));
    }
}
