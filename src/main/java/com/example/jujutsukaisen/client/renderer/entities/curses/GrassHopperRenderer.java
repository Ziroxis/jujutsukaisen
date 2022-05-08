package com.example.jujutsukaisen.client.renderer.entities.curses;

import com.example.jujutsukaisen.entities.curses.GrassHopperEntity;
import com.example.jujutsukaisen.models.cursed_spirits.GrassHopperModel;
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
public class GrassHopperRenderer extends MobRenderer<GrassHopperEntity, GrassHopperModel<GrassHopperEntity>> {

    private static final ResourceLocation texture = new ResourceLocation("jujutsukaisen:textures/entities/curses/grasshoppertexture.png");

    public GrassHopperRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new GrassHopperModel<>(), 1f);
    }

    @Override
    public void render(GrassHopperEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(GrassHopperEntity entity) {
        return texture;
    }

    public static class Factory implements IRenderFactory<GrassHopperEntity> {

        @Override
        public EntityRenderer<? super GrassHopperEntity> createRenderFor(EntityRendererManager manager) {
            return new GrassHopperRenderer(manager);
        }
    }
}
