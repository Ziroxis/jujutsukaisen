package com.example.jujutsukaisen.api.ability.sorts;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.api.ability.AbilityUseEvent;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SUpdateEquippedAbilityPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;

import java.io.Serializable;

/**
 * Class made for abilities that are active (for a time)
 */
public class ContinuousAbility extends Ability {

    private int threshold = 0;
    protected int continueTime = 0;

    // Setting the defaults so that no crash occurs and so they will be null safe.
    protected IOnStartContinuity onStartContinuityEvent = (player) -> { return true; };
    protected IOnEndContinuity onEndContinuityEvent = (player) -> { return true; };
    protected IDuringContinuity duringContinuityEvent = (player, continuousTime) -> {};

    public ContinuousAbility(String name, AbilityCategories.AbilityCategory category)
    {
        super(name, category);
    }


    /*
     *  Event Starters
     */
    @Override
    public void use(PlayerEntity player)
    {
        if(player.level.isClientSide)
            return;

        if (this.isOnCooldown() && this.getCooldown() <= 10)
            this.stopCooldown(player);

        AbilityUseEvent event = new AbilityUseEvent(player, this);
        if (MinecraftForge.EVENT_BUS.post(event))
            return;

        if(!this.isContinuous())
        {
            if(!this.isOnStandby())
                return;

            if (this.onStartContinuityEvent.onStartContinuity(player))
            {
                this.checkAbilityPool(player, State.CONTINUOUS);


                this.startContinuity(player);
                PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, this), player);
            }
        }
        else
        {
            if (!this.isStateForced() && this.onEndContinuityEvent.onEndContinuity(player))
            {
                this.checkAbilityPool(player, State.COOLDOWN);
                this.stopContinuity(player);
            }
        }
    }



    /*
     * 	Setters/Getters
     */
    public void setThreshold(int threshold)
    {
        this.threshold = threshold * 20;
    }

    public void setThresholdInTicks(int threshold)
    {
        this.threshold = threshold;
    }

    public int getThreshold()
    {
        return this.threshold;
    }

    public void setContinueTime(int time)
    {
        this.continueTime = time * 20;;
    }

    public int getContinueTime()
    {
        return this.continueTime;
    }



    /*
     * 	Methods
     */
    public void tick(PlayerEntity player)
    {
        //if(player.level.isClientSide)
        //	return;
        IEntityStats propsEntity = EntityStatsCapability.get(player);
        if(!this.canUse(player))
        {
            this.stopContinuity(player);
            return;
        }

        player.level.getProfiler().push(Beapi.getResourceName(this.getName()));

        if(this.isContinuous())
        {
            if (player.tickCount % 20 == 0)
            {
                if (propsEntity.getLevel() < getExperienceGainLevelCap())
                {
                    propsEntity.alterExperience(getExperiencePoint());

                    ExperienceUpEvent eventExperience = new ExperienceUpEvent(player, getExperiencePoint());
                    MinecraftForge.EVENT_BUS.post(eventExperience);
                }
                propsEntity.alterCursedEnergy((int) -getCursedEnergyCost());
            }
            PacketHandler.sendTo(new CursedEnergySync(propsEntity.returnCursedEnergy()), player);
            PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), propsEntity), player);
            this.continueTime++;
            if((this.isClientSide() || !player.level.isClientSide) && !this.isStateForced())
                this.duringContinuityEvent.duringContinuity(player, this.continueTime);

            if(this.threshold > 0 && this.continueTime >= this.threshold)
                this.stopContinuity(player);
        }

        player.level.getProfiler().pop();
    }

    public void startContinuity(PlayerEntity player)
    {
        this.setState(State.CONTINUOUS);
    }

    public void stopContinuity(PlayerEntity player)
    {
        if(player.level.isClientSide)
            return;

        if(this.onEndContinuityEvent.onEndContinuity(player))
        {
            this.continueTime = 0;
            this.startCooldown(player);
            PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, this), player);
        }
    }

    /*
     *	Interfaces
     */
    public interface IDuringContinuity extends Serializable
    {
        void duringContinuity(PlayerEntity player, int passiveTime);
    }

    public interface IOnStartContinuity extends Serializable
    {
        boolean onStartContinuity(PlayerEntity player);
    }

    public interface IOnEndContinuity extends Serializable
    {
        boolean onEndContinuity(PlayerEntity player);
    }
}
