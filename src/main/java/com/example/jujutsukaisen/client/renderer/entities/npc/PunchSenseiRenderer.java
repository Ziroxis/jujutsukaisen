package com.example.jujutsukaisen.client.renderer.entities.npc;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.npc.PunchSenseiEntity;
import com.example.jujutsukaisen.models.npcs.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class PunchSenseiRenderer extends MobRenderer<PunchSenseiEntity, HumanoidModel<PunchSenseiEntity>> {

    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(Main.MODID, "textures/entities/npc/punchsensei.png");

    public PunchSenseiRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new HumanoidModel<>(), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(PunchSenseiEntity p_110775_1_) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<PunchSenseiEntity> {

        @Override
        public EntityRenderer<? super PunchSenseiEntity> createRenderFor(EntityRendererManager manager) {
            return new PunchSenseiRenderer(manager);
        }
    }
}
