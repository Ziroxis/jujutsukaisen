package com.example.jujutsukaisen.damagesource;

import com.example.jujutsukaisen.init.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class ModEntityDamageSource extends ModDamageSource
{
    @Nullable
    protected final Entity damageSourceEntity;

    public ModEntityDamageSource(String damageTypeIn, @Nullable Entity damageSourceEntityIn) {
        super(damageTypeIn, false, false, false);
        this.damageSourceEntity = damageSourceEntityIn;
    }

    @Nullable
    public Entity getTrueSource() {
        return this.damageSourceEntity;
    }

    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        ItemStack itemstack = this.damageSourceEntity instanceof LivingEntity ? ((LivingEntity)this.damageSourceEntity).getMainHandItem() : ItemStack.EMPTY;
        String s = "death.attack." + this.msgId;
        return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? new TranslationTextComponent(s + ".item", entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemstack.getDisplayName()) : new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName());
    }

    public boolean isDifficultyScaled() {
        return this.damageSourceEntity != null && this.damageSourceEntity instanceof LivingEntity && !(this.damageSourceEntity instanceof PlayerEntity);
    }

    @Nullable
    public Vector3d getDamageLocation() {
        return this.damageSourceEntity != null ? this.damageSourceEntity.position() : null;
    }
}
