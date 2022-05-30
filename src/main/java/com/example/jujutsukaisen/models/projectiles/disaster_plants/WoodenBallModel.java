package com.example.jujutsukaisen.models.projectiles.disaster_plants;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WoodenBallModel extends EntityModel {

    private final ModelRenderer Wooden_Ball_Spell;

    public WoodenBallModel() {
        texWidth = 64;
        texHeight = 64;

        Wooden_Ball_Spell = new ModelRenderer(this);
        Wooden_Ball_Spell.setPos(0.0F, 24.0F, 0.0F);
        Wooden_Ball_Spell.texOffs(0, 0).addBox(-5.0F, -14.0F, -5.0F, 10.0F, 9.0F, 10.0F, 0.0F, false);
        Wooden_Ball_Spell.texOffs(14, 28).addBox(5.0F, -13.0F, -3.0F, 1.0F, 7.0F, 6.0F, 0.0F, false);
        Wooden_Ball_Spell.texOffs(0, 28).addBox(-6.0F, -13.0F, -3.0F, 1.0F, 7.0F, 6.0F, 0.0F, false);
        Wooden_Ball_Spell.texOffs(24, 20).addBox(-4.0F, -15.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
        Wooden_Ball_Spell.texOffs(0, 19).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
        Wooden_Ball_Spell.texOffs(30, 0).addBox(-4.0F, -13.0F, -6.0F, 8.0F, 7.0F, 1.0F, 0.0F, false);
        Wooden_Ball_Spell.texOffs(28, 29).addBox(-4.0F, -13.0F, 5.0F, 8.0F, 7.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Wooden_Ball_Spell.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
