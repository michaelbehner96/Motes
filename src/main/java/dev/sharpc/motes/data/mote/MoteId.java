package dev.sharpc.motes.data.mote;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.Motes;
import net.minecraft.resources.ResourceLocation;

public record MoteId(ResourceLocation id)
{
    public static final Codec<MoteId> CODEC =
            ResourceLocation.CODEC.xmap(MoteId::new, MoteId::id);

    public MoteId
    {
        if (id == null)
            throw new IllegalArgumentException("MoteId id cannot be null.");
    }

    public static MoteId of(String id)
    {
        return MoteId.of(Motes.MOD_ID, id);
    }

    public static MoteId of(String namespace, String path)
    {
        return MoteId.of(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static MoteId of(ResourceLocation id)
    {
        return new MoteId(id);
    }
}
