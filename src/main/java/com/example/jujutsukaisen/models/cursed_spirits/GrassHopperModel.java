package com.example.jujutsukaisen.models.cursed_spirits;// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.example.jujutsukaisen.entities.curses.GrassHopperEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

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
		Base.setPos(0.0F, -8.0F, 0.0F);
		Base.texOffs(0, 0).addBox(-7.0F, -3.0F, -5.0F, 14.0F, 19.0F, 12.0F, 0.0F, false);
		Base.texOffs(0, 0).addBox(-1.0F, 7.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		Leg_1 = new ModelRenderer(this);
		Leg_1.setPos(-3.0F, 9.0F, 0.0F);
		Leg_1.texOffs(36, 58).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 16.0F, 6.0F, 0.0F, false);

		Leg_2 = new ModelRenderer(this);
		Leg_2.setPos(3.0F, 9.0F, 0.0F);
		Leg_2.texOffs(52, 0).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 16.0F, 6.0F, 0.0F, false);

		Arm_1 = new ModelRenderer(this);
		Arm_1.setPos(-9.0F, -9.0F, 0.0F);
		Arm_1.texOffs(18, 50).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 22.0F, 5.0F, 0.0F, false);

		Arm_2 = new ModelRenderer(this);
		Arm_2.setPos(9.0F, -9.0F, 0.0F);
		Arm_2.texOffs(0, 50).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 22.0F, 5.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -11.0F, 0.0F);
		Head.texOffs(36, 38).addBox(-4.0F, -8.0F, -9.0F, 8.0F, 8.0F, 12.0F, 0.0F, false);
	}
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Head.xRot = headPitch * ((float)Math.PI / 180F);
		this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.Arm_1.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.Arm_2.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.Leg_1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.Leg_2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

	}
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Base.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_1.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_2.render(matrixStack, buffer, packedLight, packedOverlay);
		Arm_1.render(matrixStack, buffer, packedLight, packedOverlay);
		Arm_2.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}