package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GrainDefinition()
{
    public static final Codec<GrainDefinition> CODEC = Codec.unit(GrainDefinition::new);


}
