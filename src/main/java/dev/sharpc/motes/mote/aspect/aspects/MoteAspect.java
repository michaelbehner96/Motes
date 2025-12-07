package dev.sharpc.motes.mote.aspect.aspects;

public sealed interface MoteAspect permits StabilityAspect, EnvironmentalAspect, LevelingAspect
{
    MoteAspectType<?> type();
}
