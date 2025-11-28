package dev.sharpc.motes.data.loader;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.MoteDefinition;
import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.data.model.recipe.TransmutationRecipe;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import dev.sharpc.motes.data.registry.TransmutationRecipes;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

@SuppressWarnings("deprecation")
public class TransmutationRecipeLoader
        extends SimpleJsonResourceReloadListener<TransmutationRecipe>
        implements IdentifiableResourceReloadListener
{

    public static final ResourceLocation ID = Motes.createModdedResourceLocation("transmutations");

    public TransmutationRecipeLoader()
    {
        // data/motes/transmutations/fire/water_to_steam.json -> motes:fire/water_to_steam
        // data/motes/transmutations/fire/ash_to_soot.json -> motes:fire/ash_to_soot
        // etc...
        super(TransmutationRecipe.CODEC, FileToIdConverter.json(ID.getPath()));
    }

    @Override
    protected void apply(Map<ResourceLocation, TransmutationRecipe> object, ResourceManager resourceManager, ProfilerFiller profilerFiller)
    {
        MoteDefinitions.clear();
        for (var entry : object.entrySet())
            TransmutationRecipes.register(new MoteId(entry.getKey()), entry.getValue());
    }

    @Override
    public ResourceLocation getFabricId()
    {
        return ID;
    }

}
