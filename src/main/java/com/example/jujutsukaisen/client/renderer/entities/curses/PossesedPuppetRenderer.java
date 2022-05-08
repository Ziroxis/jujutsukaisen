package com.example.jujutsukaisen.client.renderer.entities.curses;

import com.example.jujutsukaisen.entities.curses.GrassHopperEntity;
import com.example.jujutsukaisen.entities.curses.PossesedPuppetEntity;
import com.example.jujutsukaisen.models.cursed_spirits.GrassHopperModel;
import com.example.jujutsukaisen.models.cursed_spirits.PossesedPuppetModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class PossesedPuppetRenderer extends MobRenderer<PossesedPuppetEntity, PossesedPuppetModel<PossesedPuppetEntity>> {

    private static final ResourceLocation texture = new ResourceLocation("jujutsukaisen:textures/entities/curses/possesedpuppettexture.png");

    public PossesedPuppetRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new PossesedPuppetModel<>(), 1f);
    }

    @Override
    public void render(PossesedPuppetEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(PossesedPuppetEntity entity) {
        return texture;
    }

    public static class Factory implements IRenderFactory<PossesedPuppetEntity> {

        @Override
        public EntityRenderer<? super PossesedPuppetEntity> createRenderFor(EntityRendererManager manager) {
            return new PossesedPuppetRenderer(manager);
        }
    }
}
