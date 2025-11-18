package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.menu.FocusChamberMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public final class ModMenus
{
    public static final MenuType<FocusChamberMenu> FOCUS_CHAMBER =
            register("focus_chamber", FocusChamberMenu::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(
            String name,
            MenuType.MenuSupplier<T> factory)
    {
        var key = Motes.createModdedResourceKey(Registries.MENU, name);
        var type = new MenuType<>(factory, FeatureFlagSet.of());

        return Registry.register(BuiltInRegistries.MENU, key, type);
    }

    public static void initialize()
    {
    }
}
