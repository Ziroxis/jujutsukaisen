package com.example.jujutsukaisen.events.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.events.projectile.ProjectileBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class AbilityProjectileEvents
{
	@SubscribeEvent
	public static void onBlockCheck(ProjectileBlockEvent event)
	{
		if( false )
		{
			event.setCanBlock(true);
		}
	}
}
