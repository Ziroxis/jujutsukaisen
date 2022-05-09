package com.example.jujutsukaisen.models.projectiles.disaster_tide;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CursedFishModel extends EntityModel {
	private final ModelRenderer Cursed_Shark;
	private final ModelRenderer body;
	private final ModelRenderer waist;
	private final ModelRenderer head;
	private final ModelRenderer leftFin;
	private final ModelRenderer rightFin;
	private final ModelRenderer tailfin;

	public CursedFishModel() {
		texWidth = 64;
		texHeight = 64;

		Cursed_Shark = new ModelRenderer(this);
		Cursed_Shark.setPos(0.0F, 16.0F, -2.0F);
		

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		Cursed_Shark.addChild(body);
		body.texOffs(0, 0).addBox(-2.0F, -4.0F, 1.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
		body.texOffs(10, 13).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);
		body.texOffs(18, 7).addBox(-2.0F, 0.0F, 3.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

		waist = new ModelRenderer(this);
		waist.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(waist);
		

		head = new ModelRenderer(this);
		head.setPos(0.0F, -2.0F, 0.0F);
		Cursed_Shark.addChild(head);
		head.texOffs(0, 0).addBox(-0.9992F, -2.0008F, -3.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		head.texOffs(15, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);

		leftFin = new ModelRenderer(this);
		leftFin.setPos(1.0F, -1.0F, 0.0F);
		Cursed_Shark.addChild(leftFin);
		leftFin.texOffs(10, 20).addBox(0.0F, 0.0F, 0.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);

		rightFin = new ModelRenderer(this);
		rightFin.setPos(-1.0F, -1.0F, 0.0F);
		Cursed_Shark.addChild(rightFin);
		rightFin.texOffs(0, 19).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);

		tailfin = new ModelRenderer(this);
		tailfin.setPos(0.0F, 0.0F, 8.0F);
		Cursed_Shark.addChild(tailfin);
		tailfin.texOffs(0, 11).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
	}



	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Cursed_Shark.render(matrixStack, buffer, packedLight, packedOverlay);
	}


}