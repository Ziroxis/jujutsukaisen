package com.example.jujutsukaisen.models.projectiles.disaster_flames;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EmberInsectModel extends EntityModel {
	private final ModelRenderer Ember_Insect;

	public EmberInsectModel() {
		texWidth = 64;
		texHeight = 64;

		Ember_Insect = new ModelRenderer(this);
		Ember_Insect.setPos(0.0F, 24.0F, 0.0F);
		Ember_Insect.texOffs(0, 0).addBox(-4.0F, -14.0F, -2.0F, 7.0F, 2.0F, 7.0F, 0.0F, false);
		Ember_Insect.texOffs(15, 10).addBox(-7.0F, -15.0F, -2.0F, 4.0F, 1.0F, 7.0F, 0.0F, false);
		Ember_Insect.texOffs(0, 9).addBox(2.0F, -15.0F, -2.0F, 4.0F, 1.0F, 7.0F, 0.0F, false);
		Ember_Insect.texOffs(0, 2).addBox(-3.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Ember_Insect.texOffs(0, 0).addBox(1.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Ember_Insect.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}