package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.registry.MaterialIds;
import dev.sharpc.motes.item.MaterialItem;
import dev.sharpc.motes.item.MoteItem;
import dev.sharpc.motes.data.registry.MoteIds;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeTabs
{
    public static final ResourceKey<CreativeModeTab> MOTES_TAB_KEY = Motes.createModdedResourceKey(Registries.CREATIVE_MODE_TAB, "motes");
    public static final CreativeModeTab MOTES_TAB =
            FabricItemGroup.builder()
                           .icon(() -> new ItemStack(ModItems.MOTE))
                           .title(Component.translatable("itemGroup.motes.motes"))
                           .displayItems((context, entries) ->
                           {
                               // Motes
                               entries.accept(MoteItem.stackOf(MoteIds.FIRE));
                               entries.accept(MoteItem.stackOf(MoteIds.EARTH));
                               entries.accept(MoteItem.stackOf(MoteIds.WATER));
                               entries.accept(MoteItem.stackOf(MoteIds.WIND));
                               entries.accept(MoteItem.stackOf(MoteIds.DARK));
                               entries.accept(MoteItem.stackOf(MoteIds.LIGHT));

                               // Materials
                               entries.accept(MaterialItem.stackOf(MaterialIds.FIRE));
                               entries.accept(MaterialItem.stackOf(MaterialIds.WATER));
                               entries.accept(MaterialItem.stackOf(MaterialIds.EARTH));
                               entries.accept(MaterialItem.stackOf(MaterialIds.WIND));
                               entries.accept(MaterialItem.stackOf(MaterialIds.LIGHT));
                               entries.accept(MaterialItem.stackOf(MaterialIds.DARK));

                               entries.accept(ModBlocks.FOCUS_CHAMBER);
                           }).build();

    public static void initialize()
    {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOTES_TAB_KEY, MOTES_TAB);
    }
}
