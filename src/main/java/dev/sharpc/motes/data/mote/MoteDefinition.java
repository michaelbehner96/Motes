package dev.sharpc.motes.data.mote;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record MoteDefinition
        (
                float stability,
                List<MoteId> coalescenceParents
        )
{
    public static final Codec<MoteDefinition> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(

                    Codec.FLOAT.fieldOf("stability").forGetter(MoteDefinition::stability),

                    MoteId.CODEC
                            .listOf()
                            .optionalFieldOf("coalescence_parents", List.of())
                            .forGetter(MoteDefinition::coalescenceParents)

            ).apply(instance, MoteDefinition::new));

    public MoteDefinition
    {
        if (stability < 0f || stability > 1f)
            throw new IllegalArgumentException("stability must be between 0 and 1.");

        if (coalescenceParents.size() > 2)
            throw new IllegalArgumentException("coalescenceParents of size greater than 2 is not supported.");
    }
}
