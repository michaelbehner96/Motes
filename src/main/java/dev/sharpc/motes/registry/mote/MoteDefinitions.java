package dev.sharpc.motes.registry.mote;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.mote.MoteDefinition;
import dev.sharpc.motes.data.mote.MoteElement;
import dev.sharpc.motes.data.mote.MoteId;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MoteDefinitions
{
    private static final Map<MoteId, MoteDefinition> BY_ID = new HashMap<>();
    private static boolean bootstrapped = false;

    public static void bootstrap()
    {
        if (bootstrapped)
        {
            Motes.LOGGER.warn("Mote Definition bootstrapper was called twice!");
            return;
        }

        // TODO Move logic into datapacks (code driven -> data driven)
        register(new MoteDefinition(MoteIds.FIRE, MoteElement.FIRE, 0, 100, vanillaItem("coal"), 1));
        register(new MoteDefinition(MoteIds.WATER, MoteElement.WATER, 0, 100, vanillaItem("clay_ball"), 1));
        register(new MoteDefinition(MoteIds.EARTH, MoteElement.EARTH, 0, 100, vanillaItem("flint"), 1));
        register(new MoteDefinition(MoteIds.WIND, MoteElement.WIND, 0, 100, vanillaItem("string"), 1));
        register(new MoteDefinition(MoteIds.LIGHT, MoteElement.LIGHT, 0, 100, vanillaItem("glowstone_dust"), 1));
        register(new MoteDefinition(MoteIds.DARK, MoteElement.DARK, 0, 100, vanillaItem("ender_pearl"), 1));

        bootstrapped = true;
    }

    private static void register(MoteDefinition definition)
    {
        if (BY_ID.put(definition.id(), definition) != null) throw new IllegalStateException("Duplicate mote definition for id " + definition.id());
    }

    public static MoteDefinition get(MoteId id)
    {
        return BY_ID.get(id);
    }

    public static Map<MoteId, MoteDefinition> all()
    {
        return Collections.unmodifiableMap(BY_ID);
    }

    private static ResourceKey<Item> vanillaItem(String path)
    {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(path));
    }
}
