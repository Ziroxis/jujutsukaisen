package com.example.jujutsukaisen.client.overlay.renderer;

import com.example.jujutsukaisen.client.overlay.model.DisasterPlantsFlowerModel;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.init.ModValues;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class DisasterPlantsFlowerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation resourceLocation =
            new ResourceLocation("jujutsukaisen:textures/overlay/disaster_plants_overlay.png");

    private DisasterPlantsFlowerModel model = new DisasterPlantsFlowerModel();

    public DisasterPlantsFlowerRenderer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {


        if (!(entitylivingbaseIn instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) entitylivingbaseIn;
        IEntityStats statsProps = EntityStatsCapability.get(player);

        if (statsProps.getCurse().equals(ModValues.PLANTS))
        {
            matrixStackIn.pushPose();
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, this.model.renderType(resourceLocation), false, false);
            this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }

}
