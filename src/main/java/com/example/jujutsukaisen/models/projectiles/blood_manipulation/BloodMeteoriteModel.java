package com.example.jujutsukaisen.models.projectiles.blood_manipulation;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BloodMeteoriteModel extends EntityModel {
	public ModelRenderer Blood_Meteorite;

	public BloodMeteoriteModel()
	{
		texWidth = 16;
		texHeight = 16;

		Blood_Meteorite = new ModelRenderer(this);
		Blood_Meteorite.setPos(-1.0F, 15.0F, 0.0F);
		Blood_Meteorite.texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Blood_Meteorite.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}