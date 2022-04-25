package com.example.jujutsukaisen.api.ability;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.entities.spells.AbilityProjectileEntity;
import com.example.jujutsukaisen.init.ModRenderTypes;
import com.example.jujutsukaisen.models.projectiles.CubeModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class AbilityProjectileRenderer<T extends AbilityProjectileEntity, M extends EntityModel<T>> extends EntityRenderer<T>
{
	private float scaleX = 1, scaleY = 1, scaleZ = 1;
	private float red, blue, green, alpha;
	protected M model;
	private ResourceLocation texture;

	public AbilityProjectileRenderer(EntityRendererManager renderManager, M model)
	{
		super(renderManager);
		this.model = model;
	}

	public void setTexture(ResourceLocation res)
	{
		this.texture = res;
	}

	public void setColor(double red, double green, double blue, double alpha)
	{
		this.red = (float) (red / 255.0);
		this.green = (float) (green / 255.0);
		this.blue = (float) (blue / 255.0);
		this.alpha = (float) (alpha / 255.0);
	}

	public void setScale(double scaleX, double scaleY, double scaleZ)
	{
		this.scaleX = (float) scaleX;
		this.scaleY = (float) scaleY;
		this.scaleZ = (float) scaleZ;
	}

	public Vector3d getScale() 
	{
		return new Vector3d(this.scaleX, this.scaleY, this.scaleZ);
	}
	
	public Color getRGB()
	{
		return new Color(this.red, this.green, this.blue, this.alpha);
	}
	
	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight)
	{
		if(entity.tickCount < 2)
			return;

		matrixStack.pushPose();

		matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot) + 180.0F));
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot)));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
			
		matrixStack.scale(this.scaleX, this.scaleY, this.scaleZ);
						
		ResourceLocation finalTexture = this.getTextureLocation(entity);
		RenderType type = finalTexture == null ? ModRenderTypes.TRANSPARENT_COLOR : RenderType.entityTranslucent(finalTexture);

		if(this.model != null)
			this.model.renderToBuffer(matrixStack, buffer.getBuffer(type), packedLight, OverlayTexture.NO_OVERLAY, this.red, this.green, this.blue, this.alpha);
		
		matrixStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(AbilityProjectileEntity entity)
	{

		
		return this.texture;
	}

	public static class Factory implements IRenderFactory<AbilityProjectileEntity>
	{
		protected EntityModel model = new CubeModel();
		protected double scaleX = 1, scaleY = 1, scaleZ = 1;
		protected double red = 255, green = 255, blue = 255, alpha = 255;
		protected ResourceLocation texture;

		public Factory(EntityModel model)
		{
			this.model = model;
		}

		public Factory setTexture(String technique, String textureName)
		{
			this.texture = new ResourceLocation(Main.MODID, "textures/entities/projectiles/" + technique + "/" + textureName + ".png");
			return this;
		}
		
		public Factory setTexture(ResourceLocation location)
		{
			this.texture = location;
			return this;
		}

		public Factory setColor(double red, double green, double blue, double alpha)
		{
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
			return this;
		}

		public Factory setColor(String hex)
		{
			Color color = Beapi.hexToRGB(hex);
			this.red = color.getRed();
			this.green = color.getGreen();
			this.blue = color.getBlue();
			this.alpha = color.getAlpha();
			return this;
		}
		
		public Factory setAlpha(double alpha)
		{
			this.alpha = alpha;
			return this;
		}

		public Factory setScale(double scale)
		{
			this.scaleX = this.scaleY = this.scaleZ = scale;
			return this;
		}

		public Factory setScale(double scaleX, double scaleY, double scaleZ)
		{
			this.scaleX = scaleX;
			this.scaleY = scaleY;
			this.scaleZ = scaleZ;
			return this;
		}
		
		@Override
		public EntityRenderer<? super AbilityProjectileEntity> createRenderFor(EntityRendererManager manager)
		{
			AbilityProjectileRenderer renderer = new AbilityProjectileRenderer(manager, this.model);
			renderer.setTexture(this.texture);
			renderer.setScale(this.scaleX, this.scaleY, this.scaleZ);
			renderer.setColor(this.red, this.green, this.blue, this.alpha);
			return renderer;
		}
	}

}
