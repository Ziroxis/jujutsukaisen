package com.example.jujutsukaisen.models.projectiles.basic;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EveningMoonModel extends EntityModel {
	private final ModelRenderer Moon_EveningDrawing;

	public EveningMoonModel() {
		texWidth = 64;
		texHeight = 64;

		Moon_EveningDrawing = new ModelRenderer(this);
		Moon_EveningDrawing.setPos(0.0F, 24.0F, 0.0F);
		Moon_EveningDrawing.texOffs(0, 0).addBox(-10.0F, -15.0F, 0.0F, 20.0F, 5.0F, 3.0F, 0.0F, false);
		Moon_EveningDrawing.texOffs(0, 8).addBox(-9.0F, -13.0F, -1.0F, 18.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Moon_EveningDrawing.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}