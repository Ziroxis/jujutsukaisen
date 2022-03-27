package com.example.jujutsukaisen.models.cursed_spirits;


import com.example.jujutsukaisen.entities.curses.SmallPoxEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SmallPoxModel<T extends SmallPoxEntity> extends EntityModel<T> {
	private final ModelRenderer Leg_1;
	private final ModelRenderer Leg_2;
	private final ModelRenderer Base_Part;
	private final ModelRenderer Arm_1;
	private final ModelRenderer Arm_2;
	private final ModelRenderer Head;

	public SmallPoxModel()
	{
		texWidth = 256;
		texHeight = 256;

		Leg_1 = new ModelRenderer(this);
		Leg_1.setPos(0.0F, 24.0F, 0.0F);
		Leg_1.texOffs(44, 102).addBox(4.0F, -27.0F, -2.0F, 7.0F, 27.0F, 5.0F, 0.0F, false);

		Leg_2 = new ModelRenderer(this);
		Leg_2.setPos(0.0F, 24.0F, 0.0F);
		Leg_2.texOffs(92, 57).addBox(-11.0F, -27.0F, -2.0F, 7.0F, 27.0F, 5.0F, 0.0F, false);

		Base_Part = new ModelRenderer(this);
		Base_Part.setPos(0.0F, 24.0F, 0.0F);
		Base_Part.texOffs(0, 46).addBox(-12.0F, -58.0F, -6.0F, 24.0F, 31.0F, 14.0F, 0.0F, false);
		Base_Part.texOffs(0, 91).addBox(-6.0F, -50.0F, 9.0F, 11.0F, 13.0F, 11.0F, 0.0F, false);

		Arm_1 = new ModelRenderer(this);
		Arm_1.setPos(0.0F, 0.0F, 0.0F);
		Base_Part.addChild(Arm_1);
		Arm_1.texOffs(77, 15).addBox(12.0F, -53.0F, -4.0F, 8.0F, 11.0F, 31.0F, 0.0F, false);

		Arm_2 = new ModelRenderer(this);
		Arm_2.setPos(0.0F, 24.0F, 0.0F);
		Arm_2.texOffs(45, 60).addBox(-20.0F, -53.0F, -4.0F, 8.0F, 11.0F, 31.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 24.0F, 0.0F);
		Head.texOffs(0, 0).addBox(-13.0F, -76.0F, -8.0F, 27.0F, 18.0F, 28.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Leg_1.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_2.render(matrixStack, buffer, packedLight, packedOverlay);
		Base_Part.render(matrixStack, buffer, packedLight, packedOverlay);
		Arm_2.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}