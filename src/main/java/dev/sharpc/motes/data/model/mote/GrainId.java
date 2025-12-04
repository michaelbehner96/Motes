package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.Motes;
import net.minecraft.resources.ResourceLocation;

public record GrainId(ResourceLocation id)
{
    public static final Codec<GrainId> CODEC =
            ResourceLocation.CODEC.xmap(GrainId::new, GrainId::id);

    private static final String ID_PREFIX = "grain/";

    public static final GrainId FIRE = GrainId.of("fire");
    public static final GrainId WATER = GrainId.of("water");
    public static final GrainId EARTH = GrainId.of("earth");
    public static final GrainId WIND = GrainId.of("wind");
    public static final GrainId LIGHT = GrainId.of("light");
    public static final GrainId DARK = GrainId.of("dark");

    public GrainId
    {
        if (id == null)
            throw new IllegalArgumentException("ResourceLocation of GrainId cannot be null.");
    }

    public String getTranslationKey()
    {
        return "grain." + id().getNamespace() + "." + id().getPath().replaceFirst(ID_PREFIX, "");
    }

    private static GrainId of(String name)
    {
        return new GrainId(Motes.createModdedResourceLocation(name).withPrefix(ID_PREFIX));
    }
}
