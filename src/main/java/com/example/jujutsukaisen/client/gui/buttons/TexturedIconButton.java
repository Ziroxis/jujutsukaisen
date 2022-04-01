package com.example.jujutsukaisen.client.gui.buttons;

import com.example.jujutsukaisen.api.Beapi;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TexturedIconButton extends Button
{
	private ResourceLocation texture;
	private int textureWidth;
	private int textureHeight;
	private int texturePosX;
	private int texturePosY;
	private int textPosX;
	private int textPosY;
	private double textScale = 1;
	private ResourceLocation iconTexture;
	private double iconScale = 1;
	private int iconPosX;
	private int iconPosY;
	private boolean isPressed;

	public TexturedIconButton(ResourceLocation loc, int posX, int posY, int width, int height, ITextComponent text, IPressable onPress)
	{
		super(posX, posY, width, height, text, onPress);

		this.texture = loc;
		this.texturePosX = posX;
		this.texturePosY = posY;
		this.textureWidth = width;
		this.textureHeight = height;
	}

	public TexturedIconButton setTextureInfo(int texturePosX, int texturePosY, int textureWidth, int textureHeight)
	{
		this.texturePosX = texturePosX;
		this.texturePosY = texturePosY;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		return this;
	}

	public TexturedIconButton setTextInfo(int textPosX, int textPosY, double scale)
	{
		this.textPosX = textPosX;
		this.textPosY = textPosY - 7;
		this.textScale = scale;
		return this;
	}

	public TexturedIconButton setIconInfo(ResourceLocation loc, int iconPosX, int iconPosY, double scale)
	{
		this.iconTexture = loc;
		this.iconPosX = iconPosX;
		this.iconPosY = iconPosY;
		this.iconScale = scale;
		return this;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		if (!this.visible)
			return;

		this.isHovered = this.active && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

		matrixStack.pushPose();
		if (this.isHovered || this.isPressed)
		{
			matrixStack.translate(0, 0.5, 0);
			RenderSystem.color3f(0.7f, 0.7f, 0.7f);
		}
		
		if(!this.active)
			RenderSystem.color3f(0.5f, 0.5f, 0.5f);

		Beapi.drawIcon(this.texture, this.texturePosX, this.texturePosY, this.textureWidth, this.textureHeight);

		// Icons handling, scaling, position, rendering etc
		if (this.iconTexture != null)
		{
			matrixStack.pushPose();
			{
				RenderSystem.enableBlend();
				matrixStack.translate(this.iconPosX, this.iconPosY, 2);
				matrixStack.scale((float)this.iconScale, (float)this.iconScale, (float)this.iconScale);
				Beapi.drawIcon(matrixStack.last().pose(), this.iconTexture, 0, 0, 16, 16);
			}
			matrixStack.popPose();
		}

		// Text handling, scaling, split lines etc
		matrixStack.pushPose();
		{
			FontRenderer font = Minecraft.getInstance().font;
			List<String> strings = Arrays.asList(this.getMessage().getString());
			List<IReorderingProcessor> splitProc = null;
			
			int splits = this.getMessage().getString().split(" ").length;
			if(splits > 1)
				splitProc = font.split(this.getMessage(), (this.width / splits) + 10);
				//strings = WyHelper.splitString(font, this.getMessage().getString(), this.textPosX - font.width(this.getMessage()) / 2 + 26, (this.width / splits) + 10);

			if(splitProc != null)
			{
				matrixStack.translate(this.textPosX, this.textPosY - (splitProc.size() > 1 ? splitProc.size() * 3 : 0), 2);
				matrixStack.translate(128, 128, 0);
				matrixStack.scale((float)this.textScale, (float)this.textScale, (float)this.textScale);
				matrixStack.translate(-128, -128, 1);
				for (int b = 0; b < splitProc.size(); b++)
				{
					Beapi.drawStringWithBorder(font, matrixStack, splitProc.get(b), 0, 7 + (b * 9), Beapi.hexToRGB("#FFFFFF").getRGB());
				}
			}
			else
			{
				matrixStack.translate(this.textPosX, this.textPosY - (strings.size() > 1 ? strings.size() * 3 : 0), 2);
				matrixStack.translate(128, 128, 0);
				matrixStack.scale((float)this.textScale, (float)this.textScale, (float)this.textScale);
				matrixStack.translate(-128, -128, 1);
				for (int b = 0; b < strings.size(); b++)
				{
					Beapi.drawStringWithBorder(font, matrixStack, strings.get(b), 0, 7 + (b * 9), Beapi.hexToRGB("#FFFFFF").getRGB());
				}
			}
		}
		matrixStack.popPose();

		RenderSystem.color3f(1f, 1f, 1f);

		//if(WyDebug.isDebug())
		//	this.fillGradient(this.x, this.y, this.width + this.x, this.height + this.y, WyHelper.hexToRGB("#FF0000").getRGB(), WyHelper.hexToRGB("#FF5500").getRGB());
		
		matrixStack.popPose();
	}

	public void setIsPressed(boolean flag)
	{
		this.isPressed = flag;
	}
}
