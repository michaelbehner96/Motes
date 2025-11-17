package dev.sharpc.motes.data.mote;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.List;

/**
 * A static definition of what a mote is.
 */
public record MoteDefinition(
        MoteId id,
        MoteElement element,
        int tier,
        float baseStability,
        ResourceKey<Item> product,
        int productAmount,
        List<MoteId> componentMotes)
{
    public MoteDefinition
    {
        if (baseStability < 0f || baseStability > 1f)
            throw new IllegalArgumentException("baseStability must be between 0 and 1.");

        if (componentMotes == null)
            throw new IllegalArgumentException("componentMotes cannot be null.");
    }

    public boolean hasComponentMotes()
    {
        return !componentMotes.isEmpty();
    }
}
