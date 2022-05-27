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

public class DisasterTidesTailModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer Disaster_Tides_Tail;

    public DisasterTidesTailModel() {
        super(RenderType::entityTranslucent, 1, 0.0F, 64, 64);
        texWidth = 32;
        texHeight = 32;


        Disaster_Tides_Tail = new ModelRenderer(this);
        Disaster_Tides_Tail.setPos(0.0F, 0.0F, 0.0F);
        Disaster_Tides_Tail.texOffs(0, 0).addBox(-2.0F, 2.0F, 2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
        Disaster_Tides_Tail.texOffs(0, 6).addBox(-1.0F, 4.0F, 2.0F, 2.0F, 8.0F, 3.0F, 0.0F, false);
        Disaster_Tides_Tail.texOffs(7, 6).addBox(-1.0F, 12.0F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.crouching = entityIn.isCrouching();

        this.Disaster_Tides_Tail.copyFrom(this.body);


        if(!(entityIn instanceof PlayerEntity))
            return;

        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) entityIn;


        this.swimAmount = clientPlayer.getSwimAmount(ageInTicks);

        this.Disaster_Tides_Tail.copyFrom(this.body);
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.Disaster_Tides_Tail).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
}
