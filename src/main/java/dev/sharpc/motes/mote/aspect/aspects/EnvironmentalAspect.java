package dev.sharpc.motes.mote.aspect.aspects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record EnvironmentalAspect(
        List<ResourceLocation> preferredBiomes,
        List<ResourceLocation> dislikedBiomes,
        double preferredMultiplier,
        double dislikedMultiplier,
        double neutralMultiplier
) implements MoteAspect
{
    public static final Codec<EnvironmentalAspect> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(

                    Codec.list(ResourceLocation.CODEC)
                         .optionalFieldOf("preferredBiomes", List.of())
                         .forGetter(EnvironmentalAspect::preferredBiomes),

                    Codec.list(ResourceLocation.CODEC)
                         .optionalFieldOf("dislikedBiomes", List.of())
                         .forGetter(EnvironmentalAspect::dislikedBiomes),

                    Codec.DOUBLE.optionalFieldOf("preferredMultiplier", 1.25)
                                .forGetter(EnvironmentalAspect::preferredMultiplier),

                    Codec.DOUBLE.optionalFieldOf("dislikedMultiplier", 0.75)
                                .forGetter(EnvironmentalAspect::dislikedMultiplier),

                    Codec.DOUBLE.optionalFieldOf("neutralMultiplier", 1.0)
                                .forGetter(EnvironmentalAspect::neutralMultiplier)

            ).apply(instance, EnvironmentalAspect::new));

    public EnvironmentalAspect
    {
        if (dislikedMultiplier < 0)
            throw new IllegalArgumentException("Disliked multiplier cannot be negative.");

        if (preferredMultiplier < 0)
            throw new IllegalArgumentException("Preferred multiplier cannot be negative.");

        if (neutralMultiplier < 0)
            throw new IllegalArgumentException("Neutral multiplier cannot be negative.");
    }

    @Override
    public MoteAspectType<?> type()
    {
        return MoteAspectTypes.ENVIRONMENTAL;
    }
}
