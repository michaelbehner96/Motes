package dev.sharpc.motes.data.mote;

import dev.sharpc.motes.Motes;
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
        super(MoteDefinition.CODEC, FileToIdConverter.json("mote_definitions"));
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
