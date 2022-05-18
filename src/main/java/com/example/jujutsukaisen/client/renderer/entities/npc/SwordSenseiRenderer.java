package com.example.jujutsukaisen.client.renderer.entities.npc;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.npc.SwordSenseiEntity;
import com.example.jujutsukaisen.models.npcs.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unchecked")
public class SwordSenseiRenderer extends MobRenderer<SwordSenseiEntity, HumanoidModel<SwordSenseiEntity>> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Main.MODID, "textures/entities/npc/swordsensei.png");

    public SwordSenseiRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new HumanoidModel<>(), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(SwordSenseiEntity p_110775_1_) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<SwordSenseiEntity> {

        @Override
        public EntityRenderer<? super SwordSenseiEntity> createRenderFor(EntityRendererManager manager) {
            return new SwordSenseiRenderer(manager);
        }
    }
}
