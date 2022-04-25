package com.example.jujutsukaisen.init;

import com.example.jujutsukaisen.Main;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModRenderTypes extends RenderType
{	
	public ModRenderTypes(String name, VertexFormat format, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask)
	{
		super(name, format, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
	}

	public static final RenderType ENERGY = create(Main.MODID + ":energy", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP, GL11.GL_QUADS, 256, false, true, State.builder()
		.setTextureState(RenderState.NO_TEXTURE)
		.setCullState(RenderState.CULL)
		.setAlphaState(RenderState.DEFAULT_ALPHA)
		.setTransparencyState(RenderState.LIGHTNING_TRANSPARENCY)
		.createCompositeState(true));

	public static final RenderType SOLID = create(Main.MODID + ":solid", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP, GL11.GL_QUADS, 256, false, true, State.builder()
			.setTextureState(RenderState.NO_TEXTURE)
			.setCullState(RenderState.CULL)
			.createCompositeState(true));


	public static final RenderType TRANSPARENT_COLOR = create(Main.MODID + "translucent_color_notexture", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, true, State.builder()
		.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
		.setTextureState(NO_TEXTURE)
		.setCullState(RenderState.NO_CULL)
		.setLightmapState(LIGHTMAP)
		.setDiffuseLightingState(DIFFUSE_LIGHTING)
		.createCompositeState(true));
	
	public static RenderType getEnergyRenderType()
	{
		State state = State.builder()
			.setTextureState(RenderState.NO_TEXTURE)
			.setCullState(RenderState.CULL)
			.setAlphaState(RenderState.DEFAULT_ALPHA)
			.setDiffuseLightingState(RenderState.DIFFUSE_LIGHTING)
			.setOverlayState(RenderState.OVERLAY)
			.setTransparencyState(RenderState.LIGHTNING_TRANSPARENCY)
			.createCompositeState(true);
		
		return create(Main.MODID + "energy", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP, GL11.GL_QUADS, 256, false, true, state);
	}
	
	public static RenderType getAuraRenderType(ResourceLocation texture)
	{
		TextureState textureState = new TextureState(texture, false, false);
		State state = State.builder()
			.setTextureState(textureState)
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setDiffuseLightingState(DIFFUSE_LIGHTING)
			.setAlphaState(DEFAULT_ALPHA)
			.setLightmapState(LIGHTMAP)
			.setOverlayState(OVERLAY)
			.createCompositeState(true);
		
		return create(Main.MODID + "aura_color_notexture", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, true, state);
	}
	

	


	public static RenderType getAbilityBody(ResourceLocation texture)
	{
		TextureState textureState = new TextureState(texture, false, false);
		State state = State.builder()
			.setTransparencyState(TransparencyState.TRANSLUCENT_TRANSPARENCY)
			.setAlphaState(AlphaState.DEFAULT_ALPHA)
			.setTextureState(textureState)
			.setCullState(CullState.NO_CULL)
			.setLightmapState(LightmapState.LIGHTMAP)
			.createCompositeState(true);
		return create(Main.MODID + ":ability_body", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, true, state);
	}
	
	public static RenderType getAbilityHand(ResourceLocation texture)
	{
		TextureState textureState = new TextureState(texture, false, false);
		State state = State.builder()
			.setTransparencyState(TransparencyState.TRANSLUCENT_TRANSPARENCY)
			.setAlphaState(AlphaState.DEFAULT_ALPHA)
			.setTextureState(textureState)
			.setCullState(CullState.NO_CULL)
			.setLightmapState(LightmapState.LIGHTMAP)
			.createCompositeState(true);
		return create("ability_hand", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, true, state);
	}
	
	public static RenderType getWantedPoster(ResourceLocation texture)
	{
		TextureState textureState = new TextureState(texture, false, false);
		State state = State.builder()
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setAlphaState(DEFAULT_ALPHA)
			.setTextureState(textureState)
			.setCullState(NO_CULL)
			.setLightmapState(LIGHTMAP)
			.createCompositeState(true);
		return create("wanted_poster", DefaultVertexFormats.BLOCK, 7, 256, true, true, state);
	}
	
	public static RenderType getWantedPosterExpiration(ResourceLocation texture)
	{
		TextureState textureState = new TextureState(texture, false, false);
		State state = State.builder()
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setAlphaState(DEFAULT_ALPHA)
			.setTextureState(textureState)
			.setCullState(CullState.CULL)
			.setLightmapState(LIGHTMAP)
			.createCompositeState(true);
		return create("wanted_poster_expiration", DefaultVertexFormats.BLOCK, 7, 256, true, true, state);
	}

	public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP = new VertexFormat(ImmutableList.<VertexFormatElement>builder()
		.add(DefaultVertexFormats.ELEMENT_POSITION)
		.add(DefaultVertexFormats.ELEMENT_UV0)
		.add(DefaultVertexFormats.ELEMENT_COLOR)
		.add(DefaultVertexFormats.ELEMENT_UV2)
		.build());
}
