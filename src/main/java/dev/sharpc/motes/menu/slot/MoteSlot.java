package dev.sharpc.motes.menu.slot;

import dev.sharpc.motes.item.MoteItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MoteSlot extends Slot
{
    public MoteSlot(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof MoteItem;
    }
}
