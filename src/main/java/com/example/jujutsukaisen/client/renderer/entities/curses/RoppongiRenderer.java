package com.example.jujutsukaisen.client.renderer.entities.curses;

import com.example.jujutsukaisen.entities.curses.RoppongiEntity;
import com.example.jujutsukaisen.models.cursed_spirits.RoppongiModel;
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
public class RoppongiRenderer extends MobRenderer<RoppongiEntity, RoppongiModel<RoppongiEntity>> {

    private static final ResourceLocation texture = new ResourceLocation("jujutsukaisen:textures/entities/curses/roppongitexture.png");

    public RoppongiRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new RoppongiModel(), 1f);
    }

    @Override
    public void render(RoppongiEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static class Factory implements IRenderFactory<RoppongiEntity> {

        @Override
        public EntityRenderer<? super RoppongiEntity> createRenderFor(EntityRendererManager manager) {
            return new RoppongiRenderer(manager);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(RoppongiEntity entity) {
        return texture;
    }
}
