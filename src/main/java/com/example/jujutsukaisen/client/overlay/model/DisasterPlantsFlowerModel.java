package com.example.jujutsukaisen.client.overlay.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

//TODO make it so it opens and closes
public class DisasterPlantsFlowerModel<T extends LivingEntity> extends BipedModel<T> {

    private final ModelRenderer FlowerArmOverlay;

    public DisasterPlantsFlowerModel() {
        super(RenderType::entityTranslucent, 1, 0.0F, 64, 64);
        texWidth = 16;
        texHeight = 16;

        FlowerArmOverlay = new ModelRenderer(this);
        FlowerArmOverlay.setPos(6.0F, 0.0F, 0.0F);
        FlowerArmOverlay.texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        FlowerArmOverlay.texOffs(0, 5).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        FlowerArmOverlay.texOffs(6, 10).addBox(-1.0F, -2.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        FlowerArmOverlay.texOffs(6, 5).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        FlowerArmOverlay.texOffs(0, 9).addBox(-2.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        FlowerArmOverlay.texOffs(6, 7).addBox(1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);    }
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.crouching = entityIn.isCrouching();

        this.FlowerArmOverlay.copyFrom(this.leftArm);


        if(!(entityIn instanceof PlayerEntity))
            return;

        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) entityIn;


        this.swimAmount = clientPlayer.getSwimAmount(ageInTicks);

        this.FlowerArmOverlay.copyFrom(this.leftArm);
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.FlowerArmOverlay).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
}
