package dev.sharpc.motes.data.loader;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.GrainDefinition;
import dev.sharpc.motes.data.model.mote.GrainId;
import dev.sharpc.motes.data.registry.GrainDefinitions;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

@SuppressWarnings("deprecation")
public final class GrainDefinitionLoader
        extends SimpleJsonResourceReloadListener<GrainDefinition>
        implements IdentifiableResourceReloadListener
{
    public static final ResourceLocation ID = Motes.createModdedResourceLocation("grains");

    public GrainDefinitionLoader()
    {
        super(GrainDefinition.CODEC, FileToIdConverter.json(ID.getPath()));
    }

    @Override
    protected void apply(Map<ResourceLocation, GrainDefinition> object,
            ResourceManager resourceManager,
            ProfilerFiller profilerFiller)
    {
        GrainDefinitions.clear();

        for (var entry : object.entrySet())
        {
            Motes.LOGGER.info("Building grain definition for: {}", entry.getKey());
            GrainDefinitions.register(new GrainId(entry.getKey()), entry.getValue());
        }
    }

    @Override
    public ResourceLocation getFabricId()
    {
        return ID;
    }
}

