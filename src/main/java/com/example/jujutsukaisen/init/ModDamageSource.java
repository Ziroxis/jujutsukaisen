package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.damagesource.AbilityDamageSource;
import com.example.jujutsukaisen.damagesource.ModEntityDamageSource;
import com.example.jujutsukaisen.damagesource.ModIndirectEntityDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.DamageSource;

public class ModDamageSource extends DamageSource
{
	public static final ModDamageSource FIRE = new ModDamageSource("onFire", false, true, false);
	public static final ModDamageSource LIGHTNING_BOLT = new ModDamageSource("lightningBolt", true, false, false);
	public static final ModDamageSource MAGMA = new ModDamageSource("lava", true, true, false);
	public static final ModDamageSource SPECIAL = new ModDamageSource("special", false, false, false);
	public static final DamageSource DEVILS_CURSE = new DamageSource("devils_curse").bypassMagic().bypassInvul();

    private boolean markSlashDamage = false;
    private boolean isInternal = false;

    public ModDamageSource(String damageTypeIn, boolean bypassArmor, boolean isFireDamage, boolean isExplosive)
    {
        super(damageTypeIn);

        if(bypassArmor)
            this.bypassArmor();
        if(isFireDamage)
            this.setIsFire();
        if(isExplosive)
            this.setExplosion();
    }
    
    public ModDamageSource(String damageType) {
        this(damageType, false, false, false);
    }
    
    public ModDamageSource causeIndirectDamageFromSource(ThrowableEntity entity) {
        ModDamageSource source = new ModIndirectEntityDamageSource(this.getMsgId(), entity, entity.getOwner());
        if(this.isFire())
            source.setIsFire();
        if(this.isExplosion())
            source.setExplosion();
        if(this.isBypassArmor())
            source.bypassArmor();

        return source;
    }

    public ModDamageSource causeEntityDamageFromSource(Entity entity) {
        ModDamageSource source = new ModEntityDamageSource(this.getMsgId(), entity);
        return this.setSourceProprieties(source);
    }

    public boolean isSlashDamage()
    {
    	return this.markSlashDamage;
    }
    
    public ModDamageSource markDamageAsSlash()
    {
        this.markSlashDamage = true;
        return this;
    }
    
    public boolean isInternalDamage()
    {
    	return this.isInternal;
    }
    
    public ModDamageSource setInternalDamage()
    {
    	this.isInternal = true;
    	return this;
    }

    public ModDamageSource getSource() {
        return this.setSourceProprieties(new ModDamageSource(this.getMsgId()));
    }

    private ModDamageSource setSourceProprieties(ModDamageSource s) {
        if(this.isFire())
            s.setIsFire();
        if(this.isExplosion())
            s.setExplosion();
        if(this.isBypassArmor())
            s.bypassArmor();
        return s;
    }

    @Override
    public ModDamageSource setProjectile() {
        return this.setSourceProprieties((ModDamageSource) super.setProjectile());
    }
    
	public static AbilityDamageSource causeAbilityDamage(LivingEntity player, Ability ability)
	{
		return new AbilityDamageSource("ability", player, ability);
	}


    public static AbilityDamageSource causeAbilityDamage(LivingEntity player, Ability ability, String damageType)
    {
        return new AbilityDamageSource(damageType, player, ability);
    }
}