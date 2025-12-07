package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.registry.GrainDefinitions;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record GrainId(ResourceLocation id)
{
    public static final Codec<GrainId> CODEC =
            ResourceLocation.CODEC.xmap(GrainId::new, GrainId::id);

    private static final String ID_TYPE = "grain";
    private static final String ID_PREFIX = "grain/";

    public GrainId
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

    public @Nullable GrainDefinition definition()
    {
        return GrainDefinitions.get(this);
    }

    private static GrainId of(String name)
    {
        return new GrainId(Motes.createModdedResourceLocation(name).withPrefix(ID_PREFIX));
    }
}
