package dev.sharpc.motes.data.loader;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.EssentiaDefinition;
import dev.sharpc.motes.data.model.mote.EssentiaId;
import dev.sharpc.motes.data.registry.EssentiaDefinitions;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

@SuppressWarnings("deprecation")
public final class EssentiaDefinitionLoader
        extends SimpleJsonResourceReloadListener<EssentiaDefinition>
        implements IdentifiableResourceReloadListener
{
    public static final ResourceLocation ID = Motes.createModdedResourceLocation("essentia_definitions");

    public EssentiaDefinitionLoader()
    {
        super(EssentiaDefinition.CODEC, FileToIdConverter.json(ID.getPath()));
    }

    @Override
    protected void apply(Map<ResourceLocation, EssentiaDefinition> object,
            ResourceManager resourceManager,
            ProfilerFiller profilerFiller)
    {
        EssentiaDefinitions.clear();

        for (var entry : object.entrySet())
        {
            Motes.LOGGER.info("Building essentia definition for: {}", entry.getKey());
            EssentiaDefinitions.register(new EssentiaId(entry.getKey()), entry.getValue());
        }
    }

    @Override
    public ResourceLocation getFabricId()
    {
        return ID;
    }
}

