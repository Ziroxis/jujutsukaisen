package com.example.jujutsukaisen.models.cursed_spirits;

import com.example.jujutsukaisen.entities.curses.LizardEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class LizardModel<T extends LizardEntity> extends EntityModel<T> {
	private final ModelRenderer Lizard_Main;
	private final ModelRenderer Lizard_Base;
	private final ModelRenderer Lizard_Tail;
	private final ModelRenderer Lizard_Head;
	private final ModelRenderer Lizard_UpperMouth;
	private final ModelRenderer Lizard_UpperMouth_Details2;
	private final ModelRenderer Lizard_LowerMouth;
	private final ModelRenderer Lizard_LowerMouth_Details;
	private final ModelRenderer Lizard_Legs;
	private final ModelRenderer Lizard_FrontLegs;
	private final ModelRenderer Lizard_Front_LeftLeg;
	private final ModelRenderer Front_LeftLeg2_r1;
	private final ModelRenderer Front_LeftLeg1_r1;
	private final ModelRenderer Lizard_Front_RightLeg;
	private final ModelRenderer Front_RightLeg2_r1;
	private final ModelRenderer Front_RightLeg1_r1;
	private final ModelRenderer Lizard_MiddleLegs;
	private final ModelRenderer Lizard_Middle_LeftLeg;
	private final ModelRenderer Middle_LeftLeg2_r1;
	private final ModelRenderer Middle_LeftLeg1_r1;
	private final ModelRenderer Lizard_Middle_RightLeg;
	private final ModelRenderer Middle_RightLeg2_r1;
	private final ModelRenderer Middle_RightLeg1_r1;
	private final ModelRenderer Lizard_BackLegs;
	private final ModelRenderer Lizard_Back_LeftLeg;
	private final ModelRenderer Back_LeftLeg2_r1;
	private final ModelRenderer Back_LeftLeg1_r1;
	private final ModelRenderer Lizard_Back_RightLeg;
	private final ModelRenderer Back_RightLeg2_r1;
	private final ModelRenderer Back_RightLeg1_r1;

	public LizardModel() {
		texWidth = 512;
		texHeight = 512;

		Lizard_Main = new ModelRenderer(this);
		Lizard_Main.setPos(-59.0F, 48.0F, 0.0F);


		Lizard_Base = new ModelRenderer(this);
		Lizard_Base.setPos(11.1667F, -39.9167F, -0.5F);
		Lizard_Main.addChild(Lizard_Base);
		Lizard_Base.texOffs(0, 188).addBox(30.8333F, -16.0833F, -10.5F, 18.0F, 34.0F, 21.0F, 0.0F, false);
		Lizard_Base.texOffs(0, 125).addBox(6.8333F, -17.5833F, -13.0F, 24.0F, 37.0F, 26.0F, 0.0F, false);
		Lizard_Base.texOffs(0, 27).addBox(-14.1667F, -20.0833F, -14.5F, 27.0F, 42.0F, 29.0F, 0.0F, false);
		Lizard_Base.texOffs(116, 99).addBox(-32.1667F, -17.5833F, -13.0F, 24.0F, 37.0F, 26.0F, 0.0F, false);
		Lizard_Base.texOffs(179, 184).addBox(-48.1667F, -16.0833F, -10.5F, 20.0F, 34.0F, 21.0F, 0.0F, false);
		Lizard_Base.texOffs(0, 0).addBox(-47.1667F, -36.0833F, 0.0F, 95.0F, 27.0F, 0.0F, 0.0F, false);

		Lizard_Tail = new ModelRenderer(this);
		Lizard_Tail.setPos(-42.1667F, 0.9167F, 0.0F);
		Lizard_Base.addChild(Lizard_Tail);
		Lizard_Tail.texOffs(100, 162).addBox(-19.0F, -13.0F, -8.5F, 33.0F, 26.0F, 17.0F, 0.0F, false);
		Lizard_Tail.texOffs(112, 48).addBox(-50.0F, -10.0F, -7.5F, 40.0F, 20.0F, 15.0F, 0.0F, false);
		Lizard_Tail.texOffs(0, 98).addBox(-87.0F, -7.0F, -6.5F, 58.0F, 14.0F, 13.0F, 0.0F, false);
		Lizard_Tail.texOffs(83, 27).addBox(-135.0F, -5.0F, -5.5F, 67.0F, 10.0F, 11.0F, 0.0F, false);

		Lizard_Head = new ModelRenderer(this);
		Lizard_Head.setPos(46.8333F, 2.4167F, 0.0F);
		Lizard_Base.addChild(Lizard_Head);
		Lizard_Head.texOffs(78, 205).addBox(-2.0F, -16.5F, -9.5F, 8.0F, 29.0F, 19.0F, 0.0F, false);
		Lizard_Head.texOffs(205, 66).addBox(4.0F, -18.0F, -8.5F, 12.0F, 32.0F, 17.0F, 0.0F, false);

		Lizard_UpperMouth = new ModelRenderer(this);
		Lizard_UpperMouth.setPos(14.9333F, -1.6667F, 0.0333F);
		Lizard_Head.addChild(Lizard_UpperMouth);
		Lizard_UpperMouth.texOffs(190, 115).addBox(-1.0333F, 2.1667F, -7.2333F, 9.0F, 2.0F, 8.0F, 0.0F, false);
		Lizard_UpperMouth.texOffs(152, 85).addBox(-1.0333F, 2.1667F, -0.7333F, 9.0F, 2.0F, 8.0F, 0.0F, false);
		Lizard_UpperMouth.texOffs(228, 0).addBox(-3.9333F, -15.8333F, -7.5333F, 12.0F, 19.0F, 15.0F, 0.0F, false);

		Lizard_UpperMouth_Details2 = new ModelRenderer(this);
		Lizard_UpperMouth_Details2.setPos(-1.4788F, 1.1258F, -0.0242F);
		Lizard_UpperMouth.addChild(Lizard_UpperMouth_Details2);
		Lizard_UpperMouth_Details2.texOffs(106, 131).addBox(3.0455F, 1.0409F, 4.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(0, 131).addBox(5.0455F, 0.9409F, 5.1909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(129, 105).addBox(7.0455F, 1.0409F, 4.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(129, 99).addBox(7.1455F, 0.9409F, 2.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(129, 93).addBox(7.0455F, 1.0409F, 0.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(106, 125).addBox(7.3455F, 1.0909F, -1.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(98, 125).addBox(7.0455F, 0.8409F, -3.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(74, 125).addBox(7.1455F, 1.0409F, -5.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(0, 125).addBox(7.0455F, 0.8409F, -7.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(115, 48).addBox(5.0455F, 1.1409F, -7.1091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_UpperMouth_Details2.texOffs(112, 54).addBox(3.0455F, 1.0409F, -7.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		Lizard_LowerMouth = new ModelRenderer(this);
		Lizard_LowerMouth.setPos(13.9333F, 8.7F, 0.0333F);
		Lizard_Head.addChild(Lizard_LowerMouth);
		Lizard_LowerMouth.texOffs(256, 107).addBox(-0.0333F, -1.9F, -7.2333F, 9.0F, 2.0F, 8.0F, 0.0F, false);
		Lizard_LowerMouth.texOffs(254, 170).addBox(-0.0333F, -1.9F, -0.7333F, 9.0F, 2.0F, 8.0F, 0.0F, false);
		Lizard_LowerMouth.texOffs(200, 162).addBox(-2.9333F, -1.2F, -7.5333F, 12.0F, 6.0F, 15.0F, 0.0F, false);

		Lizard_LowerMouth_Details = new ModelRenderer(this);
		Lizard_LowerMouth_Details.setPos(0.5212F, 1.7136F, -0.0242F);
		Lizard_LowerMouth.addChild(Lizard_LowerMouth_Details);
		Lizard_LowerMouth_Details.texOffs(107, 48).addBox(2.0455F, -4.9136F, 4.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(0, 104).addBox(4.0455F, -5.0136F, 5.1909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(99, 48).addBox(6.0455F, -4.9136F, 4.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(0, 98).addBox(6.1455F, -5.0136F, 2.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(91, 48).addBox(6.0455F, -5.4136F, 0.9909F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(83, 48).addBox(6.3455F, -4.8636F, -1.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(83, 27).addBox(6.0455F, -5.1136F, -3.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(16, 48).addBox(6.1455F, -4.9136F, -5.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(8, 48).addBox(6.0455F, -5.1136F, -7.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(0, 48).addBox(4.0455F, -4.8136F, -7.1091F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		Lizard_LowerMouth_Details.texOffs(21, 27).addBox(2.0455F, -4.9136F, -7.0091F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		Lizard_Legs = new ModelRenderer(this);
		Lizard_Legs.setPos(0.0F, -34.0F, 0.0F);
		Lizard_Main.addChild(Lizard_Legs);


		Lizard_FrontLegs = new ModelRenderer(this);
		Lizard_FrontLegs.setPos(32.0F, 7.0F, 0.0F);
		Lizard_Legs.addChild(Lizard_FrontLegs);


		Lizard_Front_LeftLeg = new ModelRenderer(this);
		Lizard_Front_LeftLeg.setPos(-0.5F, 0.4337F, 8.7896F);
		Lizard_FrontLegs.addChild(Lizard_Front_LeftLeg);
		Lizard_Front_LeftLeg.texOffs(256, 232).addBox(-3.5F, 12.4346F, 1.7104F, 7.0F, 14.0F, 7.0F, 0.0F, false);
		Lizard_Front_LeftLeg.texOffs(90, 143).addBox(-4.0F, 26.3663F, 1.7104F, 8.0F, 0.0F, 10.0F, 0.0F, false);

		Front_LeftLeg2_r1 = new ModelRenderer(this);
		Front_LeftLeg2_r1.setPos(0.0F, 17.6846F, 4.5095F);
		Lizard_Front_LeftLeg.addChild(Front_LeftLeg2_r1);
		Front_LeftLeg2_r1.texOffs(239, 152).addBox(-4.0F, -13.25F, -3.0009F, 8.0F, 10.0F, 8.0F, 0.0F, false);

		Front_LeftLeg1_r1 = new ModelRenderer(this);
		Front_LeftLeg1_r1.setPos(-1.5F, 9.0663F, 3.2104F);
		Lizard_Front_LeftLeg.addChild(Front_LeftLeg1_r1);
		Front_LeftLeg1_r1.texOffs(36, 243).addBox(-3.0F, -24.5F, -2.0F, 9.0F, 24.0F, 9.0F, 0.0F, false);

		Lizard_Front_RightLeg = new ModelRenderer(this);
		Lizard_Front_RightLeg.setPos(-0.5F, 0.4337F, -9.7896F);
		Lizard_FrontLegs.addChild(Lizard_Front_RightLeg);
		Lizard_Front_RightLeg.texOffs(0, 27).addBox(-3.5F, 12.4346F, -7.7104F, 7.0F, 14.0F, 7.0F, 0.0F, false);
		Lizard_Front_RightLeg.texOffs(102, 83).addBox(-4.0F, 26.3663F, -10.7104F, 8.0F, 0.0F, 10.0F, 0.0F, false);

		Front_RightLeg2_r1 = new ModelRenderer(this);
		Front_RightLeg2_r1.setPos(0.0F, 17.6846F, -3.5095F);
		Lizard_Front_RightLeg.addChild(Front_RightLeg2_r1);
		Front_RightLeg2_r1.texOffs(74, 125).addBox(-4.0F, -13.25F, -4.9991F, 8.0F, 10.0F, 8.0F, 0.0F, false);

		Front_RightLeg1_r1 = new ModelRenderer(this);
		Front_RightLeg1_r1.setPos(-1.5F, 9.0663F, -2.2104F);
		Lizard_Front_RightLeg.addChild(Front_RightLeg1_r1);
		Front_RightLeg1_r1.texOffs(184, 239).addBox(-3.0F, -24.5F, -7.0F, 9.0F, 24.0F, 9.0F, 0.0F, false);

		Lizard_MiddleLegs = new ModelRenderer(this);
		Lizard_MiddleLegs.setPos(0.0F, 5.0F, 0.0F);
		Lizard_Legs.addChild(Lizard_MiddleLegs);


		Lizard_Middle_LeftLeg = new ModelRenderer(this);
		Lizard_Middle_LeftLeg.setPos(-0.5F, 2.4337F, 6.7896F);
		Lizard_MiddleLegs.addChild(Lizard_Middle_LeftLeg);
		Lizard_Middle_LeftLeg.texOffs(72, 253).addBox(-3.5F, 12.4346F, 1.7104F, 7.0F, 14.0F, 7.0F, 0.0F, false);
		Lizard_Middle_LeftLeg.texOffs(134, 83).addBox(-4.0F, 26.3663F, 1.7104F, 8.0F, 0.0F, 10.0F, 0.0F, false);

		Middle_LeftLeg2_r1 = new ModelRenderer(this);
		Middle_LeftLeg2_r1.setPos(0.0F, 17.6846F, 4.5095F);
		Lizard_Middle_LeftLeg.addChild(Middle_LeftLeg2_r1);
		Middle_LeftLeg2_r1.texOffs(190, 0).addBox(-4.0F, -13.25F, -3.0009F, 8.0F, 10.0F, 8.0F, 0.0F, false);

		Middle_LeftLeg1_r1 = new ModelRenderer(this);
		Middle_LeftLeg1_r1.setPos(-1.5F, 9.0663F, 3.2104F);
		Lizard_Middle_LeftLeg.addChild(Middle_LeftLeg1_r1);
		Middle_LeftLeg1_r1.texOffs(0, 243).addBox(-3.0F, -24.5F, -2.0F, 9.0F, 24.0F, 9.0F, 0.0F, false);

		Lizard_Middle_RightLeg = new ModelRenderer(this);
		Lizard_Middle_RightLeg.setPos(-0.5F, 1.4337F, -7.7896F);
		Lizard_MiddleLegs.addChild(Lizard_Middle_RightLeg);
		Lizard_Middle_RightLeg.texOffs(240, 183).addBox(-3.5F, 13.4346F, -8.7104F, 7.0F, 14.0F, 7.0F, 0.0F, false);
		Lizard_Middle_RightLeg.texOffs(118, 83).addBox(-4.0F, 27.3663F, -11.7104F, 8.0F, 0.0F, 10.0F, 0.0F, false);

		Middle_RightLeg2_r1 = new ModelRenderer(this);
		Middle_RightLeg2_r1.setPos(0.0F, 18.6846F, -4.5095F);
		Lizard_Middle_RightLeg.addChild(Middle_RightLeg2_r1);
		Middle_RightLeg2_r1.texOffs(57, 188).addBox(-4.0F, -13.25F, -4.9991F, 8.0F, 10.0F, 8.0F, 0.0F, false);

		Middle_RightLeg1_r1 = new ModelRenderer(this);
		Middle_RightLeg1_r1.setPos(-1.5F, 10.0663F, -3.2104F);
		Lizard_Middle_RightLeg.addChild(Middle_RightLeg1_r1);
		Middle_RightLeg1_r1.texOffs(220, 239).addBox(-3.0F, -24.5F, -7.0F, 9.0F, 24.0F, 9.0F, 0.0F, false);

		Lizard_BackLegs = new ModelRenderer(this);
		Lizard_BackLegs.setPos(-29.0F, 5.0F, 0.0F);
		Lizard_Legs.addChild(Lizard_BackLegs);


		Lizard_Back_LeftLeg = new ModelRenderer(this);
		Lizard_Back_LeftLeg.setPos(0.0F, -0.8993F, 6.9363F);
		Lizard_BackLegs.addChild(Lizard_Back_LeftLeg);
		Lizard_Back_LeftLeg.texOffs(100, 253).addBox(-3.5F, 15.7676F, 0.5637F, 7.0F, 14.0F, 7.0F, 0.0F, false);
		Lizard_Back_LeftLeg.texOffs(0, 135).addBox(-4.0F, 29.6993F, 0.5637F, 8.0F, 0.0F, 10.0F, 0.0F, false);

		Back_LeftLeg2_r1 = new ModelRenderer(this);
		Back_LeftLeg2_r1.setPos(1.5F, 21.0176F, 2.3628F);
		Lizard_Back_LeftLeg.addChild(Back_LeftLeg2_r1);
		Back_LeftLeg2_r1.texOffs(228, 37).addBox(-7.0F, -13.25F, -3.0009F, 11.0F, 10.0F, 11.0F, 0.0F, false);

		Back_LeftLeg1_r1 = new ModelRenderer(this);
		Back_LeftLeg1_r1.setPos(0.0F, 12.3993F, 1.0637F);
		Lizard_Back_LeftLeg.addChild(Back_LeftLeg1_r1);
		Back_LeftLeg1_r1.texOffs(132, 227).addBox(-7.0F, -25.5F, -2.0F, 14.0F, 25.0F, 12.0F, 0.0F, false);

		Lizard_Back_RightLeg = new ModelRenderer(this);
		Lizard_Back_RightLeg.setPos(0.0F, -0.8993F, -6.9363F);
		Lizard_BackLegs.addChild(Lizard_Back_RightLeg);
		Lizard_Back_RightLeg.texOffs(246, 58).addBox(-3.5F, 15.7676F, -8.5637F, 7.0F, 14.0F, 7.0F, 0.0F, false);
		Lizard_Back_RightLeg.texOffs(0, 125).addBox(-4.0F, 29.6993F, -11.5637F, 8.0F, 0.0F, 10.0F, 0.0F, false);

		Back_RightLeg2_r1 = new ModelRenderer(this);
		Back_RightLeg2_r1.setPos(1.5F, 21.0176F, -3.3628F);
		Lizard_Back_RightLeg.addChild(Back_RightLeg2_r1);
		Back_RightLeg2_r1.texOffs(132, 205).addBox(-7.0F, -13.25F, -7.9991F, 11.0F, 10.0F, 11.0F, 0.0F, false);

		Back_RightLeg1_r1 = new ModelRenderer(this);
		Back_RightLeg1_r1.setPos(0.0F, 12.3993F, -2.0637F);
		Lizard_Back_RightLeg.addChild(Back_RightLeg1_r1);
		Back_RightLeg1_r1.texOffs(216, 115).addBox(-7.0F, -25.5F, -10.0F, 14.0F, 25.0F, 12.0F, 0.0F, false);	}



	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Lizard_Main.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}