package dev.sharpc.motes.data.mote;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

/**
 * A static definition of what a mote is.
 */
public record MoteDefinition(MoteId id, MoteElement element, int tier, int baseStability, ResourceKey<Item> product, int productAmount)
{

}
