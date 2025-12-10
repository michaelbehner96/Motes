package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.mote.aspect.MoteAspectSerializationHelper;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspect;
import dev.sharpc.motes.mote.aspect.MoteAspectType;

import java.util.Map;

public record MoteDefinition(
        Map<MoteAspectType<?>, MoteAspect> aspects
)
{
    public static final Codec<MoteDefinition> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    MoteAspectSerializationHelper.ASPECT_MAP_CODEC
                            .optionalFieldOf("aspects", Map.of())
                            .forGetter(MoteDefinition::aspects)
            ).apply(instance, MoteDefinition::new)
    );
}
