package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.item.MaterialItem;
import dev.sharpc.motes.item.MoteItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public final class ModItems
{
    public static final Item MOTE = register(
            Motes.createMoteResourceLocation("mote"),
            MoteItem::new,
            new Item.Properties());

    public static final Item MATERIAL = register(
            Motes.createMaterialResourceLocation("material"),
            MaterialItem::new,
            new Item.Properties());

    public static Item register(String name, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        return register(Motes.createModdedResourceLocation(name), function, properties);
    }

    public static Item register(ResourceLocation location, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        var key = ResourceKey.create(Registries.ITEM, location);
        var item = function.apply(properties.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void initialize()
    {
    }
}
