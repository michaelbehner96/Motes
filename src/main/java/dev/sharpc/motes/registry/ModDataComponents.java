package dev.sharpc.motes.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.MoteData;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class ModDataComponents
{
    public static final DataComponentType<MoteData> MOTE = register("mote",
            DataComponentType.<MoteData>builder()
                             .persistent(MoteData.CODEC)
                             .build());

    private static <T> DataComponentType<T> register(String dataComponentName, DataComponentType<T> type)
    {
        var id = ResourceLocation.fromNamespaceAndPath(Motes.MOD_ID, dataComponentName);
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, type);
    }

    public static void initialize()
    {
    }
}
