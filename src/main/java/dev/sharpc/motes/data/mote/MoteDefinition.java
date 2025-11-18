package dev.sharpc.motes.data.mote;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A static definition of what a mote is. For a code-driven list
 * of definitions, see MoteDefinitions.
 *
 * @param id             A unique identifier
 * @param element        Elemental tag of the mote.
 * @param tier           A non-negative tier of the mote.
 * @param baseStability  The base resistance a mote has against regression
 * @param product        What the mote produces (if anything)
 * @param productAmount  The quantity of product made per production cycle.
 * @param componentMotes A list of up to 2 motes that this mote will regress into if the regression check fails during a production cycle.
 * @see dev.sharpc.motes.registry.mote.MoteDefinitions
 */
public record MoteDefinition(
        MoteId id,
        MoteElement element,
        int tier,
        float baseStability,
        @Nullable ResourceKey<Item> product,
        int productAmount,
        List<MoteId> componentMotes)
{
    public MoteDefinition
    {
        if (baseStability < 0f || baseStability > 1f)
            throw new IllegalArgumentException("baseStability must be between 0 and 1.");

        if (componentMotes == null)
            throw new IllegalArgumentException("componentMotes cannot be null.");

        if (componentMotes.size() > 2)
            throw new IllegalArgumentException("componentMotes of size greater than 2 is not supported.");
    }

    public @Nullable Item getProductAsItem()
    {
        if (product != null)
        {
            var optional = BuiltInRegistries.ITEM.get(product);

            if (optional.isPresent())
                return optional.get().value();
        }

        return null;
    }

    public boolean hasComponentMotes()
    {
        return !componentMotes.isEmpty();
    }
}
