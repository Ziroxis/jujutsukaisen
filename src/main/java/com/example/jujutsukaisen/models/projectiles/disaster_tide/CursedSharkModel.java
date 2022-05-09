package com.example.jujutsukaisen.models.projectiles.disaster_tide;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CursedSharkModel extends EntityModel {
	private final ModelRenderer Cursed_Shark;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer nose;
	private final ModelRenderer tail;
	private final ModelRenderer tail_fin;
	private final ModelRenderer back_fin;
	private final ModelRenderer left_fin;
	private final ModelRenderer right_fin;

	public CursedSharkModel() {
		texWidth = 64;
		texHeight = 64;

		Cursed_Shark = new ModelRenderer(this);
		Cursed_Shark.setPos(0.0F, 24.0F, 0.0F);
		

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, -3.0F);
		Cursed_Shark.addChild(body);
		body.texOffs(0, 0).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -4.0F, 0.0F);
		body.addChild(head);
		head.texOffs(29, 0).addBox(-5.0F, -3.0F, -6.0F, 10.0F, 7.0F, 6.0F, 0.0F, false);

		nose = new ModelRenderer(this);
		nose.setPos(0.0F, 4.0F, -10.0F);
		head.addChild(nose);
		nose.texOffs(29, 38).addBox(-3.0F, -7.0F, 0.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setPos(0.0F, -2.5F, 16.0F);
		body.addChild(tail);
		tail.texOffs(0, 20).addBox(-3.0F, -3.3257F, -2.9924F, 6.0F, 6.0F, 11.0F, 0.0F, false);

		tail_fin = new ModelRenderer(this);
		tail_fin.setPos(0.0F, 0.1743F, 7.0076F);
		tail.addChild(tail_fin);
		tail_fin.texOffs(28, 31).addBox(-5.0F, -2.5F, -1.0F, 10.0F, 1.0F, 6.0F, 0.0F, false);
		tail_fin.texOffs(23, 20).addBox(-5.0F, 0.5F, -1.0F, 10.0F, 1.0F, 6.0F, 0.0F, false);

		back_fin = new ModelRenderer(this);
		back_fin.setPos(0.0F, -7.0F, 5.0F);
		body.addChild(back_fin);
		back_fin.texOffs(31, 44).addBox(-0.5F, 0.25F, -0.5F, 1.0F, 4.0F, 5.0F, 0.0F, false);
		back_fin.texOffs(0, 0).addBox(-0.5F, -3.75F, -2.5F, 1.0F, 4.0F, 5.0F, 0.0F, false);

		left_fin = new ModelRenderer(this);
		left_fin.setPos(3.0F, -2.0F, 5.0F);
		body.addChild(left_fin);
		left_fin.texOffs(18, 38).addBox(-1.0F, -4.0F, -1.5F, 2.0F, 4.0F, 7.0F, 0.0F, false);

		right_fin = new ModelRenderer(this);
		right_fin.setPos(-3.0F, -2.0F, 5.0F);
		body.addChild(right_fin);
		right_fin.texOffs(0, 37).addBox(-1.0F, -4.0F, -1.5F, 2.0F, 4.0F, 7.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Cursed_Shark.render(matrixStack, buffer, packedLight, packedOverlay);
	}

}