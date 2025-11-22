package dev.sharpc.motes.data.mote.recipe;

import dev.sharpc.motes.data.mote.MoteId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record TransmutationRecipe
        (
                ResourceLocation id,
                MoteId moteId,
                ResourceKey<Item> inputMaterial,
                int stagesPerCycle,
                int ticksPerStage,
                ResourceKey<Item> outputMaterial,
                int outputQuantity
        )
{

}
