package dev.sharpc.motes.data.model.mote;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.Motes;
import net.minecraft.resources.ResourceLocation;

public record MaterialId(ResourceLocation id)
{
    public static final Codec<MaterialId> CODEC =
            ResourceLocation.CODEC.xmap(MaterialId::new, MaterialId::id);

    public MaterialId
    {
        if (id == null)
            throw new IllegalArgumentException("ResourceLocation of MaterialId cannot be null.");
    }

    public String getTranslationKey()
    {
        return "material." + id().getNamespace() + "." + id().getPath();
    }

    public static MaterialId of(String id)
    {
        return MaterialId.of(Motes.MOD_ID, id);
    }

    public static MaterialId of(String namespace, String path)
    {
        return MaterialId.of(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static MaterialId of(ResourceLocation id)
    {
        return new MaterialId(id);
    }
}
