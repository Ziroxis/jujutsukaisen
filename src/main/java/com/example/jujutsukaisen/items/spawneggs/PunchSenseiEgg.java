package com.example.jujutsukaisen.items.spawneggs;

import com.example.jujutsukaisen.entities.npc.PunchSenseiEntity;
import com.example.jujutsukaisen.init.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PunchSenseiEgg extends Item {

    public PunchSenseiEgg()
    {
        super(new Properties().tab(ItemGroup.TAB_MISC).stacksTo(1));
    }

    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext)
    {
        World world = itemUseContext.getLevel();
        if (!(world instanceof ServerWorld)) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = itemUseContext.getItemInHand();
            BlockPos blockpos = itemUseContext.getClickedPos();
            Direction direction = itemUseContext.getClickedFace();
            Item item = itemUseContext.getItemInHand().getItem();
            BlockState blockstate = world.getBlockState(blockpos);

            PunchSenseiEntity entity = ModEntities.PUNCH_SENSEI.get().create(world);
            entity.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
            world.addFreshEntity(entity);
            itemstack.shrink(1);
            return ActionResultType.CONSUME;
        }
    }
}
