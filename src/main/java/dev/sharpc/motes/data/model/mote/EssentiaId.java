package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.registry.EssentiaDefinitions;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record EssentiaId(ResourceLocation id)
{
    public static final Codec<EssentiaId> CODEC =
            ResourceLocation.CODEC.xmap(EssentiaId::new, EssentiaId::id);

    private static final String ID_TYPE = "essentia";
    private static final String ID_PREFIX = "essentia/";

    public EssentiaId
    {
        if (id == null)
            throw new IllegalArgumentException("ResourceLocation of %s cannot be null.".formatted(ID_PREFIX));
    }

    public String getTranslationKey()
    {
        var pathWithoutPrefix = id().getPath().replaceFirst(ID_PREFIX, "");
        var nameStartIndex = pathWithoutPrefix.contains("/") ? id.getPath().lastIndexOf('/') + 1 : 0;
        var name = pathWithoutPrefix.substring(nameStartIndex);

        return "%s.%s.%s".formatted(ID_TYPE, id().getNamespace(), name);
    }

    public @Nullable EssentiaDefinition definition()
    {
        return EssentiaDefinitions.get(this);
    }

    private static EssentiaId of(String name)
    {
        return new EssentiaId(Motes.createModdedResourceLocation(name).withPrefix(ID_PREFIX));
    }
}
