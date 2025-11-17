package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.mote.MoteId;
import dev.sharpc.motes.item.MoteItem;
import dev.sharpc.motes.registry.mote.MoteIds;
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
                    .component(ModDataComponents.MOTE_ID, MoteIds.FIRE));

    public static final Item WATER_MOTE = register("water_mote",
            MoteItem::new,
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .component(ModDataComponents.MOTE_ID, MoteIds.WATER));

    public static final Item EARTH_MOTE = register("earth_mote",
            MoteItem::new,
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .component(ModDataComponents.MOTE_ID, MoteIds.EARTH));

    public static final Item WIND_MOTE = register("wind_mote",
            MoteItem::new,
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .component(ModDataComponents.MOTE_ID, MoteIds.WIND));

    public static final Item LIGHT_MOTE = register("light_mote",
            MoteItem::new,
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .component(ModDataComponents.MOTE_ID, MoteIds.LIGHT));

    public static final Item DARK_MOTE = register("dark_mote",
            MoteItem::new,
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .component(ModDataComponents.MOTE_ID, MoteIds.DARK));

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
