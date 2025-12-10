package dev.sharpc.motes.mote.aspect;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspect;
import net.minecraft.resources.ResourceLocation;

public record MoteAspectType<T extends MoteAspect>(ResourceLocation id, Codec<T> codec)
{
}
