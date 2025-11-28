package dev.sharpc.motes.data.model.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.MoteId;
import net.minecraft.resources.ResourceLocation;

public record TransmutationRecipe
        (
                MoteId catalystMoteId,
                ResourceLocation substrateId,
                int substrateQuantity,
                ResourceLocation productId,
                int productQuantity,
                int stagesPerCycle,
                int ticksPerStage
        )
{

    public static final Codec<TransmutationRecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(

                    MoteId.CODEC.fieldOf("catalystMoteId").forGetter(TransmutationRecipe::catalystMoteId),
                    ResourceLocation.CODEC.fieldOf("substrateId").forGetter(TransmutationRecipe::substrateId),
                    Codec.INT.fieldOf("substrateQuantity").forGetter(TransmutationRecipe::substrateQuantity),
                    ResourceLocation.CODEC.fieldOf("productId").forGetter(TransmutationRecipe::productId),
                    Codec.INT.fieldOf("productQuantity").forGetter(TransmutationRecipe::productQuantity),
                    Codec.INT.fieldOf("stagesPerCycle").forGetter(TransmutationRecipe::stagesPerCycle),
                    Codec.INT.fieldOf("ticksPerStage").forGetter(TransmutationRecipe::ticksPerStage)

            ).apply(instance, TransmutationRecipe::new));
}
