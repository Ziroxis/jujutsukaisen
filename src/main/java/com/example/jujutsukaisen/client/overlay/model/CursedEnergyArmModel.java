package com.example.jujutsukaisen.client.overlay.model;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CursedEnergyArmModel<T extends LivingEntity> extends BipedModel<T> {
	private final ModelRenderer Cursed_Energetic_Arm;

	public CursedEnergyArmModel() {
		super(RenderType::entityTranslucent, 1, 0.0F, 64, 64);
		texWidth = 16;
		texHeight = 16;

		Cursed_Energetic_Arm = new ModelRenderer(this);
		Cursed_Energetic_Arm.setPos(-5.0F, 2.0F, 0.0F);
		Cursed_Energetic_Arm.texOffs(8, 8).addBox(-3.0F, 6.0F, -2.01F, 4.0F, 4.0F, 0.0F, 0.0F, false);
		Cursed_Energetic_Arm.texOffs(8, 4).addBox(-3.0F, 6.0F, 2.0F, 4.0F, 4.0F, 0.0F, 0.0F, false);
		Cursed_Energetic_Arm.texOffs(0, 4).addBox(-3.0F, 6.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, false);
		Cursed_Energetic_Arm.texOffs(0, 0).addBox(1.01F, 6.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, false);
		Cursed_Energetic_Arm.texOffs(0, 0).addBox(-3.0F, 10.01F, -2.0F, 4.0F, 0.0F, 4.0F, 0.0F, false);
	}
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.crouching = entityIn.isCrouching();

		this.Cursed_Energetic_Arm.copyFrom(this.rightArm);


		if(!(entityIn instanceof PlayerEntity))
			return;

		AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) entityIn;


		this.swimAmount = clientPlayer.getSwimAmount(ageInTicks);

		this.Cursed_Energetic_Arm.copyFrom(this.rightArm);
	}


	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.Cursed_Energetic_Arm).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}
}