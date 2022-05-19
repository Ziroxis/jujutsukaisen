package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.COpenPlayerScreenPacket;
import com.example.jujutsukaisen.networking.client.ability.CChangeCombatBarPacket;
import com.example.jujutsukaisen.networking.client.ability.CToggleCombatModePacket;
import com.example.jujutsukaisen.networking.client.ability.CUseAbilityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ModKeyBinds {
    public static KeyBinding infoCard, enterCombatMode, nextCombatBar, prevCombatBar, combatSlot1, combatSlot2, combatSlot3, combatSlot4, combatSlot5, combatSlot6, combatSlot7, combatSlot8;
    private static KeyBinding[] keyBindsCombatbar;
    private static final int[] PREVIOUS_INVENTORY_KEYBINDS = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static int[] keyCooldown = new int[8];

    public static void init()
    {
        infoCard = new KeyBinding("Keys.jujutsukaisen.info_card", 40, "Keys.jujutsukaisen.gui");
        ClientRegistry.registerKeyBinding(infoCard);

        enterCombatMode = new KeyBinding("Keys.jujutsukaisen.combat_mode", 41, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(enterCombatMode);

        nextCombatBar = new KeyBinding("Keys.jujutsukaisen.next_bar", 42, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(nextCombatBar);
        prevCombatBar = new KeyBinding("Keys.jujutsukaisen.previous_bar", 43, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(prevCombatBar);

        combatSlot1 = new KeyBinding("Keys.jujutsukaisen.combat_1", GLFW.GLFW_KEY_1, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot1);
        combatSlot2 = new KeyBinding("Keys.jujutsukaisen.combat_2", GLFW.GLFW_KEY_2, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot2);
        combatSlot3 = new KeyBinding("Keys.jujutsukaisen.combat_3", GLFW.GLFW_KEY_3, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot3);
        combatSlot4 = new KeyBinding("Keys.jujutsukaisen.combat_4", GLFW.GLFW_KEY_4, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot4);
        combatSlot5 = new KeyBinding("Keys.jujutsukaisen.combat_5", GLFW.GLFW_KEY_5, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot5);
        combatSlot6 = new KeyBinding("Keys.jujutsukaisen.combat_6", GLFW.GLFW_KEY_6, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot6);
        combatSlot7 = new KeyBinding("Keys.jujutsukaisen.combat_7", GLFW.GLFW_KEY_7, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot7);
        combatSlot8 = new KeyBinding("Keys.jujutsukaisen.combat_8", GLFW.GLFW_KEY_8, "Keys.jujutsukaisen.combat");
        ClientRegistry.registerKeyBinding(combatSlot8);

        keyBindsCombatbar = new KeyBinding[]
                {
                        combatSlot1, combatSlot2, combatSlot3, combatSlot4, combatSlot5, combatSlot6, combatSlot7, combatSlot8
                };
    }

    public static boolean isShiftKeyDown()
    {
        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static boolean isSpaceKeyDown()
    {
        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_SPACE);
    }

    @SubscribeEvent
    public static void onPlayerJoins(EntityJoinWorldEvent event)
    {
        for(int i = 0; i < PREVIOUS_INVENTORY_KEYBINDS.length; i++)
        {
            PREVIOUS_INVENTORY_KEYBINDS[i] = -1;
        }
    }

    @SubscribeEvent
    public static void onPlayerLeaves(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        for(int i = 0; i < Minecraft.getInstance().options.keyHotbarSlots.length; i++)
        {
            KeyBinding kb = Minecraft.getInstance().options.keyHotbarSlots[i];
            if(PREVIOUS_INVENTORY_KEYBINDS[i] != -1)
            {
                kb.setKey(InputMappings.getKey(PREVIOUS_INVENTORY_KEYBINDS[i], 0));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if(event.getEntity() instanceof ClientPlayerEntity && event.getEntityLiving() == Minecraft.getInstance().player)
        {
            for(int i = 0; i < 8; i++)
            {
                if(keyCooldown[i] > 0)
                    --keyCooldown[i];
            }
        }
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseInputEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null || event.getAction() == GLFW.GLFW_RELEASE)
            return;

        ModKeyBinds.checkKeybindings(player);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null)
            return;

        ModKeyBinds.checkKeybindings(player);
    }

    private static void checkKeybindings(PlayerEntity player)
    {
        IAbilityData abilityDataProps = AbilityDataCapability.get(player);
        IEntityStats entityStatsProps = EntityStatsCapability.get(player);

        if(entityStatsProps.isInCombatMode())
        {
            if(nextCombatBar.isDown())
                PacketHandler.sendToServer(new CChangeCombatBarPacket(0));
            else if(prevCombatBar.isDown())
                PacketHandler.sendToServer(new CChangeCombatBarPacket(1));
        }

        if (infoCard.isDown())
        {
            if(Minecraft.getInstance().screen != null)
                return;

            PacketHandler.sendToServer(new COpenPlayerScreenPacket());
        }

        if (enterCombatMode.isDown())
        {
            keybindsAssignment(entityStatsProps);
        }

        int j = keyBindsCombatbar.length;

        for (int i = 0; i < j; i++)
        {
            if (keyBindsCombatbar[i].isDown())
            {
                int k = i + (abilityDataProps.getCombatBarSet() * 8);
                Ability abl = abilityDataProps.getEquippedAbility(k);
                if (entityStatsProps.isInCombatMode() && abl != null)
                {
                    if((!abl.isOnCooldown() || abl.getCooldown() <= 10) && keyCooldown[i] <= 0)
                    {
                        PacketHandler.sendToServer(new CUseAbilityPacket(k));
                        keyCooldown[i] = 5;
                    }
                }
                else
                    player.inventory.selected = i;
            }
        }
    }

    private static void keybindsAssignment(IEntityStats entityStatsProps)
    {
        entityStatsProps.setCombatMode(!entityStatsProps.isInCombatMode());
        if (entityStatsProps.isInCombatMode())
        {
            for(int i = 0; i < Minecraft.getInstance().options.keyHotbarSlots.length; i++)
            {
                KeyBinding kb = Minecraft.getInstance().options.keyHotbarSlots[i];
                PREVIOUS_INVENTORY_KEYBINDS[i] = kb.getKey().getValue();
                kb.setKey(InputMappings.getKey(GLFW.GLFW_KEY_UNKNOWN, 0));
            }

            KeyBinding.resetMapping();
        }
        else
        {
            for(int i = 0; i < Minecraft.getInstance().options.keyHotbarSlots.length; i++)
            {
                KeyBinding kb = Minecraft.getInstance().options.keyHotbarSlots[i];
                if(PREVIOUS_INVENTORY_KEYBINDS[i] == -1)
                    kb.setKey(InputMappings.getKey(kb.getDefaultKey().getValue(), 0));
                else
                    kb.setKey(InputMappings.getKey(PREVIOUS_INVENTORY_KEYBINDS[i], 0));
            }

            KeyBinding.resetMapping();
        }
        PacketHandler.sendToServer(new CToggleCombatModePacket(entityStatsProps.isInCombatMode()));
    }
}
