package dev.sharpc.motes.mote.aspect.context;

import dev.sharpc.motes.data.model.mote.MoteDefinition;
import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspect;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspectType;
import dev.sharpc.motes.registry.ModDataComponents;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record MoteContext(
        ItemStack stack,
        MoteId id,
        MoteDefinition definition
)
{

    public static @Nullable MoteContext from(ItemStack stack)
    {
        var id = stack.get(ModDataComponents.MOTE_ID);
        if (id == null) return null;

        var def = MoteDefinitions.get(id);
        if (def == null) return null;

        return new MoteContext(stack, id, def);
    }

    @SuppressWarnings("unchecked")
    public <T extends MoteAspect> @Nullable T getAspect(MoteAspectType<T> type)
    {
        var module = definition.aspects().get(type);

        if (module == null)
        {
            return null;
        }

        return (T) module;
    }
}
