package dev.sharpc.motes.mote.aspect;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import dev.sharpc.motes.Motes;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspect;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspectType;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspectTypes;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class MoteAspectSerializationHelper
{
    public static final Codec<JsonElement> JSON_ELEMENT_CODEC = Codec.PASSTHROUGH.xmap(
            dynamic -> dynamic.convert(JsonOps.INSTANCE).getValue(),
            json -> new Dynamic<>(JsonOps.INSTANCE, json)
    );

    public static final Codec<Map<MoteAspectType<?>, MoteAspect>> ASPECT_MAP_CODEC =
            Codec.unboundedMap(ResourceLocation.CODEC, JSON_ELEMENT_CODEC).xmap(
                    MoteAspectSerializationHelper::decodeAspects,
                    MoteAspectSerializationHelper::encodeAspects
            );

    private static Map<MoteAspectType<?>, MoteAspect> decodeAspects(Map<ResourceLocation, JsonElement> raw)
    {
        var result = new HashMap<MoteAspectType<?>, MoteAspect>();

        for (var entry : raw.entrySet())
        {
            var id = entry.getKey();
            var json = entry.getValue();
            var type = MoteAspectTypes.get(id);

            if (type == null)
            {
                Motes.LOGGER.warn("Unknown mote aspect type: {}", id);
                continue;
            }

            var parsed = type.codec().parse(JsonOps.INSTANCE, json).result();

            if (parsed.isEmpty())
            {
                Motes.LOGGER.warn("Failed to decode mote aspect {}: {}", id, json);
                continue;
            }

            var aspect = parsed.get();
            result.put(type, aspect);
        }

        return Map.copyOf(result);
    }

    @SuppressWarnings("unchecked")
    private static Map<ResourceLocation, JsonElement> encodeAspects(Map<MoteAspectType<?>, MoteAspect> aspects)
    {
        var raw = new HashMap<ResourceLocation, JsonElement>();

        for (var entry : aspects.entrySet())
        {
            var type = entry.getKey();
            var aspect = entry.getValue();
            var castType = (MoteAspectType<MoteAspect>) type;
            var encoded = castType.codec().encodeStart(JsonOps.INSTANCE, aspect);

            encoded.resultOrPartial(
                           errorMessage -> Motes.LOGGER.error("Failed to encode aspect {}: {}", type.id(), errorMessage))
                   .ifPresent(json -> raw.put(type.id(), json));
        }

        return raw;
    }
}
