package dev.sharpc.motes.data.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.GrainDefinition;
import dev.sharpc.motes.data.model.mote.GrainId;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class GrainDefinitions
{
    private static final Map<GrainId, GrainDefinition> DEFINITIONS = new HashMap<>();

    public static void register(GrainId id, GrainDefinition definition)
    {
        if (DEFINITIONS.put(id, definition) != null)
        {
            throw new IllegalStateException("Duplicate grain definition for grainId " + id);
        }

        Motes.LOGGER.info("Registered grain definition for: {} ({} total)", id.id(), DEFINITIONS.size());
    }

    public static @Nullable GrainDefinition get(GrainId id)
    {
        return DEFINITIONS.get(id);
    }

    public static void clear()
    {
        DEFINITIONS.clear();
    }

    public static Map<GrainId, GrainDefinition> all()
    {
        return Collections.unmodifiableMap(DEFINITIONS);
    }
}

