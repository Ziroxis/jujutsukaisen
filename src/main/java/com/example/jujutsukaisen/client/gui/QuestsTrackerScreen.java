package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.client.gui.buttons.TexturedIconButton;
import com.example.jujutsukaisen.data.ability.AbilityDataCapability;
import com.example.jujutsukaisen.data.ability.IAbilityData;
import com.example.jujutsukaisen.data.entity.entitystats.EntityStatsCapability;
import com.example.jujutsukaisen.data.entity.entitystats.IEntityStats;
import com.example.jujutsukaisen.data.quest.IQuestData;
import com.example.jujutsukaisen.data.quest.QuestDataCapability;
import com.example.jujutsukaisen.init.ModResources;
import com.example.jujutsukaisen.networking.PacketHandler;
import com.example.jujutsukaisen.networking.client.CAbandonQuestPacket;
import com.example.jujutsukaisen.networking.client.CStartObjectiveEventPacket;
import com.example.jujutsukaisen.quest.Objective;
import com.example.jujutsukaisen.quest.Quest;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class QuestsTrackerScreen extends Screen
{
	private final ResourceLocation background = ModResources.QUEST;

	private PlayerEntity player;
	private IQuestData qprops;
	private int questIndex = 0;
	private List<String> hiddenTexts = new ArrayList<String>();
	private Quest currentQuest = null;
	private List<Quest> availableQuests;
	private final int xSize = 256;
	private final int ySize = 256;

	private int guiLeft;
	private int guiTop;
	public QuestsTrackerScreen(PlayerEntity player)
	{
		super(new StringTextComponent(""));
		this.player = player;
		this.qprops = QuestDataCapability.get(player);
		this.availableQuests = Arrays.asList(this.qprops.getInProgressQuests()).stream().filter(quest -> quest != null).collect(Collectors.toList());
	}

	@Override
	public void init()
	{

		guiLeft = (this.width - this.xSize) / 2;
		guiTop = (this.height - this.ySize) / 2;

		int posX = ((this.width - 256) / 2) - 110;
		int posY = (this.height - 256) / 2;

		IAbilityData abilityProps = AbilityDataCapability.get(this.player);
		IQuestData questProps = QuestDataCapability.get(this.player);

	}

	@Override
	public void render(MatrixStack matrixStack, int x, int y, float f)
	{
		this.renderBackground(matrixStack);
		backgroundRendering(matrixStack);

		super.render(matrixStack, x, y, f);
	}

	public void backgroundRendering(MatrixStack matrixStack)
	{
		PlayerEntity player = this.getMinecraft().player;
		IEntityStats statsProps = EntityStatsCapability.get(player);
		IQuestData questProps = QuestDataCapability.get(this.player);

		Quest[] quests = questProps.getInProgressQuests();



				/*
		String questInProgress = this.currentQuest.getTitle().toString();
		String test = questProps.getInProgressQuests().toString();

				 */



		minecraft.getTextureManager().bind(background);
		GuiUtils.drawTexturedModalRect(matrixStack, guiLeft, guiTop + 20, 0, 0, xSize, ySize, 0);

		//TODO add a little V icon when it's done
		for (int i = 0; i < quests.length; i++)
		{
			if (quests[i] != null)
			{
				Quest questInProgress = questProps.getInProgressQuests()[i];
				String questInProgressString = questInProgress.getTitle();
				String questInProgressDescription = questInProgress.getDescription();
				drawString(matrixStack, font, "Quest in progress: ", guiLeft + 5 , guiTop + 30 + (i * 40), 16777215);
				drawString(matrixStack, font, questInProgressString, guiLeft + 5, guiTop + 40+ (i * 40), 16777215);
				List<Objective> objectives = questInProgress.getObjectives();
				for (int a = 0; a < objectives.size(); a++)
				{
					Objective objective = objectives.get(a);
					String objectiveString = objective.getTitle();
					drawString(matrixStack, font, "Goal: " + objectiveString, guiLeft + 5, guiTop + 50 + (i * 40) + (a * 5), 16777215);
				}
			}

		}
	}

	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
}
