package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.api.Beapi;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class AbilityOverlay
{
	private ResourceLocation texture = null;
	private RenderType renderType = RenderType.NORMAL;
	private Color color = Beapi.hexToRGB("#FFFFFFFF");

	public AbilityOverlay setTexture(ResourceLocation texture)
	{
		this.texture = texture;
		return this;
	}

	public AbilityOverlay setColor(Color color)
	{
		this.color = color;
		return this;
	}
	
	public AbilityOverlay setColor(String hex)
	{
		this.color = Beapi.hexToRGB(hex);
		return this;
	}
	
	public AbilityOverlay setRenderType(RenderType type)
	{
		this.renderType = type;
		return this;
	}
	
	public ResourceLocation getTexture()
	{
		return this.texture;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public RenderType getRenderType()
	{
		return renderType;
	}
	
	public enum RenderType
	{
		NORMAL, ENERGY
	}
}
