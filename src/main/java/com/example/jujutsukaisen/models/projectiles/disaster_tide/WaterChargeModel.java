package com.example.jujutsukaisen.models.projectiles.disaster_tide;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WaterChargeModel extends EntityModel {
	private final ModelRenderer Water_Charge;

	public WaterChargeModel() {
		texWidth = 64;
		texHeight = 64;

		Water_Charge = new ModelRenderer(this);
		Water_Charge.setPos(-1.0F, 15.0F, 1.0F);
		Water_Charge.texOffs(0, 0).addBox(-3.0F, -5.0F, -7.0F, 6.0F, 9.0F, 15.0F, 0.0F, false);
		Water_Charge.texOffs(29, 11).addBox(-5.0F, -3.0F, -6.0F, 2.0F, 5.0F, 13.0F, 0.0F, false);
		Water_Charge.texOffs(0, 24).addBox(3.0F, -3.0F, -6.0F, 2.0F, 5.0F, 13.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Water_Charge.render(matrixStack, buffer, packedLight, packedOverlay);
	}

}