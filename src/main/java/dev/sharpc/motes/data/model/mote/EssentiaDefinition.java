package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;

public record EssentiaDefinition()
{
    public static final Codec<EssentiaDefinition> CODEC = Codec.unit(EssentiaDefinition::new);
}
