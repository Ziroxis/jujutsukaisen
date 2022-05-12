package com.example.jujutsukaisen.models.projectiles.disaster_flames;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlameBallModel extends EntityModel {
	private final ModelRenderer Flame_Ball;

	public FlameBallModel() {
		texWidth = 64;
		texHeight = 64;

		Flame_Ball = new ModelRenderer(this);
		Flame_Ball.setPos(0.0F, 24.0F, 0.0F);
		Flame_Ball.texOffs(0, 0).addBox(-6.0F, -14.0F, -7.0F, 12.0F, 10.0F, 15.0F, 0.0F, false);
		Flame_Ball.texOffs(39, 0).addBox(-5.0F, -13.0F, 8.0F, 10.0F, 8.0F, 1.0F, 0.0F, false);
		Flame_Ball.texOffs(15, 25).addBox(-5.0F, -13.0F, -8.0F, 10.0F, 8.0F, 1.0F, 0.0F, false);
		Flame_Ball.texOffs(28, 25).addBox(6.0F, -13.0F, -6.0F, 1.0F, 8.0F, 13.0F, 0.0F, false);
		Flame_Ball.texOffs(0, 25).addBox(-7.0F, -13.0F, -6.0F, 1.0F, 8.0F, 13.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Flame_Ball.render(matrixStack, buffer, packedLight, packedOverlay);
	}

}