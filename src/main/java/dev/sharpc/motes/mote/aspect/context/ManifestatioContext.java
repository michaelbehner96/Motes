package dev.sharpc.motes.mote.aspect.context;

import dev.sharpc.motes.data.model.mote.EssentiaDefinition;
import dev.sharpc.motes.data.registry.EssentiaDefinitions;
import dev.sharpc.motes.mote.aspect.MoteAspectTypes;
import dev.sharpc.motes.mote.aspect.aspects.ManifestatioAspect;
import org.jetbrains.annotations.Nullable;

public record ManifestatioContext(
        MoteContext moteContext,
        ManifestatioAspect manifestatioAspect,
        EssentiaDefinition essentiaDefinition
)
{
    public static @Nullable ManifestatioContext from(MoteContext context)
    {
        var aspect = context.getAspect(MoteAspectTypes.MANIFESTATIO);
        if (aspect == null) return null;

        var definition = EssentiaDefinitions.get(aspect.essentiaId());
        if (definition == null) return null;

        return new ManifestatioContext(context, aspect, definition);
    }
}
