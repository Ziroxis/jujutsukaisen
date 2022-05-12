package com.example.jujutsukaisen.models.projectiles.disaster_flames;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlameArrowModel extends EntityModel {
	private final ModelRenderer Flame_Arrow;

	public FlameArrowModel() {
		texWidth = 128;
		texHeight = 128;

		Flame_Arrow = new ModelRenderer(this);
		Flame_Arrow.setPos(0.0F, 24.0F, 0.0F);
		Flame_Arrow.texOffs(0, 0).addBox(-3.0F, -13.0F, -14.0F, 5.0F, 5.0F, 29.0F, 0.0F, false);
		Flame_Arrow.texOffs(0, 34).addBox(-2.0F, -15.0F, -13.0F, 3.0F, 2.0F, 12.0F, 0.0F, false);
		Flame_Arrow.texOffs(30, 34).addBox(2.0F, -12.0F, -13.0F, 2.0F, 3.0F, 12.0F, 0.0F, false);
		Flame_Arrow.texOffs(0, 0).addBox(-5.0F, -12.0F, -13.0F, 2.0F, 3.0F, 12.0F, 0.0F, false);
		Flame_Arrow.texOffs(0, 0).addBox(-2.0F, -12.0F, -15.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Flame_Arrow.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}