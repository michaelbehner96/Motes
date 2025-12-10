package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.registry.EssentiaDefinitions;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import dev.sharpc.motes.item.EssentiaItem;
import dev.sharpc.motes.item.MoteItem;
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
                               Motes.LOGGER.info("Adding motes to creative tab for {} definition(s).", MoteDefinitions.all().size());
                               for (var definition : MoteDefinitions.all().entrySet())
                                   entries.accept(MoteItem.stackOf(definition.getKey()));

                               // Essentia
                               Motes.LOGGER.info("Adding essentia to creative tab for {} definition(s).", EssentiaDefinitions.all().size());
                               for (var definition : EssentiaDefinitions.all().entrySet())
                                   entries.accept(EssentiaItem.stackOf(definition.getKey()));

                               entries.accept(ModBlocks.FOCUS_CHAMBER);
                           }).build();

    public static void initialize()
    {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOTES_TAB_KEY, MOTES_TAB);
    }
}
