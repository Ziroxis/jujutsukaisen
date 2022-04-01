package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.ChargeableAbility;
import com.example.jujutsukaisen.api.ability.ContinuousAbility;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModResources;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class CombatModeEvents
{
	//private static final ArrayList<Supplier<Effect>> FOV_EFFECTS = new ArrayList<>(Arrays.<Supplier<Effect>>asList(ModEffects.PARALYSIS, ModEffects.GUARDING, ModEffects.MOVEMENT_BLOCKED, ModEffects.CANDLE_LOCK, () -> Effects.MOVEMENT_SPEED, () -> Effects.MOVEMENT_SLOWDOWN ));

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRenderOverlay(RenderGameOverlayEvent event)
	{
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		IAbilityData abilityDataProps = AbilityDataCapability.get(player);
		IEntityStats entityStatsProps = EntityStatsCapability.get(player);

		ForgeIngameGui.left_height += 1;

		int posX = mc.getWindow().getGuiScaledWidth();
		int posY = mc.getWindow().getGuiScaledHeight();

		if (abilityDataProps == null)
			return;

		if (event.getType() == ElementType.HEALTH)
		{
			event.setCanceled(true);
			double maxHealth = player.getAttribute(Attributes.MAX_HEALTH).getValue();
			double health = player.getHealth();
			int absorptionBonus = MathHelper.ceil(player.getAbsorptionAmount());
			int rgb = Color.RED.getRGB();
			
			if(absorptionBonus > 0)
				rgb = Color.YELLOW.getRGB();
			
			Beapi.drawStringWithBorder(Minecraft.getInstance().font, event.getMatrixStack(), (int) (health + absorptionBonus)+ "", posX / 2 - 27, posY - 39, rgb);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			mc.getTextureManager().bind(Widget.GUI_ICONS_LOCATION);

			for (int i = MathHelper.ceil((maxHealth) / 2.0F) - 1; i >= 0; i--)
			{
				int k = (posX / 2 - 91) + i % 10 * 6;

				GuiUtils.drawTexturedModalRect(event.getMatrixStack(), k, posY - 39, 16, 0, 9, 9, 0);
			}

			for (int i = 0; i < (100 - (((maxHealth - health) / maxHealth)) * 100) / 10; i++)
			{
				int k = (posX / 2 - 91) + i % 10 * 6;

				int u = 36;
				if(absorptionBonus > 0)
					u = 144;
				
				GuiUtils.drawTexturedModalRect(event.getMatrixStack(), k, posY - 39, 16 + u, 9 * 0, 9, 9, 0);
			}
		}

		if(!entityStatsProps.isInCombatMode())
			return;
		
		if (event.getType() == ElementType.HOTBAR)
		{
			/*
			boolean[] visuals = ClientConfig.INSTANCE.getCooldownVisuals();

			boolean hasNumberVisual = visuals[0]; // For Text
			boolean hasColorVisual = visuals[1]; // For Color
			 */
			event.setCanceled(true);
			event.getMatrixStack().pushPose();
			{
				RenderSystem.color4f(1, 1, 1, 1);
				RenderSystem.disableLighting();
				RenderSystem.enableBlend();
				Beapi.drawStringWithBorder(mc.font, event.getMatrixStack(), abilityDataProps.getCombatBarSet() + "", posX / 2 - 110, posY - 14, Beapi.hexToRGB("#FFFFFF").getRGB());
				
				mc.getTextureManager().bind(ModResources.WIDGETS);			
				
				for (int i = 0; i < 8; i++)
				{
					int j = i + (abilityDataProps.getCombatBarSet() * 8);
					Ability abl = abilityDataProps.getEquippedAbility(j);

					if(abl == null)
					{
						GuiUtils.drawTexturedModalRect(event.getMatrixStack(), (posX - 200 + (i * 50)) / 2, posY - 23, 0, 0, 23, 23, 0);
						continue;
					}
					
					String number = "";

					float cooldown = 23 - (float) (((abl.getCooldown() - 10) / abl.getMaxCooldown()) * 23);
					float threshold = 23;
					float charge = 23;

					if(abl.isOnCooldown() && abl.getCooldown() - 10 > 0)
						number = (int) abl.getCooldown() - 10 + " ";

					if(abl instanceof ContinuousAbility)
					{
						ContinuousAbility cAbility = (ContinuousAbility)abl;
						threshold = cAbility.getContinueTime() / (float) cAbility.getThreshold() * 23;
						if(cAbility.getThreshold() > 0 && abl.isContinuous() && cAbility.getContinueTime() > 0)
							number = cAbility.getThreshold() - cAbility.getContinueTime() + " ";
					}
				
					if(abl instanceof ChargeableAbility)
					{
						ChargeableAbility cAbility = (ChargeableAbility)abl;
						charge = cAbility.getChargeTime() / (float) cAbility.getMaxChargeTime() * 23;
						if(abl.isCharging() && cAbility.getChargeTime() > 0)
							number = cAbility.getChargeTime() + " ";
					}

					boolean isContinuous = abl.isContinuous() || (abl.getState() == Ability.State.CONTINUOUS && abl.isStateForced());
					boolean isCharging = abl.isCharging() || (abl.getState() == Ability.State.CHARGING && abl.isStateForced());
					
					// Setting their color based on their state
					if (abl.isOnCooldown() && !abl.isDisabled() && abl.getCooldown() > 10)
						RenderSystem.color4f(1, 0, 0, 1);
					else if (isCharging)
						RenderSystem.color4f(1, 1, 0, 1);
					else if (isContinuous)
						RenderSystem.color4f(0, 0, 1, 1);
					else if (abl.isDisabled())
						RenderSystem.color4f(0, 0, 0, 1);

					// Drawing the slot
					GuiUtils.drawTexturedModalRect(event.getMatrixStack(), (posX - 200 + (i * 50)) / 2, posY - 23, 0, 0, 23, 23, 0);
					// Reverting the color back to avoid future slots being wrongly colored
					RenderSystem.color4f(1, 1, 1, 1);

					// Setting up addition effects based on the ability's state
					if(false)
					{
						if (abl.isDisabled())
						{
							Beapi.drawAbilityIcon("disabled_status", (posX - 192 + (i * 50)) / 2, posY - 19, 3, 16, 16);
							mc.getTextureManager().bind(ModResources.WIDGETS);
						}
						else if(isContinuous)
						{
							if(threshold < Integer.MAX_VALUE)
								GuiUtils.drawTexturedModalRect(event.getMatrixStack(), (posX - 200 + (i * 50)) / 2, posY - 23, 24, 0, 23, (int) threshold, 0);
						}
						else if(isCharging)
						{
							GuiUtils.drawTexturedModalRect(event.getMatrixStack(), (posX - 200 + (i * 50)) / 2, posY - 23, 24, 0, 23, (int) charge, 0);
						}
						else if(abl.isOnCooldown() && !abl.isDisabled())
						{
							if(cooldown < Integer.MAX_VALUE && cooldown > 0)
							{
								GuiUtils.drawTexturedModalRect(event.getMatrixStack(), (posX - 200 + (i * 50)) / 2, posY - 23, 24, 0, 23, (int) cooldown, 0);
								
								if(abl.getCooldown() < 10)
								{
									// Ready animation
									event.getMatrixStack().pushPose();
									{
										float scale = (float) (2.5F - (abl.getCooldown() / 10.0F));
										RenderSystem.color4f(0.2f, 0.8f, 0.4f, (float)(abl.getCooldown() / 10));
										event.getMatrixStack().translate((posX - 200 + (i * 50)) / 2, posY - 23, 1);
										event.getMatrixStack().translate(12, 12, 0);
										event.getMatrixStack().scale(scale, scale, 1);
										event.getMatrixStack().translate(-12, -12, 0);
										GuiUtils.drawTexturedModalRect(event.getMatrixStack(), 0, 0, 0, 0, 23, 23, -1);	
									}
									event.getMatrixStack().popPose();
								}
							}
						}
					}
					
					// Reverting the color back to avoid future slots being wrongly colored
					RenderSystem.color4f(1, 1, 1, 1);
										
					// Drawing the ability icons
					if(!abl.isDisabled())
					{
						String texture;
						if(abl.hasCustomTexture())
							texture = Beapi.getResourceName(abl.getCustomTexture());
						else
							texture = Beapi.getResourceName(abl.getName());
						Beapi.drawAbilityIcon(texture, (posX - 192 + (i * 50)) / 2, posY - 19, 16, 16);
					}
					event.getMatrixStack().translate(0, 0, 2);
					if(false)
						Beapi.drawStringWithBorder(mc.font, event.getMatrixStack(), number, (posX - 172 + (i * 50)) / 2 - mc.font.width(number) / 2, posY - 14, Beapi.hexToRGB("#FFFFFF").getRGB());
					mc.getTextureManager().bind(ModResources.WIDGETS);
				}		
				RenderSystem.disableBlend();
			}
			event.getMatrixStack().popPose();
		}
	}

	@SubscribeEvent
	public static void updateFOV(FOVUpdateEvent event)
	{
		if (true)
		{
			//if (FOV_EFFECTS.stream().anyMatch(f -> event.getEntity().hasEffect(f.get())))
			//	event.setNewfov(1.0F);

			if ((event.getEntity().hasEffect(Effects.MOVEMENT_SPEED)) && (event.getEntity().isSprinting()))
				event.setNewfov(1.1F);			
		}
	}
}
