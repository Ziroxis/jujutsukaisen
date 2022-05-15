package com.example.jujutsukaisen.models.projectiles.blood_manipulation;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PiercingBloodModel extends EntityModel {
	private final ModelRenderer Piercing_Blood;

	public PiercingBloodModel() {
		texWidth = 64;
		texHeight = 64;

		Piercing_Blood = new ModelRenderer(this);
		Piercing_Blood.setPos(0.0F, 24.0F, 0.0F);
		Piercing_Blood.texOffs(26, 33).addBox(1.0F, -9.0F, -10.0F, 1.0F, 2.0F, 12.0F, 0.0F, false);
		Piercing_Blood.texOffs(0, 33).addBox(-3.0F, -9.0F, -10.0F, 1.0F, 2.0F, 12.0F, 0.0F, false);
		Piercing_Blood.texOffs(0, 0).addBox(-2.0F, -10.0F, -12.0F, 3.0F, 4.0F, 29.0F, 0.0F, false);
		Piercing_Blood.texOffs(0, 0).addBox(-1.0F, -13.0F, -10.0F, 1.0F, 3.0F, 13.0F, 0.0F, false);	}
	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Piercing_Blood.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}