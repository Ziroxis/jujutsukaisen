package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.items.spawneggs.*;
import com.example.jujutsukaisen.items.weapons.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public List<Supplier<Item>> items = new ArrayList<>();

    //SPAWN EGGS
    public static final RegistryObject<Item> ROPPONGI_EGG = ITEMS.register("roppongi_egg", RoppongiEgg::new);
    public static final RegistryObject<Item> SMALLPOX_EGG = ITEMS.register("smallpox_egg", SmallPoxEgg::new);
    public static final RegistryObject<Item> LIZARD_EGG = ITEMS.register("lizard_egg", LizardEgg::new);
    public static final RegistryObject<Item> PUNCH_SENSEI_EGG = ITEMS.register("punch_sensei_egg", PunchSenseiEgg::new);
    public static final RegistryObject<Item> GRASS_EGG = ITEMS.register("grass_egg", GrassHopperEgg::new);
    public static final RegistryObject<Item> PUPPET_EGG = ITEMS.register("puppet_egg", PossesedPuppetEgg::new);
    public static final RegistryObject<Item> SWORD_SENSEI_EGG = ITEMS.register("sword_sensei_egg", SwordSenseiEgg::new);



    //WEAPONS
    public static final RegistryObject<Item> BLACK_BLADE = ITEMS.register("black_blade", () -> new BlackBlade(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1), 8, 0f));
    public static final RegistryObject<Item> CURSED_HAMMER = ITEMS.register("cursed_hammer", () -> new CursedHammer(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1), 4, 0f));
    public static final RegistryObject<Item> CURSED_SPEAR = ITEMS.register("cursed_spear", () -> new CursedSpear(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1), 4, 0f));
    public static final RegistryObject<Item> DEMON_SLAUGHTERER = ITEMS.register("demon_slaughterer", () -> new DemonSlaughterer(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1), 5, 0f));
    public static final RegistryObject<Item> NANAMI_BLADE = ITEMS.register("nanami_blade", () -> new NanamiBlade(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1), 6, 0f));
    public static final RegistryObject<Item> BLOOD_EDGE = ITEMS.register("blood_edge", () -> new BloodEdge(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1), 5, 1.9f));
}
