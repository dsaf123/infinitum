package com.infinitum.infinitummod.blocks.basicgenerator;

import com.infinitum.infinitummod.InfinitumMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class BasicGeneratorScreen extends ContainerScreen<BasicGeneratorContainer> {

    private final ResourceLocation GUI = new ResourceLocation(InfinitumMod.MOD_ID, "textures/gui/basic_generator.png");

    public BasicGeneratorScreen(BasicGeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.titleX = this.xSize/2 - 40;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

    }


    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(GUI);
        int guiWidth =  (this.width - this.xSize) / 2;
        int guiHeight = (this.height - this.ySize) / 2;
        this.blit(matrixStack, guiWidth, guiHeight, 0, 0, this.xSize, this.ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int x, int y) {
        drawString(matrixStack, Minecraft.getInstance().fontRenderer, "Energy: " + container.getEnergy(), this.titleX, 15, 0xffffff);
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
    }
}
