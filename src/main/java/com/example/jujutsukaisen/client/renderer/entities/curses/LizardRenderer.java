package com.example.jujutsukaisen.client.renderer.entities.curses;

import com.example.jujutsukaisen.entities.curses.LizardEntity;
import com.example.jujutsukaisen.models.cursed_spirits.LizardModel;
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
public class LizardRenderer extends MobRenderer<LizardEntity, LizardModel<LizardEntity>> {

    private static final ResourceLocation texture = new ResourceLocation("jujutsukaisen:textures/entities/curses/lizardtexture.png");

    public LizardRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new LizardModel(), 1f);
    }

    @Override
    public void render(LizardEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(LizardEntity entity) {
        return texture;
    }

    public static class Factory implements IRenderFactory<LizardEntity> {

        @Override
        public EntityRenderer<? super LizardEntity> createRenderFor(EntityRendererManager manager) {
            return new LizardRenderer(manager);
        }
    }
}
