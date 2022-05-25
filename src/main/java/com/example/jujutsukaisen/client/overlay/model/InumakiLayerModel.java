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
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;

public class InumakiLayerModel<T extends LivingEntity> extends BipedModel<T> {

    private final ModelRenderer Cursed_Speech_Overlay;

    public InumakiLayerModel() {
        super(RenderType::entityTranslucent, 1, 0.0F, 64, 64);
        texWidth = 16;
        texHeight = 16;

        Cursed_Speech_Overlay = new ModelRenderer(this);
        Cursed_Speech_Overlay.setPos(0.0F, 0.0F, 0.0F);
        Cursed_Speech_Overlay.texOffs(0, 4).addBox(-3.0F, -2.0F, -4.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
        Cursed_Speech_Overlay.texOffs(0, 3).addBox(1.0F, -2.0F, -4.0F, 2.0F, 1.0F, 0.0F, 0.0F, false);
        Cursed_Speech_Overlay.texOffs(2, 0).addBox(-4.0F, -3.0F, -4.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
        Cursed_Speech_Overlay.texOffs(0, 0).addBox(3.0F, -3.0F, -4.0F, 1.0F, 3.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.crouching = entityIn.isCrouching();

        this.Cursed_Speech_Overlay.copyFrom(this.hat);


        if(!(entityIn instanceof PlayerEntity))
            return;

        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) entityIn;


        this.swimAmount = clientPlayer.getSwimAmount(ageInTicks);

        this.Cursed_Speech_Overlay.copyFrom(this.hat);
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.Cursed_Speech_Overlay).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
}
