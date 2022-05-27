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

public class DisasterFlamesVolcanoModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer Volcano_Head;

    public DisasterFlamesVolcanoModel() {
        super(RenderType::entityTranslucent, 1, 0.0F, 64, 64);
        texWidth = 32;
        texHeight = 32;

        Volcano_Head = new ModelRenderer(this);
        Volcano_Head.setPos(0.0F, 0.0F, 0.0F);
        Volcano_Head.texOffs(0, 0).addBox(-3.0F, -10.1F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
        Volcano_Head.texOffs(0, 8).addBox(-2.0F, -12.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
        Volcano_Head.texOffs(12, 8).addBox(-1.0F, -13.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
    }
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.crouching = entityIn.isCrouching();

        this.Volcano_Head.copyFrom(this.head);


        if(!(entityIn instanceof PlayerEntity))
            return;

        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) entityIn;


        this.swimAmount = clientPlayer.getSwimAmount(ageInTicks);

        this.Volcano_Head.copyFrom(this.head);
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.Volcano_Head).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
}