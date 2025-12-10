package dev.sharpc.motes.mote.aspect.aspects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.data.model.mote.EssentiaId;
import dev.sharpc.motes.mote.aspect.MoteAspectType;
import dev.sharpc.motes.mote.aspect.MoteAspectTypes;

public record ManifestatioAspect(EssentiaId essentiaId, int ticksPerCycle, int outputPerCycle) implements MoteAspect
{
    public static final Codec<ManifestatioAspect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssentiaId.CODEC.fieldOf("essentiaId").forGetter(ManifestatioAspect::essentiaId),
            Codec.INT.fieldOf("ticksPerCycle").forGetter(ManifestatioAspect::ticksPerCycle),
            Codec.INT.fieldOf("outputPerCycle").forGetter(ManifestatioAspect::outputPerCycle)
    ).apply(instance, ManifestatioAspect::new));

    public ManifestatioAspect
    {
        if (essentiaId == null) throw new IllegalArgumentException("Essentia Id cannot be null.");
        if (ticksPerCycle <= 0) throw new IllegalArgumentException("Ticks per cycle must be greater than zero.");
        if (outputPerCycle <= 0) throw new IllegalArgumentException("Output per cycle must be greater than zero.");
    }

    @Override
    public MoteAspectType<?> type()
    {
        return MoteAspectTypes.MANIFESTATIO;
    }
}
