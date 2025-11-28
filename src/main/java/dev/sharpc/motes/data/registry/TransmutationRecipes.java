package dev.sharpc.motes.data.registry;

import dev.sharpc.motes.data.model.mote.MaterialId;
import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.data.model.recipe.TransmutationRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TransmutationRecipes
{
    private static final Map<MoteId, TransmutationRecipe> RECIPES = new HashMap<>();

    public static void register(MoteId id, TransmutationRecipe recipe)
    {
        if (RECIPES.put(id, recipe) != null)
        {
            throw new IllegalStateException("Duplicate transmutation recipe id: " + id);
        }
    }

    public static @Nullable TransmutationRecipe get(MoteId id)
    {
        return RECIPES.get(id);
    }

    public static void clear()
    {
        RECIPES.clear();
    }

    public static Map<MoteId, TransmutationRecipe> all()
    {
        return Collections.unmodifiableMap(RECIPES);
    }

    public static @Nullable TransmutationRecipe find(MoteId moteId, MaterialId substrate)
    {
        for (TransmutationRecipe recipe : RECIPES.values())
        {
            if (recipe.catalystMoteId().equals(moteId) && recipe.substrateId().equals(substrate))
            {
                return recipe;
            }
        }

        return null;
    }

    public static List<TransmutationRecipe> forMote(MoteId moteId)
    {
        return RECIPES.values().stream()
                      .filter(r -> r.catalystMoteId().equals(moteId))
                      .toList();
    }
}
