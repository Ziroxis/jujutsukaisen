package com.example.jujutsukaisen.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class ModIndirectEntityDamageSource extends ModEntityDamageSource
{
    private final Entity indirectEntity;

    public ModIndirectEntityDamageSource(String damageTypeIn, Entity source, @Nullable Entity indirectEntityIn)
    {
        super(damageTypeIn, source);
        this.indirectEntity = indirectEntityIn;
    }

    @Nullable
    public Entity getImmediateSource() {
        return this.damageSourceEntity;
    }

    /**
     * Retrieves the true causer of the damage, e.g. the player who fired an arrow, the shulker who fired the bullet,
     * etc.
     */
    @Override
	@Nullable
    public Entity getTrueSource() {
        return this.indirectEntity;
    }

    /**
     * Gets the death message that is displayed when the player dies
     */
    @Override
	public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        ITextComponent itextcomponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
        ItemStack itemstack = this.indirectEntity instanceof LivingEntity ? ((LivingEntity)this.indirectEntity).getMainHandItem() : ItemStack.EMPTY;
        String s = "death.attack." + this.msgId;
        String s1 = s + ".item";
        return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? new TranslationTextComponent(s1, entityLivingBaseIn.getDisplayName(), itextcomponent, itemstack.getDisplayName()) : new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(), itextcomponent);
    }
}
