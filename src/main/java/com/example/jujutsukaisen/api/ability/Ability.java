package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.sorts.ChargeableAbility;
import com.example.jujutsukaisen.api.ability.sorts.ContinuousAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.world.ExtendedWorldData;
import com.example.jujutsukaisen.events.leveling.ExperienceUpEvent;
import com.example.jujutsukaisen.networking.CursedEnergySync;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.server.SSyncEntityStatsPacket;
import com.example.jujutsukaisen.networking.server.ability.SUpdateEquippedAbilityPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Random;

/**
 * Base class for all abilities, with most of the information every ability needs.
 */
public class Ability extends ForgeRegistryEntry<Ability> {

    private String name = "";
    private String displayName;
    private String textureName = "";
    private int cursedEnergyCost = 1;
    private int experiencePoint = 0;
    private ITextComponent tooltip;
    protected double cooldown;
    protected double maxCooldown;
    protected double disableTicks;
    protected double maxDisableTicks = 200;
    protected  Ability.ICheck check;
    private AbilityCategories.AbilityCategory category = AbilityCategories.AbilityCategory.ALL;
    private AbilityUnlock unlock = AbilityUnlock.PROGRESSION;
    private State state = State.STANDBY;
    private State previousState = State.STANDBY;
    private boolean hideInGUI = false;
    private boolean forcedState = false;
    @Deprecated
    private boolean needsClientSide = false;
    private int poolId = -1;

    protected final Random random = new Random();

    // Setting the defaults so that no crash occurs and so they will be null safe.
    protected IOnUse onUseEvent = (player) -> { return true; };
    protected IDuringCooldown duringCooldownEvent = (player, cooldown) -> {};
    protected IOnEndCooldown onEndCooldownEvent = (player) -> {};

    public Ability(String name, AbilityCategories.AbilityCategory category)
    {
        this.name = name;
        this.category = category;
    }

    /*
     * Event Starters
     */

    public void use(PlayerEntity player) //Is called when player uses any ability
    {
        if (player.level.isClientSide)
            return;


        player.level.getProfiler().push(() ->
        {
            return Beapi.getResourceName(this.getName());
        });

        if (this.isOnCooldown() && this.getCooldown() <= 10)
            this.stopCooldown(player);

        if (!this.isOnStandby())
            return;

        AbilityUseEvent event = new AbilityUseEvent(player, this);
        if (MinecraftForge.EVENT_BUS.post(event))
            return;

        if (!this.isStateForced() && this.onUseEvent.onUse(player))
        {
            IAbilityData props = AbilityDataCapability.get(player);
            this.checkAbilityPool(player, State.COOLDOWN);

            IEntityStats propsEntity = EntityStatsCapability.get(player);
            propsEntity.alterCursedEnergy(-cursedEnergyCost);
            propsEntity.alterExperience(experiencePoint);
            PacketHandler.sendTo(new CursedEnergySync(propsEntity.returnCursedEnergy()), player);
            PacketHandler.sendTo(new SSyncEntityStatsPacket(player.getId(), propsEntity), player);
            ExperienceUpEvent eventExperience = new ExperienceUpEvent(player, experiencePoint);
            MinecraftForge.EVENT_BUS.post(eventExperience);


            this.startCooldown(player);
            props.setPreviouslyUsedAbility(this);
            PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, this), player);
        }
        player.level.getProfiler().pop();
    }

    /*
     * Setters/Getters
     */

    public boolean isOnStandby()
    {
        return this.state == State.STANDBY;
    }

    public boolean isOnCooldown()
    {
        return this.state == State.COOLDOWN;
    }

    public boolean isPassiveEnabled()
    {
        return this.state == State.PASSIVE;
    }

    public boolean isContinuous()
    {
        return this.state == State.CONTINUOUS && !this.isStateForced();
    }

    public boolean isCharging()
    {
        return this.state == State.CHARGING && !this.isStateForced();
    }

    public boolean isDisabled()
    {
        return this.state == State.DISABLED;
    }

    public void startStandby()
    {
        this.previousState = this.state;
        this.state = State.STANDBY;
    }

    public void startDisable()
    {
        this.startDisable(20);
    }

    public void startDisable(int ticks)
    {
        this.previousState = this.state;
        this.state = State.DISABLED;
        this.maxDisableTicks = ticks;
        this.disableTicks = this.maxDisableTicks;
    }

    public double getDisableTicks()
    {
        return this.disableTicks;
    }

    public void setDisableTicks(double ticks)
    {
        this.disableTicks = ticks;
    }

    public void startCooldown(PlayerEntity player)
    {
        this.previousState = this.state;
        this.state = State.COOLDOWN;
    }

    public void stopCooldown(PlayerEntity player)
    {
        if (player.level.isClientSide)
            return;

        this.checkAbilityPool(player, State.STANDBY);

        this.cooldown = this.maxCooldown;
        this.previousState = this.state;
        this.state = State.STANDBY;
        if(!this.isStateForced())
        {
            this.onEndCooldownEvent.onEndCooldown(player);
            PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, this), player);
        }
        this.setForcedState(false);
    }

    public void setState(State state)
    {
        this.previousState = this.state;
        this.state = state;
    }

    public State getState()
    {
        return this.state;
    }

    public State getPreviousState()
    {
        return this.previousState;
    }

    public void setForcedState(boolean flag)
    {
        this.forcedState = flag;
    }

    public boolean isStateForced()
    {
        return this.forcedState;
    }

    public void hideInGUI(boolean flag)
    {
        this.hideInGUI = flag;
    }

    public boolean isHideInGUI()
    {
        return this.hideInGUI;
    }

    public void needsClientSide()
    {
        this.needsClientSide = true;
    }

    public boolean isClientSide()
    {
        return this.needsClientSide;
    }

    public void setInPool(AbilityPool pool)
    {
        this.setInPool(pool.getId());
    }

    public void setInPool(int poolId)
    {
        this.poolId = poolId;
    }

    public int getPoolId()
    {
        return this.poolId;
    }

    public boolean isInPool()
    {
        return false;
    }

    /**
     *
     * @param cooldown - seconds before the ability can be used again
     */
    public void setMaxCooldown(double cooldown)
    {
        this.maxCooldown = cooldown * 20;
        this.cooldown = this.maxCooldown;
    }

    public double getMaxCooldown()
    {
        return this.maxCooldown;
    }

    public void setCooldown(double cooldown)
    {
        this.cooldown = cooldown * 20;
    }

    public double getCooldown()
    {
        return this.cooldown;
    }

    public void setExperiencePoint(int experiencePoint)
    {
        this.experiencePoint = experiencePoint;
    }

    public int getExperiencePoint()
    {
        return this.experiencePoint;
    }


    public void setCursedEnergyCost(int cursedEnergy)
    {
        this.cursedEnergyCost = cursedEnergy;
    }

    public float getCursedEnergyCost()
    {
        return this.cursedEnergyCost;
    }

    public void setDescription(String desc)
    {
        this.tooltip = new StringTextComponent(desc);
    }

    public void setDescription(ITextComponent tooltip)
    {
        this.tooltip = tooltip;
    }

    public ITextComponent getDescription()
    {
        return this.tooltip;
    }

    public String getName()
    {
        return this.name;
    }

    public String getI18nKey()
    {
        String resourceName = Beapi.getResourceName(this.getName());
        return "ability." + Main.MODID + "." + resourceName;
    }

    public String getDisplayName()
    {
        return !Beapi.isNullOrEmpty(this.displayName) ? this.displayName : this.getName();
    }

    public void setDisplayName(String name)
    {
        this.displayName = name;
    }

    public boolean hasCustomTexture()
    {
        return !Beapi.isNullOrEmpty(this.textureName);
    }

    public String getCustomTexture()
    {
        return this.textureName;
    }

    public void setCustomTexture(String texture)
    {
        this.textureName = Beapi.getResourceName(texture);
    }

    public AbilityCategories.AbilityCategory getCategory()
    {
        return this.category;
    }

    public void setUnlockType(AbilityUnlock unlockType)
    {
        this.unlock = unlockType;
    }

    public AbilityUnlock getUnlockType()
    {
        return this.unlock;
    }

    /*
     * Methods
     */

    public void disableTick(PlayerEntity player)
    {
        if(this.isDisabled() && this.disableTicks > 0)
        {
            this.disableTicks--;
        }
    }

    public void cooldown(PlayerEntity player)
    {
        // if(player.level.isClientSide)
        // return;
        player.level.getProfiler().push(() ->
        {
            return Beapi.getResourceName(this.getName());
        });

        if (this.isOnCooldown() && this.cooldown > 0)
        {
            this.cooldown--;
            if (!player.level.isClientSide && this.getPreviousState() != State.DISABLED && !this.isStateForced())
                this.duringCooldownEvent.duringCooldown(player, (int) this.cooldown);
        }
        else if (this.isOnCooldown() && this.cooldown <= 0)
        {
            this.stopCooldown(player);
        }

        player.level.getProfiler().pop();
    }

    public void checkAbilityPool(PlayerEntity player, State state)
    {
        IAbilityData props = AbilityDataCapability.get(player);
        if(this.isInPool() && !this.isStateForced())
        {
            for(Ability abl : props.getEquippedAbilities())
            {
                if(abl != null && abl != this && abl.isInPool() && abl.getPoolId() == this.getPoolId())
                {
                    double[] values = null;
                    boolean forced = true;

                    if(state == State.COOLDOWN)
                        values = new double[] { this.getCooldown() / 20, this.getCooldown() / 20 };
                    else if(state == State.CHARGING && abl instanceof ChargeableAbility && this instanceof ChargeableAbility)
                        values = new double[] { ((ChargeableAbility)this).getChargeTime() / 20, ((ChargeableAbility)this).getMaxChargeTime() / 20 };
                    else if(state == State.CONTINUOUS && abl instanceof ContinuousAbility && this instanceof ContinuousAbility)
                        values = new double[] { ((ContinuousAbility)this).getContinueTime() / 20, ((ContinuousAbility)this).getThreshold() / 20 };
                    else if(state == State.STANDBY)
                        forced = false;

                    abl.previousState = this.getState();
                    abl.state = state;

                    abl.forcedState = forced;

                    if(values != null)
                        PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, abl, state, values), player);
                    else
                        PacketHandler.sendToAllTrackingAndSelf(new SUpdateEquippedAbilityPacket(player, abl), player);
                }
            }
        }
    }

    /**
     * @return % of remaining cooldown between 0-100, going towards 0% from max cooldown(100%)
     */
    public double getCooldownPercentage()
    {
        return (this.cooldown / this.maxCooldown) * 100;
    }

    /**
     * @return % of passed cooldown between 0-100, going towards 100% from 0%
     */
    public double getInvertedCooldownPercentage()
    {
        return (1 - (this.cooldown / this.maxCooldown)) * 100;
    }

    @Override
    public boolean equals(Object abl)
    {
        if (!(abl instanceof Ability))
            return false;

        return this.getName().equalsIgnoreCase(((Ability) abl).getName());
    }

    @Nullable
    public Ability create()
    {
        try
        {
            return this.getClass().getConstructor().newInstance();
        }
        catch (Exception ex)
        {
            System.out.println("Exception raised for " + this.getDisplayName());
            ex.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static Ability get(ResourceLocation res)
    {
        Ability ability = GameRegistry.findRegistry(Ability.class).getValue(res);
        return ability;
    }

    public boolean canUse(PlayerEntity player)
    {
        ExtendedWorldData worldData = ExtendedWorldData.get(player.level);
        IEntityStats propsEntity = EntityStatsCapability.get(player);

        if (propsEntity.returnCursedEnergy() < cursedEnergyCost)
        {
            player.sendMessage(new TranslationTextComponent("Not enough cursed energy!"), Util.NIL_UUID);

            return false;
        }
        if (worldData.isInsideRestrictedArea((int)player.position().x(), (int)player.position().y(), (int)player.position().z()))
        {
            boolean isWhitelsited = false;
            if(isWhitelsited)
                return true;

            player.sendMessage(new TranslationTextComponent("Can't use here"), Util.NIL_UUID);
            return false;
        }

        return true;
    }

    /*
     * Enums
     */

    public enum State
    {
        STANDBY, DISABLED,

        COOLDOWN, PASSIVE, CONTINUOUS, CHARGING
    }

    /*
     * Interfaces
     */

    public interface ICheck {
        boolean check(PlayerEntity player);
    }


    public interface IOnUse extends Serializable
    {
        boolean onUse(PlayerEntity player);
    }

    public interface IDuringCooldown extends Serializable
    {
        void duringCooldown(PlayerEntity player, int cooldown);
    }

    public interface IOnEndCooldown extends Serializable
    {
        void onEndCooldown(PlayerEntity player);
    }

}
