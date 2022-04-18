package com.example.jujutsukaisen.models.cursed_spirits;


import com.example.jujutsukaisen.entities.curses.SmallPoxEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SmallPoxModel<T extends SmallPoxEntity> extends EntityModel<T> {
	private final ModelRenderer SmallPox_Main;
	private final ModelRenderer SmallPox_Base;
	private final ModelRenderer SmallPox_Head;
	private final ModelRenderer SmallPox_UpperMouth;
	private final ModelRenderer SmallPox_LowerMouth;
	private final ModelRenderer SmallPox_Head_Details;
	private final ModelRenderer SmallPox_RightFang;
	private final ModelRenderer SmallPox_LeftFang;
	private final ModelRenderer SmallPox_Arms;
	private final ModelRenderer SmallPox_LeftArm;
	private final ModelRenderer SmallPox_RightArm;
	private final ModelRenderer SmallPox_TailMain;
	private final ModelRenderer SmallPox_Tail;

	public SmallPoxModel() {
		texWidth = 256;
		texHeight = 256;

		SmallPox_Main = new ModelRenderer(this);
		SmallPox_Main.setPos(0.0F, 24.0F, 0.0F);


		SmallPox_Base = new ModelRenderer(this);
		SmallPox_Base.setPos(-0.5F, -44.1667F, 2.9111F);
		SmallPox_Main.addChild(SmallPox_Base);
		SmallPox_Base.texOffs(0, 35).addBox(-10.0F, -16.8333F, -7.9111F, 20.0F, 32.0F, 12.0F, 0.0F, false);
		SmallPox_Base.texOffs(59, 0).addBox(-8.0F, -18.8333F, -6.9111F, 16.0F, 6.0F, 11.0F, 0.0F, false);
		SmallPox_Base.texOffs(114, 0).addBox(-9.5F, -15.8333F, -8.9111F, 19.0F, 29.0F, 1.0F, 0.0F, false);
		SmallPox_Base.texOffs(54, 118).addBox(-9.0F, -13.3333F, -9.9111F, 18.0F, 26.0F, 1.0F, 0.0F, false);
		SmallPox_Base.texOffs(92, 137).addBox(-7.0F, -11.3333F, -10.7111F, 14.0F, 22.0F, 1.0F, 0.0F, false);
		SmallPox_Base.texOffs(64, 23).addBox(-9.5F, -4.8333F, -0.9111F, 19.0F, 19.0F, 12.0F, 0.0F, false);
		SmallPox_Base.texOffs(0, 109).addBox(-8.5F, -5.3333F, -0.9111F, 17.0F, 20.0F, 10.0F, 0.0F, false);
		SmallPox_Base.texOffs(126, 30).addBox(-8.5F, -3.8333F, 9.0889F, 17.0F, 17.0F, 3.0F, 0.0F, false);
		SmallPox_Base.texOffs(84, 54).addBox(-8.0F, -3.3333F, 10.0889F, 16.0F, 16.0F, 3.0F, 0.0F, false);

		SmallPox_Head = new ModelRenderer(this);
		SmallPox_Head.setPos(0.0F, -14.8333F, -1.9111F);
		SmallPox_Base.addChild(SmallPox_Head);
		SmallPox_Head.texOffs(58, 90).addBox(-9.0F, -9.0F, -4.0F, 18.0F, 16.0F, 12.0F, 0.0F, false);
		SmallPox_Head.texOffs(0, 0).addBox(-10.5F, -13.0F, -2.0F, 21.0F, 18.0F, 17.0F, 0.0F, false);

		SmallPox_UpperMouth = new ModelRenderer(this);
		SmallPox_UpperMouth.setPos(0.0F, -6.75F, 13.75F);
		SmallPox_Head.addChild(SmallPox_UpperMouth);
		SmallPox_UpperMouth.texOffs(106, 83).addBox(-9.5F, -5.75F, -0.75F, 19.0F, 9.0F, 5.0F, 0.0F, false);
		SmallPox_UpperMouth.texOffs(0, 41).addBox(7.5F, 3.25F, -0.75F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		SmallPox_UpperMouth.texOffs(0, 35).addBox(-9.5F, 3.25F, -0.75F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		SmallPox_UpperMouth.texOffs(136, 97).addBox(-8.5F, -4.75F, 3.25F, 17.0F, 9.0F, 2.0F, 0.0F, false);

		SmallPox_LowerMouth = new ModelRenderer(this);
		SmallPox_LowerMouth.setPos(0.0F, 1.75F, 13.75F);
		SmallPox_Head.addChild(SmallPox_LowerMouth);
		SmallPox_LowerMouth.texOffs(92, 128).addBox(-9.5F, -1.25F, -0.75F, 19.0F, 4.0F, 5.0F, 0.0F, false);
		SmallPox_LowerMouth.texOffs(52, 41).addBox(-9.5F, -3.25F, -0.75F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		SmallPox_LowerMouth.texOffs(52, 35).addBox(7.5F, -3.25F, -0.75F, 2.0F, 2.0F, 4.0F, 0.0F, false);
		SmallPox_LowerMouth.texOffs(76, 17).addBox(-8.5F, -2.25F, 3.25F, 17.0F, 4.0F, 2.0F, 0.0F, false);

		SmallPox_Head_Details = new ModelRenderer(this);
		SmallPox_Head_Details.setPos(-0.5F, -0.75F, 6.25F);
		SmallPox_LowerMouth.addChild(SmallPox_Head_Details);


		SmallPox_RightFang = new ModelRenderer(this);
		SmallPox_RightFang.setPos(6.5F, -0.1075F, 0.8313F);
		SmallPox_Head_Details.addChild(SmallPox_RightFang);
		SmallPox_RightFang.texOffs(59, 0).addBox(-1.0F, -1.1495F, -2.918F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		SmallPox_RightFang.texOffs(0, 0).addBox(-0.5F, -4.1495F, -2.418F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		SmallPox_LeftFang = new ModelRenderer(this);
		SmallPox_LeftFang.setPos(-5.5F, -0.1075F, 0.8313F);
		SmallPox_Head_Details.addChild(SmallPox_LeftFang);
		SmallPox_LeftFang.texOffs(59, 5).addBox(-1.0F, -1.1495F, -2.918F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		SmallPox_LeftFang.texOffs(12, 0).addBox(-0.5F, -4.1495F, -2.418F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		SmallPox_Arms = new ModelRenderer(this);
		SmallPox_Arms.setPos(0.0F, -49.0F, 0.0F);
		SmallPox_Main.addChild(SmallPox_Arms);


		SmallPox_LeftArm = new ModelRenderer(this);
		SmallPox_LeftArm.setPos(-15.0F, 1.5F, 1.0F);
		SmallPox_Arms.addChild(SmallPox_LeftArm);
		SmallPox_LeftArm.texOffs(0, 79).addBox(-5.0F, -5.5F, -5.0F, 10.0F, 11.0F, 19.0F, 0.0F, false);
		SmallPox_LeftArm.texOffs(103, 54).addBox(-4.5F, -5.0F, 12.0F, 9.0F, 10.0F, 19.0F, 0.0F, false);

		SmallPox_RightArm = new ModelRenderer(this);
		SmallPox_RightArm.setPos(14.0F, 1.5F, 1.0F);
		SmallPox_Arms.addChild(SmallPox_RightArm);
		SmallPox_RightArm.texOffs(45, 60).addBox(-5.0F, -5.5F, -5.0F, 10.0F, 11.0F, 19.0F, 0.0F, false);
		SmallPox_RightArm.texOffs(99, 99).addBox(-4.5F, -5.0F, 12.0F, 9.0F, 10.0F, 19.0F, 0.0F, false);

		SmallPox_TailMain = new ModelRenderer(this);
		SmallPox_TailMain.setPos(0.0F, -32.0F, -3.0F);
		SmallPox_Main.addChild(SmallPox_TailMain);


		SmallPox_Tail = new ModelRenderer(this);
		SmallPox_Tail.setPos(-0.5F, -0.8F, 0.5F);
		SmallPox_TailMain.addChild(SmallPox_Tail);
		SmallPox_Tail.texOffs(132, 129).addBox(-4.0F, -1.2F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);
		SmallPox_Tail.texOffs(0, 139).addBox(-3.0F, 2.8F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
		SmallPox_Tail.texOffs(24, 139).addBox(-2.5F, 6.8F, -2.5F, 5.0F, 10.0F, 5.0F, 0.0F, false);
		SmallPox_Tail.texOffs(0, 0).addBox(-2.0F, 13.8F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		SmallPox_Tail.texOffs(0, 79).addBox(-1.5F, 22.8F, -1.5F, 3.0F, 10.0F, 3.0F, 0.0F, false);	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		SmallPox_Main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

}