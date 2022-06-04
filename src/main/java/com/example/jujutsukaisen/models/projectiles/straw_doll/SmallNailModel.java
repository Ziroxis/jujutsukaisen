package com.example.jujutsukaisen.models.projectiles.straw_doll;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmallNailModel extends EntityModel {
    private final ModelRenderer Nail;

    public SmallNailModel() {
        texWidth = 16;
        texHeight = 16;

        Nail = new ModelRenderer(this);
        Nail.setPos(-0.5F, 17.0F, -1.0F);
        Nail.texOffs(0, 0).addBox(-0.5F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        Nail.texOffs(0, 0).addBox(-0.5F, -2.0F, 3.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        Nail.texOffs(0, 4).addBox(0.0F, 0.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Nail.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
