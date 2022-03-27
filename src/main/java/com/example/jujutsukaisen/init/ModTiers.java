package com.example.jujutsukaisen.init;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum ModTiers implements IItemTier {


    WEAPON(1000, 8, 0, 1, 20, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    SWORDMAGIC(0, 8, 0, 1, 20, () -> {
        return Ingredient.of(ItemStack.EMPTY);
    }),
    MAGICWEAPON(0, 8, 0, 1, 20, () -> {
        return Ingredient.of(ItemStack.EMPTY);
    });


    private int maxUses;
    private float efficiency;
    private float attackDamage;
    private int harvestLevel;
    private int enchantability;
    private final LazyValue<Ingredient> repairIngredient;

    private ModTiers(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Ingredient> ingredient) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairIngredient = new LazyValue<>(ingredient);
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}
