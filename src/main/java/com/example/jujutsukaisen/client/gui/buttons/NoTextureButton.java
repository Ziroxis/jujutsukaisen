package com.example.jujutsukaisen.client.gui.buttons;

import com.example.jujutsukaisen.api.Beapi;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class NoTextureButton extends Button
{
	private boolean isSelected;
	private boolean isFake;

	private String onHoverTextColor = "#00FFBB";
	
	public NoTextureButton(int posX, int posY, int width, int height, ITextComponent string, IPressable onPress)
	{
		super(posX, posY, width, height, string, onPress);
	}

	public void setFake()
	{
		this.isFake = true;
	}
	
	public void setOnHoverTextColor(String color)
	{
		this.onHoverTextColor = color;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		matrixStack.pushPose();
		if (this.visible)
		{
			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int rgb = Beapi.hexToRGB("#FFFFFF").getRGB();
			int topGrad = Beapi.hexToRGB("#B3B3B3").getRGB();
			int bottomGrad = Beapi.hexToRGB("#939393").getRGB();
			
			if (this.isHovered)
			{
				matrixStack.translate(0, 0.5, 0);
				rgb = Beapi.hexToRGB(this.onHoverTextColor).getRGB();
				topGrad = Beapi.hexToRGB("#B3B3B3").getRGB();
				bottomGrad = Beapi.hexToRGB("#505050").getRGB();
				
				if(this.isFake)
				{
					topGrad = Beapi.hexToRGB("#3333BB00").getRGB();
					bottomGrad = Beapi.hexToRGB("#000055FF").getRGB();
					this.fillGradient(matrixStack, this.x, this.y, this.width + this.x, this.height + this.y, topGrad, bottomGrad);
				}
			}
			
			if (this.isSelected)
			{
				topGrad = Beapi.hexToRGB("#00CC00").getRGB();
				bottomGrad = Beapi.hexToRGB("#005500").getRGB();
			}

			int outlineSize = 1;
			
			if(!this.isFake)
			{
				this.fillGradient(matrixStack, this.x - outlineSize, this.y - outlineSize, this.width + this.x + outlineSize, this.height + this.y + outlineSize, Beapi.hexToRGB("#000000").getRGB(), Beapi.hexToRGB("#000000").getRGB());
				this.fillGradient(matrixStack, this.x, this.y, this.width + this.x, this.height + this.y, topGrad, bottomGrad);
				
				FontRenderer font = Minecraft.getInstance().font;
				Beapi.drawStringWithBorder(font, matrixStack, this.getMessage().getString(), this.x - font.width(this.getMessage()) / 2 + this.width / 2, this.y + (this.height / 2) - 4, rgb);
			}
			
			RenderSystem.color3f(1f, 1f, 1f);
		}
		matrixStack.popPose();
	}

	public void select()
	{
		this.isSelected = !this.isSelected;
	}
}
