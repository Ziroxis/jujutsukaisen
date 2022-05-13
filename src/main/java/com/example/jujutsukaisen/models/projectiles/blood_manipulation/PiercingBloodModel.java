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
		texWidth = 32;
		texHeight = 32;

		Piercing_Blood = new ModelRenderer(this);
		Piercing_Blood.setPos(-0.5F, 19.0F, 0.5F);
		Piercing_Blood.texOffs(0, 0).addBox(-12.4535F, -2.1299F, -0.5F, 17.0F, 2.0F, 1.0F, 0.0F, false);
		Piercing_Blood.texOffs(0, 3).addBox(-0.4535F, -3.1299F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		Piercing_Blood.texOffs(0, 7).addBox(0.5465F, -2.1299F, -1.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		Piercing_Blood.texOffs(0, 5).addBox(0.5465F, -2.1299F, 0.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Piercing_Blood.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}