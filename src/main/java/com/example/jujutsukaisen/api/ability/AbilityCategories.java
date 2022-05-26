package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IExtensibleEnum;

import javax.annotation.Nullable;
import java.util.function.Function;

public class AbilityCategories {


    private static final Function<PlayerEntity, ResourceLocation> GET_TECHNIQUE_ICON = (player) ->
    {
        IEntityStats props = EntityStatsCapability.get(player);
        String iconName = props.getTechnique();
        ResourceLocation icon = null;
        icon = new ResourceLocation(Main.MODID, "textures/technique/" + Beapi.getResourceName(iconName) + ".png");

        return icon;
    };

    private static final Function<PlayerEntity, ResourceLocation> GET_REVERSED_ICON = (player) ->
    {
        IEntityStats props = EntityStatsCapability.get(player);
        ResourceLocation icon = null;
        icon = new ResourceLocation(Main.MODID, "textures/technique/reversed_energy.png");

        return icon;
    };

    private static final Function<PlayerEntity, ResourceLocation> GET_BASIC_ICON = (player) ->
    {
        IEntityStats props = EntityStatsCapability.get(player);
        String iconName = props.getClan();
        ResourceLocation icon = null;
        icon = new ResourceLocation(Main.MODID, "textures/abilities/basic_ability.png");

        return icon;
    };

    //TODO make this actually usable
    private static final Function<PlayerEntity, ResourceLocation> GET_CURSED_ICON = (player) ->
    {
        IEntityStats props = EntityStatsCapability.get(player);
        String iconName = props.getCurse();
        ResourceLocation icon = null;
        icon = new ResourceLocation(Main.MODID, "textures/cursed/" + Beapi.getResourceName(iconName) + ".png");

        return icon;
    };



    public static enum AbilityCategory implements IExtensibleEnum
    {
        ALL,
        REVERSED(GET_REVERSED_ICON),
        TECHNIQUE(GET_TECHNIQUE_ICON),
        CURSED(GET_CURSED_ICON),
        BASIC(GET_BASIC_ICON);

        private Function<PlayerEntity, ResourceLocation> iconFunction;

        private AbilityCategory()
        {
            this.iconFunction = null;
        }

        private AbilityCategory(Function<PlayerEntity, ResourceLocation> function)
        {
            this.iconFunction = function;
        }

        @Nullable
        public ResourceLocation getIcon(PlayerEntity player)
        {
            if(this.iconFunction == null)
                return null;
            return this.iconFunction.apply(player);
        }

        public static AbilityCategory create(String name, Function<PlayerEntity, ResourceLocation> function)
        {
            throw new IllegalStateException("Enum not extended");
        }
    }


}
