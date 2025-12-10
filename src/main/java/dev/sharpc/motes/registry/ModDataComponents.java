package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.EssentiaId;
import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.mote.aspect.component.LevelingComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public final class ModDataComponents
{
    public static final DataComponentType<MoteId> MOTE_ID = register("mote_id",
            DataComponentType.<MoteId>builder()
                             .persistent(MoteId.CODEC)
                             .build());

    public static final DataComponentType<EssentiaId> ESSENTIA_ID = register("essentia_id",
            DataComponentType.<EssentiaId>builder()
                             .persistent(EssentiaId.CODEC)
                             .build());

    public static final DataComponentType<LevelingComponent> MOTE_LEVELING = register("mote_leveling",
            DataComponentType.<LevelingComponent>builder()
                             .persistent(LevelingComponent.CODEC)
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
