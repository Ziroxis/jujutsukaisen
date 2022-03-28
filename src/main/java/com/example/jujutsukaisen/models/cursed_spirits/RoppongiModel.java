package com.example.jujutsukaisen.models.cursed_spirits;


import com.example.jujutsukaisen.entities.curses.RoppongiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class RoppongiModel<T extends RoppongiEntity> extends EntityModel<T> {
	private final ModelRenderer Roppongi_main;
	private final ModelRenderer Roppongi_base;
	private final ModelRenderer Head;
	private final ModelRenderer Roppongi_arms;
	private final ModelRenderer Arm_left;
	private final ModelRenderer Arm_right;
	private final ModelRenderer Roppongi_legs;
	private final ModelRenderer Leg_right;
	private final ModelRenderer Leg_left;

	public RoppongiModel()
	{
		texWidth = 64;
		texHeight = 64;

		Roppongi_main = new ModelRenderer(this);
		Roppongi_main.setPos(0.0F, 24.0F, 0.0F);


		Roppongi_base = new ModelRenderer(this);
		Roppongi_base.setPos(0.5F, -15.5F, 0.5F);
		Roppongi_main.addChild(Roppongi_base);
		Roppongi_base.texOffs(0, 0).addBox(-5.5F, -6.5F, -5.5F, 11.0F, 13.0F, 11.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -6.5F, 0.0F);
		Roppongi_base.addChild(Head);
		Head.texOffs(0, 24).addBox(-4.5F, -8.0F, -3.5F, 9.0F, 8.0F, 7.0F, 0.0F, false);

		Roppongi_arms = new ModelRenderer(this);
		Roppongi_arms.setPos(0.0F, -19.0F, 0.0F);
		Roppongi_main.addChild(Roppongi_arms);


		Arm_left = new ModelRenderer(this);
		Arm_left.setPos(7.5F, 0.5F, 0.5F);
		Roppongi_arms.addChild(Arm_left);
		Arm_left.texOffs(32, 24).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 11.0F, 3.0F, 0.0F, false);
		Arm_left.texOffs(0, 2).addBox(-1.5F, 9.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		Arm_right = new ModelRenderer(this);
		Arm_right.setPos(-6.5F, 0.5F, 0.5F);
		Roppongi_arms.addChild(Arm_right);
		Arm_right.texOffs(29, 38).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 11.0F, 3.0F, 0.0F, false);
		Arm_right.texOffs(0, 0).addBox(-1.5F, 9.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		Roppongi_legs = new ModelRenderer(this);
		Roppongi_legs.setPos(0.0F, -9.0F, 0.0F);
		Roppongi_main.addChild(Roppongi_legs);


		Leg_right = new ModelRenderer(this);
		Leg_right.setPos(-3.5F, 0.5F, -0.5F);
		Roppongi_legs.addChild(Leg_right);
		Leg_right.texOffs(12, 39).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		Leg_left = new ModelRenderer(this);
		Leg_left.setPos(4.5F, 0.5F, -0.5F);
		Roppongi_legs.addChild(Leg_left);
		Leg_left.texOffs(0, 39).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.Head.xRot = headPitch * ((float)Math.PI / 180F);
		this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.Arm_right.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.Arm_left.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.Leg_right.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.Leg_left.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}


	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		Roppongi_main.render(matrixStack, buffer, packedLight, packedOverlay);

	}


}
