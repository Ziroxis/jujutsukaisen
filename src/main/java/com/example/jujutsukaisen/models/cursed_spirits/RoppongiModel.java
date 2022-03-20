package com.example.jujutsukaisen.models.cursed_spirits;


import com.example.jujutsukaisen.entities.curses.RoppongiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class RoppongiModel<T extends RoppongiEntity> extends EntityModel<T> {
	private final ModelRenderer Base;
	private final ModelRenderer Head;
	private final ModelRenderer Arm_2;
	private final ModelRenderer Arm_1;
	private final ModelRenderer Leg_1;
	private final ModelRenderer Leg_2;

	public RoppongiModel()
	{
		texWidth = 64;
		texHeight = 64;

		Base = new ModelRenderer(this);
		Base.setPos(0.0F, 24.0F, 0.0F);
		Base.texOffs(0, 0).addBox(-5.0F, -22.0F, -5.0F, 11.0F, 13.0F, 11.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 24.0F, 0.0F);
		Head.texOffs(0, 24).addBox(-4.0F, -30.0F, -3.0F, 9.0F, 8.0F, 7.0F, 0.0F, false);

		Arm_2 = new ModelRenderer(this);
		Arm_2.setPos(0.0F, 24.0F, 0.0F);
		Arm_2.texOffs(32, 24).addBox(6.0F, -20.0F, -1.0F, 3.0F, 11.0F, 3.0F, 0.0F, false);
		Arm_2.texOffs(0, 2).addBox(6.0F, -9.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		Arm_1 = new ModelRenderer(this);
		Arm_1.setPos(0.0F, 24.0F, 0.0F);
		Arm_1.texOffs(29, 38).addBox(-8.0F, -20.0F, -1.0F, 3.0F, 11.0F, 3.0F, 0.0F, false);
		Arm_1.texOffs(0, 0).addBox(-8.0F, -9.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		Leg_1 = new ModelRenderer(this);
		Leg_1.setPos(0.0F, 24.0F, 0.0F);
		Leg_1.texOffs(12, 39).addBox(-5.0F, -9.0F, -2.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		Leg_2 = new ModelRenderer(this);
		Leg_2.setPos(0.0F, 24.0F, 0.0F);
		Leg_2.texOffs(0, 39).addBox(3.0F, -9.0F, -2.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}


	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		Base.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		Arm_1.render(matrixStack, buffer, packedLight, packedOverlay);
		Arm_2.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_1.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_2.render(matrixStack, buffer, packedLight, packedOverlay);

	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
