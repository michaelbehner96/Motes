package dev.sharpc.motes;

import dev.sharpc.motes.registry.ModMenus;
import dev.sharpc.motes.screen.FocusChamberScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class MotesClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        MenuScreens.register(ModMenus.FOCUS_CHAMBER, FocusChamberScreen::new);
    }

}