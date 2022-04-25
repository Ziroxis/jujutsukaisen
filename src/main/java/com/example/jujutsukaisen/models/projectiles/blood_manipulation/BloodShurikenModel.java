package com.example.jujutsukaisen.models.projectiles.blood_manipulation;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BloodShurikenModel extends EntityModel {
	private final ModelRenderer Blood_Shuriken;

	public BloodShurikenModel() {
		texWidth = 32;
		texHeight = 32;

		Blood_Shuriken = new ModelRenderer(this);
		Blood_Shuriken.setPos(0.0F, 17.0F, -1.0F);
		Blood_Shuriken.texOffs(0, 0).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 2.0F, 6.0F, 0.0F, false);
		Blood_Shuriken.texOffs(6, 10).addBox(-6.0F, 0.0F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
		Blood_Shuriken.texOffs(12, 11).addBox(-2.0F, 0.0F, 3.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		Blood_Shuriken.texOffs(0, 8).addBox(5.0F, 0.0F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
		Blood_Shuriken.texOffs(12, 8).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Blood_Shuriken.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}