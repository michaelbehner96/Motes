package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;

public record GrainDefinition()
{
    public static final Codec<GrainDefinition> CODEC = Codec.unit(GrainDefinition::new);
}
