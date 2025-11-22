package dev.sharpc.motes.data.mote;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public record TransmutationSpec(ResourceKey<Item> inputMaterialKey,
                                int inputQuantity,
                                ResourceKey<Item> outputMaterialKey,
                                int outputQuantity,
                                int numberOfStages)
{
    public static final Codec<TransmutationSpec> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                                    ResourceKey.codec(Registries.ITEM).fieldOf("inputMaterialKey").forGetter(TransmutationSpec::inputMaterialKey),
                                    Codec.INT.fieldOf("inputQuantity").forGetter(TransmutationSpec::inputQuantity),
                                    ResourceKey.codec(Registries.ITEM).fieldOf("outputMaterialKey").forGetter(TransmutationSpec::outputMaterialKey),
                                    Codec.INT.fieldOf("outputQuantity").forGetter(TransmutationSpec::outputQuantity),
                                    Codec.INT.fieldOf("numberOfStages").forGetter(TransmutationSpec::numberOfStages))
                            .apply(instance, TransmutationSpec::new));
}
