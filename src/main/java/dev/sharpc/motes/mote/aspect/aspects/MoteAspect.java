package dev.sharpc.motes.mote.aspect.aspects;

import dev.sharpc.motes.mote.aspect.MoteAspectType;

public sealed interface MoteAspect permits StabilityAspect, EnvironmentalAspect, LevelingAspect, ManifestatioAspect
{
    MoteAspectType<?> type();
}
