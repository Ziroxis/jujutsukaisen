package com.example.jujutsukaisen.client.renderer.entities.npc;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.npc.SenseiEntity;
import com.example.jujutsukaisen.models.npcs.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SenseiRenderer extends MobRenderer<SenseiEntity, HumanoidModel<SenseiEntity>> {

    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(Main.MODID, "textures/entities/npc/sensei.png");

    public SenseiRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new HumanoidModel<>(), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(SenseiEntity p_110775_1_) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<SenseiEntity> {

        @Override
        public EntityRenderer<? super SenseiEntity> createRenderFor(EntityRendererManager manager) {
            return new SenseiRenderer(manager);
        }
    }
}
