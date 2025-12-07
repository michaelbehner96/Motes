package dev.sharpc.motes.mote.aspect.aspects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LevelingAspect(
        int maxLevel,
        int baseExp,
        int perLevelBase,
        double perLevelExponent
) implements MoteAspect
{

    public static final Codec<LevelingAspect> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(

                            Codec.INT.optionalFieldOf("maxLevel", 100)
                                     .forGetter(LevelingAspect::maxLevel),

                            Codec.INT.optionalFieldOf("baseExp", 100)
                                     .forGetter(LevelingAspect::baseExp),

                            Codec.INT.optionalFieldOf("perLevelBase", 25)
                                     .forGetter(LevelingAspect::perLevelBase),

                            Codec.DOUBLE.optionalFieldOf("perLevelExponent", 1.0)
                                        .forGetter(LevelingAspect::perLevelExponent)

                    ).apply(instance, LevelingAspect::new)
            );

    public LevelingAspect
    {
        if (maxLevel < 0)
            throw new IllegalArgumentException("Max level cannot be negative.");

        if (baseExp < 0)
            throw new IllegalArgumentException("Base experience cannot be negative.");

        if (perLevelBase < 0)
            throw new IllegalArgumentException("Per level experience base cannot be negative.");

        if (perLevelExponent < 0)
            throw new IllegalArgumentException("Per level experience exponent cannot be negative.");
    }

    @Override
    public MoteAspectType<?> type()
    {
        return MoteAspectTypes.LEVELING;
    }

    public int experienceRequiredFor(int level)
    {
        if (level == maxLevel || level <= 1)
            return 0;

        return (int) (baseExp + perLevelBase * Math.pow(Math.max(0, level - 1), perLevelExponent));
    }
}
