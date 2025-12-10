package dev.sharpc.motes.data.model.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.data.model.mote.EssentiaId;
import dev.sharpc.motes.data.model.mote.MoteId;

public record TransmutationRecipe
        (
                MoteId catalystMoteId,
                EssentiaId substrateId,
                int substrateQuantity,
                EssentiaId productId,
                int productQuantity,
                int stagesPerCycle,
                int ticksPerStage
        )
{

    public static final Codec<TransmutationRecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(

                    MoteId.CODEC.fieldOf("catalystMoteId").forGetter(TransmutationRecipe::catalystMoteId),
                    EssentiaId.CODEC.fieldOf("substrateId").forGetter(TransmutationRecipe::substrateId),
                    Codec.INT.fieldOf("substrateQuantity").forGetter(TransmutationRecipe::substrateQuantity),
                    EssentiaId.CODEC.fieldOf("productId").forGetter(TransmutationRecipe::productId),
                    Codec.INT.fieldOf("productQuantity").forGetter(TransmutationRecipe::productQuantity),
                    Codec.INT.fieldOf("stagesPerCycle").forGetter(TransmutationRecipe::stagesPerCycle),
                    Codec.INT.fieldOf("ticksPerStage").forGetter(TransmutationRecipe::ticksPerStage)

            ).apply(instance, TransmutationRecipe::new));
}
