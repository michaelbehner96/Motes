package dev.sharpc.motes.mote.aspect.aspects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.mote.aspect.MoteAspectType;
import dev.sharpc.motes.mote.aspect.MoteAspectTypes;

public record StabilityAspect(
        double base
) implements MoteAspect
{

    public static final Codec<StabilityAspect> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(

                            Codec.DOUBLE.optionalFieldOf("base", 1.0)
                                        .forGetter(StabilityAspect::base)

                    ).apply(instance, StabilityAspect::new)
            );

    public StabilityAspect
    {
        if (base < 0 || base > 1)
            throw new IllegalArgumentException("Base stability must be a value between 0 and 1 inclusive.");
    }

    @Override
    public MoteAspectType<?> type()
    {
        return MoteAspectTypes.STABILITY;
    }


}
