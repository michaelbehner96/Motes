package dev.sharpc.motes.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Vector2i;

public class FocusChamberScreen extends AbstractContainerScreen<FocusChamberMenu>
{
    private static final Identifier GUI_TEXTURE = Identifier.of(Hephaestus.MOD_ID, "textures/gui/container/fusion_crucible.png");
    private static final Identifier PROGRESS_SPRITE = Identifier.of(Hephaestus.MOD_ID, "container/fusion_crucible/progress");
    private static final Vector2i GUI_SIZE = new Vector2i(176, 166);
    private static final Vector2i COOKING_PROGRESS_POS = new Vector2i(94, 34);
    private static final Vector2i COOKING_PROGRESS_SIZE = new Vector2i(16, 8);


    // TODO Move this to menu
    public static final Vector2i MOTE_SLOT_POSITION = new Vector2i(26, 30);
    public static final Vector2i CATALYST_SLOT_POSITION = new Vector2i(62, 30);
    public static final Vector2i REGRESSION_A_SLOT_POSITION = new Vector2i(17, 57);
    public static final Vector2i REGRESSION_B_SLOT_POSITION = new Vector2i(35, 57);
    public static final Vector2i PRODUCT_SLOT_POSITION = new Vector2i(134, 30);
    public static final Vector2i PLAYER_INVENTORY_POSITION = new Vector2i(8, 84);
    public static final Vector2i PLAYER_HOTBAR_POSITION = new Vector2i(8, 142);

}
