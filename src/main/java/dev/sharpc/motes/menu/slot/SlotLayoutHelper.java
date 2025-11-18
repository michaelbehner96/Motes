package dev.sharpc.motes.menu.slot;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.joml.Vector2i;

import java.util.function.Consumer;

public final class SlotLayoutHelper
{
    public static void addPlayerHotbarSlots(Inventory playerInventory,
            Vector2i startingPosition,
            int slotOffset,
            Consumer<Slot> slotConsumer)
    {
        // Hot bar (9 slots, indexes 0 - 8)
        int hotbarWidth = 9;
        int hotbarHeight = 1;
        int inventoryIndex = 0;

        for (int hotbarY = 0; hotbarY < hotbarHeight; hotbarY++)
        {
            for (int hotbarX = 0; hotbarX < hotbarWidth; hotbarX++)
            {
                int dx = startingPosition.x + (hotbarX * slotOffset);
                int dy = startingPosition.y;
                slotConsumer.accept(new Slot(playerInventory, inventoryIndex, dx, dy));
                inventoryIndex++;
            }
        }
    }

    public static void addPlayerHotbarSlots(Inventory playerInventory,
            Vector2i startingPosition,
            Consumer<Slot> slotConsumer)
    {
        addPlayerHotbarSlots(playerInventory, startingPosition, 18, slotConsumer);
    }

    public static void addPlayerInventorySlots(Inventory playerInventory,
            Vector2i startingPosition,
            int slotOffsetX,
            int slotOffsetY,
            Consumer<Slot> slotConsumer)
    {
        // Inventory (27 slots, indexes 9 - 35)
        int inventoryWidth = 9;
        int inventoryHeight = 3;
        int inventoryIndex = 9;

        for (int inventoryY = 0; inventoryY < inventoryHeight; inventoryY++)
        {
            for (int inventoryX = 0; inventoryX < inventoryWidth; inventoryX++)
            {
                int dx = startingPosition.x + (inventoryX * slotOffsetX);
                int dy = startingPosition.y + (inventoryY * slotOffsetY);
                slotConsumer.accept(new Slot(playerInventory, inventoryIndex, dx, dy));
                inventoryIndex++;
            }
        }
    }

    public static void addPlayerInventorySlots(Inventory playerInventory,
            Vector2i startingPosition,
            Consumer<Slot> slotConsumer)
    {
        addPlayerInventorySlots(playerInventory, startingPosition, 18, 18, slotConsumer);
    }

}
