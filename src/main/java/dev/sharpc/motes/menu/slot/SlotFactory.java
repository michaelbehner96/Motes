package dev.sharpc.motes.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

@FunctionalInterface
public interface SlotFactory<T extends Slot>
{
    T create(Container container, int index, int x, int y);
}
