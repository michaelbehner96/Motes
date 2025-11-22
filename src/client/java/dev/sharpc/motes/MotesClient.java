package dev.sharpc.motes;

import dev.sharpc.motes.registry.ModMenus;
import dev.sharpc.motes.screen.FocusChamberScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;

public class MotesClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        MenuScreens.register(ModMenus.FOCUS_CHAMBER, FocusChamberScreen::new);
    }

}