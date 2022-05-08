package com.example.jujutsukaisen.models.cursed_spirits;// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.example.jujutsukaisen.entities.curses.GrassHopperEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GrassHopperModel<T extends GrassHopperEntity> extends EntityModel<T> {
	private final ModelRenderer Base;
	private final ModelRenderer Leg_1;
	private final ModelRenderer Leg_2;
	private final ModelRenderer Arm_1;
	private final ModelRenderer Arm_2;
	private final ModelRenderer Head;

	public GrassHopperModel() {
		texWidth = 128;
		texHeight = 128;

		Base = new ModelRenderer(this);
		Base.setPos(0.0F, 24.0F, 0.0F);
		Base.texOffs(0, 0).addBox(-7.0F, -35.0F, -5.0F, 14.0F, 19.0F, 12.0F, 0.0F, false);
		Base.texOffs(0, 0).addBox(-1.0F, -25.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		Leg_1 = new ModelRenderer(this);
		Leg_1.setPos(0.0F, 24.0F, 0.0F);
		Leg_1.texOffs(36, 58).addBox(-5.0F, -16.0F, -3.0F, 4.0F, 16.0F, 6.0F, 0.0F, false);

		Leg_2 = new ModelRenderer(this);
		Leg_2.setPos(0.0F, 24.0F, 0.0F);
		Leg_2.texOffs(52, 0).addBox(1.0F, -16.0F, -3.0F, 4.0F, 16.0F, 6.0F, 0.0F, false);

		Arm_1 = new ModelRenderer(this);
		Arm_1.setPos(0.0F, 24.0F, 0.0F);
		Arm_1.texOffs(18, 50).addBox(-11.0F, -35.0F, -3.0F, 4.0F, 22.0F, 5.0F, 0.0F, false);

		Arm_2 = new ModelRenderer(this);
		Arm_2.setPos(0.0F, 24.0F, 0.0F);
		Arm_2.texOffs(0, 50).addBox(7.0F, -35.0F, -3.0F, 4.0F, 22.0F, 5.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 24.0F, 0.0F);
		Head.texOffs(36, 38).addBox(-4.0F, -43.0F, -9.0F, 8.0F, 8.0F, 12.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Base.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_1.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_2.render(matrixStack, buffer, packedLight, packedOverlay);
		Arm_1.render(matrixStack, buffer, packedLight, packedOverlay);
		Arm_2.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		//TODO add animations
	}
}