package com.example.jujutsukaisen.events;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.effects.SpecialEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Iterator;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class SpecialPotionEffectEvents
{
	@SubscribeEvent
	public static void onEntityPreRendered(RenderLivingEvent.Pre event)
	{
		LivingEntity entity = event.getEntity();
		
		for(EffectInstance instance : entity.getActiveEffects())
		{
			if(instance.getEffect() instanceof SpecialEffect)
			{
				if(((SpecialEffect)instance.getEffect()).isBlockingMovement())
				{
					entity.yBodyRot = 0;
					entity.yBodyRotO = 0;
					return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onEntityPostRendered(RenderLivingEvent.Post event)
	{
		LivingEntity entity = event.getEntity();
		
		for(EffectInstance instance : entity.getActiveEffects())
		{
			if(instance.getEffect() instanceof SpecialEffect)			
			{
				if (entity.getEffect(instance.getEffect()).getDuration() <= 0)
				{
					entity.removeEffect(instance.getEffect());
					return;
				}
				
				if(((SpecialEffect)instance.getEffect()).isBlockingMovement())
				{
					entity.yBodyRot = 0;
					entity.yBodyRotO = 0;
					return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onFirstPersonViewRendered(TickEvent.RenderTickEvent event)
	{
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		
		if (player == null)
			return;

		if(mc.options.getCameraType() != PointOfView.FIRST_PERSON)
			return;
		
		Iterator iterator = player.getActiveEffects().iterator();
		if(iterator.hasNext())
		{
			EffectInstance instance = (EffectInstance) iterator.next();
			if (instance.getDuration() <= 0)
				player.removeEffect(instance.getEffect());
			
			if(instance.getEffect() instanceof SpecialEffect)			
			{
				SpecialEffect effect = ((SpecialEffect)instance.getEffect());
				if(effect.getResourceLocation(instance.getDuration()) == null && effect.getOverlayColor() != null)
				{
					float[] colors = ((SpecialEffect)instance.getEffect()).getOverlayColor();
					Color color = new Color(colors[0], colors[1], colors[2]);
					Beapi.drawColourOnScreen(color.getRGB(), (int) (colors[3] * 255), 0, 0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), 500);
				}
			}
		}
	}	
}