package dev.sharpc.motes.mote.aspect.aspects;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public record MoteAspectType<T extends MoteAspect>(ResourceLocation id, Codec<T> codec)
{
}
