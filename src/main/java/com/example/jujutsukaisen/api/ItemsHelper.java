package com.example.jujutsukaisen.api;

import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;

import java.util.Map.Entry;
import java.util.UUID;


public class ItemsHelper
{
	private static String[] wantedPostersBackgrounds = new String[] { "forest1", "forest2", "jungle1", "jungle2", "hills1", "hills2", "hills3", "plains1", "plains2", "plains3", "taiga1", "taiga2", };




	public static boolean isBow(ItemStack itemStack)
	{
		if (itemStack.isEmpty())
			return false;

		if (itemStack.getUseAnimation() == UseAction.BOW)
			return true;

		if (itemStack.getItem() instanceof ShootableItem)
			return true;

		return false;
	}

	public static boolean isSword(ItemStack itemStack)
	{
		return !itemStack.isEmpty() && (itemStack.getItem() instanceof SwordItem);
	}







	public static float getItemDamage(ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> multimap = stack.getAttributeModifiers(EquipmentSlotType.MAINHAND);
		double modifier = EnchantmentHelper.getDamageBonus(stack, CreatureAttribute.UNDEFINED);

		for (Entry<Attribute, AttributeModifier> entry : multimap.entries())
		{
			AttributeModifier attr = entry.getValue();
			if (attr.getId().equals(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"))) // UUID obtained from this Item.ATTACK_DAMAGE_MODIFIER which is protected so I cannot simply reference it here
			{
				double damage = attr.getAmount() + modifier + 1; // Weapon damage modifier + enchantment + player's punch modifier
				return (float) damage;
			}
		}

		return -1;
	}
}
