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

public class Api {


    private static final Function<PlayerEntity, ResourceLocation> GET_CLAN_ICON = (player) ->
    {
        IEntityStats props = EntityStatsCapability.get(player);
        String iconName = props.getClan();
        ResourceLocation icon = null;
        icon = new ResourceLocation(Main.MODID, "textures/clan/" + Beapi.getResourceName(iconName) + ".png");

        return icon;
    };





    public static enum AbilityCategory implements IExtensibleEnum
    {
        ALL,
        TECHNIQUE(GET_CLAN_ICON);

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
