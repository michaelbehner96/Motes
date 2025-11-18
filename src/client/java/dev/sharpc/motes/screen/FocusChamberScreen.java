package dev.sharpc.motes.screen;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.menu.FocusChamberMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Vector2i;

public class FocusChamberScreen extends AbstractContainerScreen<FocusChamberMenu>
{
    private static final ResourceLocation GUI_TEXTURE = Motes.createModdedResourceLocation("textures/gui/container/focus_chamber.png");
    private static final ResourceLocation PROGRESS_SPRITE = Motes.createModdedResourceLocation("container/focus_chamber/progress");
    private static final Vector2i GUI_SIZE = new Vector2i(176, 166);
    private static final Vector2i COOKING_PROGRESS_POS = new Vector2i(94, 34);
    private static final Vector2i COOKING_PROGRESS_SIZE = new Vector2i(16, 8);

    public FocusChamberScreen(FocusChamberMenu abstractContainerMenu, Inventory inventory, Component component)
    {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j)
    {

    }
}
