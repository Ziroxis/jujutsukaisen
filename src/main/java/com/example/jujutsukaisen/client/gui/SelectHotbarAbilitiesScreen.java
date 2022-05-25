package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.Main;
import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.api.ability.Ability;
import com.example.jujutsukaisen.api.ability.AbilityCategories;
import com.example.jujutsukaisen.client.gui.buttons.NoTextureButton;
import com.example.jujutsukaisen.client.gui.buttons.TexturedIconButton;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.init.ModResources;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.ability.CRemoveAbilityPacket;
import com.example.jujutsukaisen.networking.client.ability.CSyncAbilityDataPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;


import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SelectHotbarAbilitiesScreen extends Screen
{
	protected PlayerEntity player;
	private AbilitiesListScreenPanel abilitiesList;
	public int slotSelected = -1;
	public int abilitySelected = -1;
	protected boolean forceUpdate = false;
	
	private IAbilityData abilityDataProps;

	public SelectHotbarAbilitiesScreen(PlayerEntity player)
	{
		super(new StringTextComponent(""));
		this.player = player;
		this.minecraft = Minecraft.getInstance();

		this.abilityDataProps = AbilityDataCapability.get(player);
	}

	@Override
	public void render(MatrixStack matrixStack, int x, int y, float f)
	{
		this.renderBackground(matrixStack);

		Minecraft.getInstance().getTextureManager().bind(ModResources.ABILITIES);

		int posX = this.width;
		int posY = this.height;
		
		this.blit(matrixStack, (posX - 250) / 2, (posY - 230) / 2, 0, 0, 256, 256);
		this.blit(matrixStack, (posX - 250) / 2, posY - 60, 0, 0, 256, 256);
		
		Beapi.drawStringWithBorder(this.minecraft.font, matrixStack, this.abilityDataProps.getCombatBarSet() + "", posX / 2 - 110, posY - 24, Beapi.hexToRGB("#FFFFFF").getRGB());
		
		this.minecraft.getTextureManager().bind(ModResources.WIDGETS);
		RenderSystem.enableBlend();
		
		for (int i = 0; i < 8; i++)
		{
			if (this.slotSelected == i + (this.abilityDataProps.getCombatBarSet() * 8))
				RenderSystem.color4f(0, 0, 1, 1);
			
			this.blit(matrixStack, (posX / 2) - 102 + (i * 25), posY - 33, 0, 0, 23, 23);
			RenderSystem.color4f(1, 1, 1, 1);
		}

		for (int i = 0; i < 8; i++)
		{
			int j = i + (this.abilityDataProps.getCombatBarSet() * 8);
			RenderSystem.blendFuncSeparate(770, 771, 1, 0);
			if (this.abilityDataProps.getEquippedAbility(j) != null)
				Beapi.drawAbilityIcon(Beapi.getResourceName(this.abilityDataProps.getEquippedAbility(j).getName()), (posX / 2) - 98 + (i * 25), posY - 29, 16, 16);
		}
		
		RenderSystem.disableBlend();

		// WyHelper.startGlScissor((posX - 220) / 2, (posY - 200) / 2, 215, 130);

		if (this.abilitiesList != null)
			this.abilitiesList.render(matrixStack, x, y, f);

		// WyHelper.endGlScissor();

		super.render(matrixStack, x, y, f);
	}

	@Override
	public void init()
	{
		int posX = this.width;
		int posY = this.height;

		int posX2 = this.width / 2;
		int posY2 = this.height / 2;
		
		// Side menu buttons
		int idx = 0;
		for(AbilityCategories.AbilityCategory category : AbilityCategories.AbilityCategory.values())
		{
			if(category == AbilityCategories.AbilityCategory.ALL)
				continue;
			
			Ability abl = this.abilityDataProps.getUnlockedAbilities(category).stream().findFirst().orElse(null);
			if (abl != null)
			{
				if(idx == 4)
				{
					posX2 += 260;
					posY2 -= 140;
				}
				
				int posY3 = posY2 - 100 + (idx * 70) / 2;
				String iconName = abl.getName();
				ResourceLocation icon = category.getIcon(this.player);
				if(icon == null)
					icon = new ResourceLocation(Main.MODID, "textures/abilities/" + Beapi.getResourceName(iconName) + ".png");
				TexturedIconButton button = new TexturedIconButton(ModResources.TAB, posX2 - 145, posY3, 30, 30, new StringTextComponent(""), (btn) -> this.updateListScreen(category));
				button = button.setTextureInfo(posX2 - 146, posY3 - 3, 32, 32).setIconInfo(icon, posX2 - 142, posY3 + 2, 1.45);
				this.addButton(button);
				idx++;
			}
		}

		// Ability slots
		for (int i = 0; i < 8; i++)
		{
			RenderSystem.enableBlend();
			final int id = i + (this.abilityDataProps.getCombatBarSet() * 8);
			NoTextureButton slotButton = new NoTextureButton((posX / 2) - 101 + (i * 25), posY - 31, 22, 21, new StringTextComponent(""), (btn) ->
			{
				if (this.slotSelected != id)
				{
					Ability ability = this.abilityDataProps.getEquippedAbility(id);
					if(ability == null)
						this.slotSelected = id;
					else
					{
						if(ability.isOnStandby())
							this.slotSelected = id;
					}
				}
				else
				{			
					Ability ability = this.abilityDataProps.getEquippedAbility(this.slotSelected);
					if(ability != null && ability.isOnStandby())
					{
						PacketHandler.sendToServer(new CRemoveAbilityPacket(this.slotSelected));
						this.abilityDataProps.setEquippedAbility(this.slotSelected, null);
					}
				}
			});
			slotButton.setFake();
			this.addButton(slotButton);
		}
		
	}

	public void updateListScreen(AbilityCategories.AbilityCategory category)
	{		
		this.children.remove(this.abilitiesList);
		
		List<Ability> list = this.abilityDataProps.getUnlockedAbilities(category);
		Ability[] arr = new Ability[list.size()];
		arr = list.toArray(arr);
		
		this.abilitiesList = new AbilitiesListScreenPanel(this, this.abilityDataProps, arr);	
		this.children.add(this.abilitiesList);
		this.setFocused(this.abilitiesList);
	}

	@Override
	public void onClose()
	{
		if(this.forceUpdate)
			PacketHandler.sendToServer(new CSyncAbilityDataPacket(this.abilityDataProps));
		super.onClose();
	}

	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	public void markDirty()
	{
		this.forceUpdate = true;
	}
}
