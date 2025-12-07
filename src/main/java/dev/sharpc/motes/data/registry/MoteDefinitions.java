package dev.sharpc.motes.data.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.MoteDefinition;
import dev.sharpc.motes.data.model.mote.MoteId;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MoteDefinitions
{
    private static final Map<MoteId, MoteDefinition> DEFINITIONS = new HashMap<>();

    public static void register(MoteId id, MoteDefinition definition)
    {
        if (DEFINITIONS.put(id, definition) != null) throw new IllegalStateException("Duplicate mote definition for moteId " + id);
        Motes.LOGGER.info("Registered definition for: {} ({} total)", id.id(), DEFINITIONS.size());
    }

    public static @Nullable MoteDefinition get(MoteId id)
    {
        return DEFINITIONS.get(id);
    }

    public static void clear()
    {
        DEFINITIONS.clear();
    }

    public static Map<MoteId, MoteDefinition> all()
    {
        return Collections.unmodifiableMap(DEFINITIONS);
    }
}
