package com.example.jujutsukaisen.client.gui;

import com.example.jujutsukaisen.api.Beapi;
import com.example.jujutsukaisen.client.gui.buttons.TexturedIconButton;
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
	private PlayerEntity player;
	private IQuestData qprops;
	private int questIndex = 0;
	private List<String> hiddenTexts = new ArrayList<String>();
	private Quest currentQuest = null;
	private List<Quest> availableQuests;

	public QuestsTrackerScreen(PlayerEntity player)
	{
		super(new StringTextComponent(""));
		this.player = player;
		this.qprops = QuestDataCapability.get(player);
		this.availableQuests = Arrays.asList(this.qprops.getInProgressQuests()).stream().filter(quest -> quest != null).collect(Collectors.toList());
	}
	
	@Override
	public void render(MatrixStack matrixStack, int x, int y, float f)
	{
		this.renderBackground(matrixStack);
		RenderSystem.color4f(1F, 1F, 1F, 1F);
		
		int posX = this.width / 2;
		int posY = this.height / 2;
		
		Minecraft.getInstance().getTextureManager().bind(ModResources.BLANK);
		RenderSystem.pushMatrix();
		{
			double scale = 1.1;
			RenderSystem.translated(posX - 35, posY + 10, 0);
			RenderSystem.translated(256, 256, 0);
			
			RenderSystem.scaled(scale * 1.5, scale * 1.4, 0);
			RenderSystem.translated(-256, -256, 0);
			//RenderSystem.rotated(90, 0, 0, 1);
			
			// Background
			GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, 1);
			
			RenderSystem.translated(-30, 50, 0);
			RenderSystem.translated(256, 256, 0);
			
			RenderSystem.scaled(scale * 0.7, scale * 0.9, 0);
			RenderSystem.translated(-256, -256, 0);
		}
		RenderSystem.popMatrix();

		String currentQuestName = this.currentQuest != null ? new TranslationTextComponent(String.format("quest." + this.currentQuest.getRegistryName().toString().replace(":", "."))).getString() : "None";
		double currentQuestProgress = this.currentQuest != null ? this.currentQuest.getProgress() * 100 : -1;
			
		RenderSystem.translated(0, 10, 0);
		
		if(this.currentQuest != null)
		{
			// Page Number
			RenderSystem.pushMatrix();
			{
				RenderSystem.translated(posX + 150, posY - 110, 0);
				
				String pageNumber = (this.questIndex + 1) + "/" + this.availableQuests.size();
				Beapi.drawStringWithBorder(this.font, matrixStack, pageNumber, 0, 0, Beapi.hexToRGB("#FFFFFF").getRGB());
			}
			RenderSystem.popMatrix();
			
			// Quest name
			RenderSystem.pushMatrix();
			{
				double scale = 1.4;
				RenderSystem.translated(posX + 100, posY + 10, 0);
				RenderSystem.translated(256, 256, 0);
				
				RenderSystem.scaled(scale, scale, 0);
				RenderSystem.translated(-256, -256, 0);

				Beapi.drawStringWithBorder(this.font, matrixStack, currentQuestName, -this.font.width(currentQuestName) / 2, 0, Beapi.hexToRGB("#FFFFFF").getRGB());
			}
			RenderSystem.popMatrix();
			
			// Quest progress
			if(currentQuestProgress != -1)
			{
				String textColor = "#FFFFFF";
				if(this.currentQuest.isComplete())
					textColor = "#00FF55";
				String progress = TextFormatting.BOLD + new TranslationTextComponent("gui.quests.progress", "Progress").getString() + " : " + String.format("%.1f", currentQuestProgress) + "%";
				Beapi.drawStringWithBorder(this.font, matrixStack, progress, posX - 120, posY - 65, Beapi.hexToRGB(textColor).getRGB());
			}
			
			// Quest Objective
			RenderSystem.pushMatrix();
			{
				List<Objective> avilableObjectives = this.currentQuest.getObjectives().stream().limit(5).collect(Collectors.toList());

				int yOffset = -20;
				int i = 0;
				for(Objective obj : avilableObjectives)
				{
					String objectiveName = obj.getLocalizedTitle();
					String progress = "";
					double objectiveProgress = (obj.getProgress() / obj.getMaxProgress()) * 100;
					List<Objective> hiddenObjs = avilableObjectives.stream().filter(o -> o.isHidden()).collect(Collectors.toList());
					yOffset += 20;

					String textColor = "#FFFFFF";
					if(obj.isComplete())
						textColor = "#00FF00";
					if(obj.isLocked())
						textColor = "#505050";
					else
						progress = " - " + String.format("%.1f", objectiveProgress) + "%";
					
					if(obj.isHidden())
					{
						// TODO(wynd) - Check if hidden objectives are still actually hidden
			            //FontRenderer galacticFont = this.minecraft.getFontResourceManager().getFontRenderer(Minecraft.ALT_FONT);
						Beapi.drawStringWithBorder(this.font, matrixStack, "• ", posX - 130, posY - 45 + yOffset, Beapi.hexToRGB(textColor).getRGB());
			            if(hiddenObjs.size() > 0)
							Beapi.drawStringWithBorder(this.font, matrixStack, this.hiddenTexts.get(hiddenObjs.indexOf(obj)), posX - 123, posY - 45 + yOffset, Beapi.hexToRGB(textColor).getRGB());
					}
					else
					{
						String optional = obj.isOptional() ? "(Optional) " : "";
						objectiveName = "• " + optional + "" + objectiveName + " " + progress;
						List<IReorderingProcessor> splitText = this.font.split(new StringTextComponent(objectiveName), 280);
						//List<String> splitText = WyHelper.splitString(this.font, objectiveName, posX, 210);
						for(int j = 0; j < splitText.size(); j++)
						{
							Beapi.drawStringWithBorder(this.font, matrixStack, splitText.get(j), posX - 130, posY - 45 + yOffset + (j * 12), Beapi.hexToRGB(textColor).getRGB());
						}
						yOffset += (splitText.size() * 8);
					}
					i++;
				}
				
				if(i == 0)
					Beapi.drawStringWithBorder(this.font, matrixStack, new TranslationTextComponent("trainer.no_objectives_left", "No objectives left!").getString(), posX - 120, posY - 20 + yOffset, Beapi.hexToRGB("#FFFFFF").getRGB());
			}
			RenderSystem.popMatrix();
			
			RenderSystem.translated(0, 20, 0);
		}
		
		super.render(matrixStack, x, y, f);
	}
	
	@Override
	public void init()
	{
		this.children.clear();
		this.buttons.clear();
		
		int posX = (this.width - 256) / 2;
		int posY = (this.height - 256) / 2;
			
		try
		{
			this.currentQuest = this.qprops.getInProgressQuests()[this.questIndex];
		}
		catch (Exception e)
		{
			if(this.qprops.getInProgressQuests().length > 0)
			{
				this.currentQuest = this.qprops.getInProgressQuests()[0];
				System.out.println(String.format("\n[ArrayOutOfBounds] \n Max possible index is : %s \n But the index requested is : %s", this.qprops.getInProgressQuests().length - 1, this.questIndex));
			}
			else
				this.currentQuest = null;		
			this.questIndex = 0;
			e.printStackTrace();
		}
		
		if(this.currentQuest == null)
			return;
		
		this.hiddenTexts.clear();
		for(Objective obj : this.currentQuest.getObjectives())
		{
			if(obj.isHidden())
			{
				this.hiddenTexts.add(EnchantmentNameParts.getInstance().getRandomName(Minecraft.getInstance().font, obj.getTitle().length() * 2).getString());
			}
		}
				
		TexturedIconButton nextButton = new TexturedIconButton(ModResources.BIG_WOOD_BUTTON_RIGHT, posX + 285, posY + 80, 24, 100, new StringTextComponent(""), (btn) ->
		{
			if(this.questIndex + 1 < availableQuests.size())
				this.questIndex++;
			else
				this.questIndex = 0;
			this.init();
		});
		nextButton = nextButton.setTextureInfo(posX + 280, posY + 35, 32, 128);
		if(this.availableQuests.size() <= 1)
			nextButton.visible = false;
		this.addButton(nextButton);
		
		TexturedIconButton prevButton = new TexturedIconButton(ModResources.BIG_WOOD_BUTTON_LEFT, posX - 55, posY + 80, 24, 100, new StringTextComponent(""), (btn) -> 
		{
			if(this.questIndex > 0)
				this.questIndex--;
			else
				this.questIndex = availableQuests.size() - 1;
			this.init();
		});
		prevButton = prevButton.setTextureInfo(posX - 58, posY + 35, 32, 128);
		if(this.availableQuests.size() <= 1)
			prevButton.visible = false;
		this.addButton(prevButton);
		
		TexturedIconButton abandonQuestButton = new TexturedIconButton(ModResources.BLANK2_SIMPLE, posX - 40, posY + 210, 60, 30, new TranslationTextComponent("Abandon"), (btn) -> 
		{
			this.player.closeContainer();
			if(this.currentQuest != null)
				PacketHandler.sendToServer(new CAbandonQuestPacket(this.qprops.getInProgressQuestSlot(this.currentQuest)));
		});			
		abandonQuestButton = abandonQuestButton.setTextureInfo(posX - 40, posY + 180, 60, 40).setTextInfo(posX - 31, posY + 189, 1);
		this.addButton(abandonQuestButton);
		
		List<Objective> avilableObjectives = this.currentQuest.getObjectives().stream().limit(5).collect(Collectors.toList());

		int yOffset = -20;
		int objId = -1;
		for(Objective obj : avilableObjectives)
		{
			yOffset += 30;
			objId++;
			
			if(!obj.hasEvent())
				continue;
			
			String startText = obj.hasStartedEvent() ? "Restart Event" : "Start Event";
						
			final int objId2 = objId;
			TexturedIconButton startEventButton = new TexturedIconButton(ModResources.BLANK2_SIMPLE, posX + 220, posY + 75 + yOffset, 60, 30, new TranslationTextComponent(startText), (btn) -> 
			{
				this.player.closeContainer();
				if(this.currentQuest != null)
					PacketHandler.sendToServer(new CStartObjectiveEventPacket(this.qprops.getInProgressQuestSlot(this.currentQuest), objId2));
			});
			int xOffset = obj.hasStartedEvent() ? 230 : 236;
			startEventButton = startEventButton.setTextureInfo(posX + 220, posY + 45 + yOffset, 60, 40).setTextInfo(posX + xOffset, posY + 56 + yOffset, 1);
			if(obj.isLocked())
				startEventButton.visible = false;
			this.addButton(startEventButton);			
		}
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
}
