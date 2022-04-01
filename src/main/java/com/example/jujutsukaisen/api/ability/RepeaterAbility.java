package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.data.world.ExtendedWorldData;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.ability.SUpdateEquippedAbilityPacket;
import net.minecraft.entity.player.PlayerEntity;

public abstract class RepeaterAbility extends ContinuousAbility implements IParallelContinuousAbility {

    private int repeaterCount;
    private int maxRepeaterCount;
    private int repeaterInterval;

    public RepeaterAbility(String name, Api.AbilityCategory category)
    {
        super(name, category);
    }

    /*
     * 	Event Starters
     */
    private void repeater(PlayerEntity player, int passiveTimer)
    {
        if(this.repeaterCount > 0 && passiveTimer % this.repeaterInterval == 0)
        {
            if(this.onUseEvent.onUse(player))
                this.repeaterCount--;
        }
    }

    /*
     * 	Setters/Getters
     */
    public void setMaxRepeaterCount(int count, int interval)
    {
        this.maxRepeaterCount = count;
        this.repeaterCount = this.maxRepeaterCount;
        this.repeaterInterval = interval;
        int threshold = (int) Math.ceil((this.repeaterCount * this.repeaterInterval) / 20.0f);
        this.setThreshold(threshold);
    }

    public void setRepeaterCount(int count)
    {
        this.repeaterCount = count;
    }


    public int getMaxRepeaterCount()
    {
        return this.maxRepeaterCount;
    }

    public int getRepeaterCount(){
        return this.repeaterCount;
    }

    public int getRepeaterInterval()
    {
        return this.repeaterInterval;
    }
    /*
     * 	Methods
     */
    @Override
    public void use(PlayerEntity player)
    {
        super.use(player);
    }

    @Override
    public void tick(PlayerEntity player)
    {
        if(this.isContinuous())
        {
            if(ExtendedWorldData.get(player.level).isInsideRestrictedArea((int)player.getX(), (int)player.getY(), (int)player.getZ()))
            {
                this.stopContinuity(player);
                return;
            }

            this.continueTime++;
            if(!player.level.isClientSide) {
                this.duringContinuityEvent.duringContinuity(player, this.continueTime);
                this.repeater(player, this.continueTime);
            }

            if(this.getThreshold() > 0 && this.continueTime >= this.getThreshold())
                this.stopContinuity(player);
        }
    }

    @Override
    public void stopContinuity(PlayerEntity player)
    {
        if(player.level.isClientSide)
            return;
        if(this.onEndContinuityEvent.onEndContinuity(player))
        {
            this.continueTime = 0;
            this.repeaterCount = this.maxRepeaterCount;
            this.startCooldown(player);
            PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, this), player);
        }
    }

}
