package dev.sharpc.motes.data.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.EssentiaDefinition;
import dev.sharpc.motes.data.model.mote.EssentiaId;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class EssentiaDefinitions
{
    private static final Map<EssentiaId, EssentiaDefinition> DEFINITIONS = new HashMap<>();

    public static void register(EssentiaId id, EssentiaDefinition definition)
    {
        if (DEFINITIONS.put(id, definition) != null)
        {
            throw new IllegalStateException("Duplicate essentia definition for essentiaId " + id);
        }

        Motes.LOGGER.info("Registered essentia definition for: {} ({} total)", id.id(), DEFINITIONS.size());
    }

    public static @Nullable EssentiaDefinition get(EssentiaId id)
    {
        return DEFINITIONS.get(id);
    }

    public static void clear()
    {
        DEFINITIONS.clear();
    }

    public static Map<EssentiaId, EssentiaDefinition> all()
    {
        return Collections.unmodifiableMap(DEFINITIONS);
    }
}

