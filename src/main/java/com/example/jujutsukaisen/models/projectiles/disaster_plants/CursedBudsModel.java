package com.example.jujutsukaisen.models.projectiles.disaster_plants;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CursedBudsModel extends EntityModel {
    private final ModelRenderer Cursed_Buds_Spell;

    public CursedBudsModel() {
        texWidth = 32;
        texHeight = 32;

        Cursed_Buds_Spell = new ModelRenderer(this);
        Cursed_Buds_Spell.setPos(0.0F, 24.0F, 0.0F);
        Cursed_Buds_Spell.texOffs(0, 0).addBox(-3.0F, -13.0F, -2.0F, 6.0F, 5.0F, 5.0F, 0.0F, false);
        Cursed_Buds_Spell.texOffs(0, 16).addBox(-2.0F, -12.0F, -3.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
        Cursed_Buds_Spell.texOffs(15, 15).addBox(-2.0F, -12.0F, 3.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
        Cursed_Buds_Spell.texOffs(8, 10).addBox(-4.0F, -12.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        Cursed_Buds_Spell.texOffs(0, 10).addBox(3.0F, -12.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Cursed_Buds_Spell.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
