package com.example.jujutsukaisen.client.renderer.entities.npc;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.entities.npc.MaidQuesterEntity;
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
public class MaidQuestRenderer extends MobRenderer<MaidQuesterEntity, HumanoidModel<MaidQuesterEntity>> {

    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(Main.MODID, "textures/entities/npc/maidquester.png");

    public MaidQuestRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new HumanoidModel<>(), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(MaidQuesterEntity p_110775_1_) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<MaidQuesterEntity> {

        @Override
        public EntityRenderer<? super MaidQuesterEntity> createRenderFor(EntityRendererManager manager) {
            return new MaidQuestRenderer(manager);
        }
    }
}
