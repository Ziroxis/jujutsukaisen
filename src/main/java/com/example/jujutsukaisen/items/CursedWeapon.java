package com.example.jujutsukaisen.items;

import com.example.jujutsukaisen.abilities.basic.sword.CursedEnergyContinuousSwordAbility;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.entities.CurseEntity;
import com.example.jujutsukaisen.init.ModDamageSource;
import com.example.jujutsukaisen.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.lwjgl.system.CallbackI;

public abstract class CursedWeapon extends SwordItem {
    public CursedWeapon(IItemTier itemTier, int damage, float attackSpeed, Properties properties) {
        super(itemTier, damage, attackSpeed, properties);
    }

    /*
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker)
    {
        if (target instanceof CurseEntity && attacker instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) attacker;
            System.out.println(player);
            System.out.println(target);
            target.invulnerableTime = 0;
            float damage = this.getDamage();
            ModDamageSource source = ModDamageSource.causeAbilityDamage(player, new Ability("Cursed weapon use", AbilityCategories.AbilityCategory.ALL)).getSource();
            target.hurt(source, damage);
        }
        return super.hurtEnemy(itemStack, target, attacker);
    }
     */

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
