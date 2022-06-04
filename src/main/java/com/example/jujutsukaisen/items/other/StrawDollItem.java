package com.example.jujutsukaisen.items.other;

import com.example.jujutsukaisen.items.CursedItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.UUID;

public class StrawDollItem extends CursedItem {

    private final static DamageSource DAMAGE_SOURCE = (new DamageSource("magic")).setMagic().bypassArmor().bypassInvul().bypassMagic();


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        if (world.isClientSide)
            return ActionResult.success(itemstack);

        ItemStack itemStack =player.getItemInHand(hand);

        LivingEntity owner = this.getOwner(world, player.blockPosition(), itemStack);
        if(owner == null)
        {
            player.inventory.removeItem(itemStack);
            return ActionResult.success(itemStack);
        }

        if (itemStack.getOrCreateTag() != null)
        {
            if (owner == player)
            {
                player.inventory.removeItem(itemStack);
            }
            else
            {
                owner.hurt(DAMAGE_SOURCE, 5);
                owner.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 250, 1));
                owner.addEffect(new EffectInstance(Effects.CONFUSION, 250, 1));
                if (owner.getHealth() <= 0)
                    player.inventory.removeItem(itemStack);
            }
        }

        return ActionResult.success(itemstack);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack itemStack, ItemEntity entityItem)
    {
        if(entityItem.level.isClientSide)
            return false;

        if (itemStack.getTag() != null)
        {
            LivingEntity target = this.getOwner(entityItem.level, entityItem.blockPosition(), itemStack);
            if(target != null)
            {
                boolean isBurning = entityItem.isOnFire();
                boolean isInVoid = entityItem.blockPosition().getY() < -1;

                if (isBurning || isInVoid)
                {
                    Iterator<ItemStack> iter = target.getHandSlots().iterator();
                    while(iter.hasNext())
                    {
                        ItemStack stack = iter.next();
                        if(stack.getItem() == Items.TOTEM_OF_UNDYING)
                            stack.shrink(stack.getCount());
                    }
                    target.hurt(DAMAGE_SOURCE, target.getMaxHealth() + target.getAbsorptionAmount() + 1);
                }
            }
        }

        return false;
    }


    @Nullable
    public LivingEntity getOwner(World world, BlockPos pos, ItemStack itemStack)
    {
        UUID uuid = itemStack.getOrCreateTag().getUUID("ownerUUID");
        return (LivingEntity) ((ServerWorld)world).getEntity(uuid);
    }

    public void setDollOwner(ItemStack itemStack, LivingEntity owner)
    {
        itemStack.setTag(new CompoundNBT());
        itemStack.getTag().putUUID("ownerUUID", owner.getUUID());
    }
}
