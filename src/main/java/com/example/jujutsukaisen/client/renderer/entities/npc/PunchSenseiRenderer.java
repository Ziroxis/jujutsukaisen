package com.example.jujutsukaisen.client.renderer.entities.npc;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.curses.LizardEntity;
import com.example.jujutsukaisen.entities.npc.PunchSenseiEntity;
import com.example.jujutsukaisen.models.npcs.HumanoidModel;
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
public class PunchSenseiRenderer extends MobRenderer<PunchSenseiEntity, HumanoidModel<PunchSenseiEntity>> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Main.MODID, "textures/entities/npc/punchsensei.png");

    public PunchSenseiRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new HumanoidModel(), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(PunchSenseiEntity entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<PunchSenseiEntity> {

        @Override
        public EntityRenderer<? super PunchSenseiEntity> createRenderFor(EntityRendererManager manager) {
            return new PunchSenseiRenderer(manager);
        }
    }
}
