package dev.sharpc.motes.data.mote.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.data.mote.MoteId;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Optional;

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

    public sealed interface Substrate
            permits Substrate.ItemSubstrate, Substrate.MaterialSubstrate
    {

        record ItemSubstrate(Item item) implements Substrate
        {
        }

        record MaterialSubstrate(ResourceLocation materialId) implements Substrate
        {
        }

        public static final Codec<Substrate> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(
                        Codec.STRING.fieldOf("type").forGetter(s ->
                        {
                            return s instanceof ItemSubstrate ? "item" : "material";
                        }),
                        BuiltInRegistries.ITEM.byNameCodec()
                                              .optionalFieldOf("item")
                                              .forGetter(s -> s instanceof ItemSubstrate(Item item) ? Optional.of(item) : Optional.empty()),
                        ResourceLocation.CODEC
                                .optionalFieldOf("material")
                                .forGetter(s -> s instanceof MaterialSubstrate(ResourceLocation materialId) ? Optional.of(materialId) : Optional.empty())
                ).apply(instance, (type, itemOpt, matOpt) ->
                {
                    return switch (type)
                    {
                        case "item" -> new ItemSubstrate(
                                itemOpt.orElseThrow(() -> new IllegalStateException("Missing 'item' for item substrate"))
                        );
                        case "material" -> new MaterialSubstrate(
                                matOpt.orElseThrow(() -> new IllegalStateException("Missing 'material' for material substrate"))
                        );
                        default -> throw new IllegalStateException("Unknown substrate type: " + type);
                    };
                }));

    }

}
