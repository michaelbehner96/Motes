package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.mote.MoteId;
import dev.sharpc.motes.item.MoteItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

public final class ModItems
{
    public static final Item FIRE_MOTE = register("fire_mote",
            MoteItem::new,
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .component(ModDataComponents.MOTE_ID, MoteId.of("fire")));

    public static final Item WATER_MOTE = register("water_mote",
            MoteItem::new,
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .component(ModDataComponents.MOTE_ID, MoteId.of("water")));


    public static Item register(String name, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        var key = createItemResourceKey(name);
        var item = function.apply(properties.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static ResourceKey<Item> createItemResourceKey(String itemName)
    {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Motes.MOD_ID, itemName));
    }

    public static void initialize()
    {
    }
}
