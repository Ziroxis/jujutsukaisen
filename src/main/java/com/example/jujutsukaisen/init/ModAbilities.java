package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.abilities.basic.BattoSwordAbility;
import com.example.jujutsukaisen.abilities.basic.CursedEnergyContinuousPunchAbility;
import com.example.jujutsukaisen.abilities.blood_manipulation.*;
import com.example.jujutsukaisen.abilities.cursed_speech.*;
import com.example.jujutsukaisen.abilities.projection_sorcery.*;
import com.example.jujutsukaisen.abilities.tenshadow_technique.DivineDogsAbility;
import com.example.jujutsukaisen.abilities.tenshadow_technique.ShadowInventoryAbility;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

import java.util.Arrays;
import java.util.Objects;

public class ModAbilities {

    public static <T extends IForgeRegistryEntry<T>> void make(ResourceLocation name, Class<T> type)
    {
        new RegistryBuilder<T>().setName(name).setType(type).setMaxID(Integer.MAX_VALUE - 1).create();
    }
    static
    {
        make(new ResourceLocation(Main.MODID, "abilities"), Ability.class);
    }

    public static final IForgeRegistry<Ability> ABILITIES_REGISTRY = RegistryManager.ACTIVE.getRegistry(Ability.class);
    private static final DeferredRegister<Ability> ABILITIES = DeferredRegister.create(ABILITIES_REGISTRY, Main.MODID);

    public static final Ability[] CURSED_SPEECH = new Ability[] {StopAbility.INSTANCE, SleepAbility.INSTANCE, GetTwistedAbility.INSTANCE, FallDownUnderAbility.INSTANCE, ExplodeAbility.INSTANCE, BlastAwayAbility.INSTANCE};
    public static final Ability[] BLOOD_MANIPULATION = new Ability[] {BloodShurikenAbility.INSTANCE, PiercingBloodAbility.INSTANCE, BloodMeteoriteAbility.INSTANCE, BloodEdgeAbility.INSTANCE, FlowingRedScaleAbility.INSTANCE};
    public static final Ability[] PROJECTION_SORCERY = new Ability[] {FrameSpeedAbility.INSTANCE, FrameBreakAbility.INSTANCE, FrameTeleportationAbility.INSTANCE, FrameCatchAbility.INSTANCE, FrameMovementPassive.INSTANCE};
    public static final Ability[] TENSHADOW_TECHNIQUE = new Ability[] {DivineDogsAbility.INSTANCE, ShadowInventoryAbility.INSTANCE};
    public static final Ability[] CURSED_PUNCHES = new Ability[] {CursedEnergyContinuousPunchAbility.INSTANCE};
    public static final Ability[] CURSED_SWORD = new Ability[] {BattoSwordAbility.INSTANCE};

    private static Ability registerAbility(Ability ability)
    {
        String resourceName = Beapi.getResourceName(ability.getName());
        Beapi.getLangMap().put(ability.getI18nKey(), ability.getName());

        final ResourceLocation key = new ResourceLocation(Main.MODID, resourceName);
        RegistryObject<Ability> ret = RegistryObject.of(key, ABILITIES_REGISTRY);
        if(!ABILITIES.getEntries().contains(ret))
            ABILITIES.register(resourceName, () -> ability);

        return ability;
    }

    private static void registerAbilities(Ability[] abilities)
    {
        Arrays.stream(abilities).filter(Objects::nonNull).forEach(ModAbilities::registerAbility);
    }

    public static void register(IEventBus eventBus)
    {
        registerAbilities(CURSED_SPEECH);
        registerAbilities(BLOOD_MANIPULATION);
        registerAbilities(PROJECTION_SORCERY);
        registerAbilities(TENSHADOW_TECHNIQUE);
        registerAbilities(CURSED_PUNCHES);
        registerAbilities(CURSED_SWORD);
        ABILITIES.register(eventBus);
    }
}
