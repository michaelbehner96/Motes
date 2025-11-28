package dev.sharpc.motes.data.loader;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.MoteDefinition;
import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

@SuppressWarnings("deprecation")
public final class MoteDefinitionLoader
        extends SimpleJsonResourceReloadListener<MoteDefinition>
        implements IdentifiableResourceReloadListener
{
    public static final ResourceLocation ID = Motes.createModdedResourceLocation("mote_definitions");

    public MoteDefinitionLoader()
    {
        // data/motes/mote_definitions/fire.json -> motes:fire
        // data/motes/mote_definitions/water.json -> motes:water
        // etc.
        super(MoteDefinition.CODEC, FileToIdConverter.json(ID.getPath()));
    }

    @Override
    protected void apply(Map<ResourceLocation, MoteDefinition> object, ResourceManager resourceManager, ProfilerFiller profilerFiller)
    {
        MoteDefinitions.clear();
        for (var entry : object.entrySet())
            MoteDefinitions.register(new MoteId(entry.getKey()), entry.getValue());
    }

    @Override
    public ResourceLocation getFabricId()
    {
        return ID;
    }
}
