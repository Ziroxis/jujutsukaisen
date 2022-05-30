package com.example.jujutsukaisen.models.projectiles.disaster_plants;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class EnergyBlastModel extends EntityModel {

    private final ModelRenderer Energy_Blast_Spell;

    public EnergyBlastModel() {
        texWidth = 256;
        texHeight = 256;

        Energy_Blast_Spell = new ModelRenderer(this);
        Energy_Blast_Spell.setPos(0.0F, 24.0F, 0.0F);
        Energy_Blast_Spell.texOffs(0, 63).addBox(-5.0F, -25.0F, -9.0F, 10.0F, 2.0F, 48.0F, 0.0F, false);
        Energy_Blast_Spell.texOffs(0, 0).addBox(-10.0F, -23.0F, -9.0F, 20.0F, 15.0F, 48.0F, 0.0F, false);
        Energy_Blast_Spell.texOffs(0, 113).addBox(10.0F, -22.0F, -8.0F, 3.0F, 13.0F, 46.0F, 0.0F, false);
        Energy_Blast_Spell.texOffs(70, 67).addBox(-13.0F, -22.0F, -8.0F, 3.0F, 13.0F, 46.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Energy_Blast_Spell.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
