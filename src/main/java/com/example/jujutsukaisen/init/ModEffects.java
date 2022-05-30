package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.effects.CursedBudEffect;
import com.example.jujutsukaisen.effects.MovementBlockedEffect;
import com.example.jujutsukaisen.effects.RegenerationEffect;
import com.example.jujutsukaisen.effects.RootEffect;
import com.example.jujutsukaisen.effects.sleep.SleepEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Main.MODID);

    public static final RegistryObject<Effect> MOVEMENT_BLOCKED = EFFECTS.register("movement_blocked", MovementBlockedEffect::new);
    public static final RegistryObject<Effect> SLEEP = EFFECTS.register("sleep", SleepEffect::new);
    public static final RegistryObject<Effect> REGENERATION = EFFECTS.register("regeneration", RegenerationEffect::new);
    public static final RegistryObject<Effect> CURSED_BUD = EFFECTS.register("cursed_bud", CursedBudEffect::new);
    public static final RegistryObject<Effect> ROOT = EFFECTS.register("root", RootEffect::new);


}
