package dev.sharpc.motes.mote.aspect;

import com.mojang.serialization.Codec;
import dev.sharpc.motes.Motes;
import dev.sharpc.motes.mote.aspect.aspects.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public final class MoteAspectTypes
{
    public static final HashMap<ResourceLocation, MoteAspectType<?>> TYPES = new HashMap<>();

    public static final MoteAspectType<StabilityAspect> STABILITY = register(Motes.createModdedResourceLocation("stability"), StabilityAspect.CODEC);
    public static final MoteAspectType<EnvironmentalAspect> ENVIRONMENTAL = register(Motes.createModdedResourceLocation("environmental"), EnvironmentalAspect.CODEC);
    public static final MoteAspectType<LevelingAspect> LEVELING = register(Motes.createModdedResourceLocation("leveling"), LevelingAspect.CODEC);
    public static final MoteAspectType<ManifestatioAspect> MANIFESTATIO = register(Motes.createModdedResourceLocation("manifestatio"), ManifestatioAspect.CODEC);

    private static <T extends MoteAspect> MoteAspectType<T> register(ResourceLocation id, Codec<T> codec)
    {
        var type = new MoteAspectType<>(id, codec);
        TYPES.put(id, type);
        return type;
    }

    public static @Nullable MoteAspectType<?> get(ResourceLocation id)
    {
        return TYPES.get(id);
    }

    public static Collection<MoteAspectType<?>> all()
    {
        return Collections.unmodifiableCollection(TYPES.values());
    }
}
