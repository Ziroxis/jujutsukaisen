package com.example.jujutsukaisen.models.projectiles.disaster_tide;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WaterFlowModel extends EntityModel {
	private final ModelRenderer Water_Flow;

	public WaterFlowModel() {
		texWidth = 32;
		texHeight = 32;

		Water_Flow = new ModelRenderer(this);
		Water_Flow.setPos(0.0F, 14.0F, 0.0F);
		Water_Flow.texOffs(0, 0).addBox(-2.0F, -5.0F, -3.0F, 3.0F, 7.0F, 12.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Water_Flow.render(matrixStack, buffer, packedLight, packedOverlay);
	}

}