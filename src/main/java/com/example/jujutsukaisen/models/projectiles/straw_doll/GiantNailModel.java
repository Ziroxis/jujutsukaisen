package com.example.jujutsukaisen.models.projectiles.straw_doll;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantNailModel extends EntityModel {
    private final ModelRenderer Giant_Nail;

    public GiantNailModel()
    {
        texWidth = 64;
        texHeight = 64;

        Giant_Nail = new ModelRenderer(this);
        Giant_Nail.setPos(-0.5F, 17.0F, 0.0F);
        Giant_Nail.texOffs(0, 0).addBox(0.5F, -1.0F, -8.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
        Giant_Nail.texOffs(0, 0).addBox(-1.0F, -2.0F, 7.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);
        Giant_Nail.texOffs(13, 18).addBox(0.5F, -2.0F, -7.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);
        Giant_Nail.texOffs(18, 0).addBox(-0.5F, -1.0F, -7.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);
        Giant_Nail.texOffs(0, 17).addBox(1.5F, -1.0F, -7.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Giant_Nail.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
