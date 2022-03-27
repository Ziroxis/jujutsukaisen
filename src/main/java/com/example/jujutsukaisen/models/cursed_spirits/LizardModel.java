package com.example.jujutsukaisen.models.cursed_spirits;

import com.example.jujutsukaisen.entities.curses.LizardEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class LizardModel<T extends LizardEntity> extends EntityModel<T> {
	private final ModelRenderer Base_Body;
	private final ModelRenderer Leg_1;
	private final ModelRenderer Leg_2;
	private final ModelRenderer Leg_3;
	private final ModelRenderer Leg_4;
	private final ModelRenderer Head;

	public LizardModel() {
		texWidth = 128;
		texHeight = 128;

		Base_Body = new ModelRenderer(this);
		Base_Body.setPos(0.0F, 16.0F, 0.0F);
		Base_Body.texOffs(0, 0).addBox(-12.0F, -17.0F, -5.0F, 21.0F, 20.0F, 9.0F, 0.0F, false);
		Base_Body.texOffs(21, 29).addBox(-12.0F, -22.0F, -1.0F, 18.0F, 5.0F, 1.0F, 0.0F, false);

		Leg_1 = new ModelRenderer(this);
		Leg_1.setPos(0.0F, 24.0F, 0.0F);
		Leg_1.texOffs(30, 35).addBox(2.0F, -19.0F, -8.0F, 7.0F, 19.0F, 3.0F, 0.0F, false);

		Leg_2 = new ModelRenderer(this);
		Leg_2.setPos(0.0F, 0.0F, 0.0F);
		Leg_1.addChild(Leg_2);
		Leg_2.texOffs(50, 35).addBox(-10.0F, -19.0F, -8.0F, 6.0F, 19.0F, 3.0F, 0.0F, false);

		Leg_3 = new ModelRenderer(this);
		Leg_3.setPos(0.0F, 0.0F, 0.0F);
		Leg_2.addChild(Leg_3);
		Leg_3.texOffs(0, 50).addBox(3.0F, -19.0F, 4.0F, 6.0F, 19.0F, 3.0F, 0.0F, false);

		Leg_4 = new ModelRenderer(this);
		Leg_4.setPos(0.0F, 0.0F, 0.0F);
		Leg_3.addChild(Leg_4);
		Leg_4.texOffs(18, 57).addBox(-9.0F, -19.0F, 4.0F, 5.0F, 19.0F, 3.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 0.0F, 0.0F);
		Leg_4.addChild(Head);
		Head.texOffs(0, 29).addBox(9.0F, -28.0F, -5.0F, 6.0F, 12.0F, 9.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Base_Body.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg_1.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}