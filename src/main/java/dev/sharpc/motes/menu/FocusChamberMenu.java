package dev.sharpc.motes.menu;

import dev.sharpc.motes.blockentity.FocusChamberBlockEntity;
import dev.sharpc.motes.menu.slot.MoteSlot;
import dev.sharpc.motes.menu.slot.OutputSlot;
import dev.sharpc.motes.menu.slot.SlotLayoutHelper;
import dev.sharpc.motes.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;


public class FocusChamberMenu extends AbstractContainerMenu
{
    private final Container container;
    private final ContainerData containerData;
    private final Level level;

    public static final Vector2i MOTE_SLOT_POSITION = new Vector2i(26, 30);
    public static final Vector2i CATALYST_SLOT_POSITION = new Vector2i(62, 30);
    public static final Vector2i REGRESSION_A_SLOT_POSITION = new Vector2i(17, 57);
    public static final Vector2i REGRESSION_B_SLOT_POSITION = new Vector2i(35, 57);
    public static final Vector2i PRODUCT_SLOT_POSITION = new Vector2i(134, 30);
    public static final Vector2i PLAYER_INVENTORY_POSITION = new Vector2i(8, 84);
    public static final Vector2i PLAYER_HOTBAR_POSITION = new Vector2i(8, 142);

    /**
     * Server-side constructor called by FocusChamberBlockEntity::createMenu.
     */
    public FocusChamberMenu(int syncId, Inventory playerInventory, Container container, ContainerData containerData)
    {
        super(ModMenus.FOCUS_CHAMBER, syncId);
        checkContainerSize(container, FocusChamberBlockEntity.CONTAINER_SIZE);
        checkContainerDataCount(containerData, FocusChamberBlockEntity.CONTAINER_DATA_COUNT);
        this.container = container;
        this.containerData = containerData;
        this.level = playerInventory.player.level();
        this.addDataSlots(containerData);
        SlotLayoutHelper.addSlot(container, FocusChamberBlockEntity.SLOT_MOTE, MOTE_SLOT_POSITION, MoteSlot::new, this::addSlot);
        SlotLayoutHelper.addSlot(container, FocusChamberBlockEntity.SLOT_PRODUCT, PRODUCT_SLOT_POSITION, OutputSlot::new, this::addSlot);
        SlotLayoutHelper.addSlot(container, FocusChamberBlockEntity.SLOT_REGRESSION_A, REGRESSION_A_SLOT_POSITION, OutputSlot::new, this::addSlot);
        SlotLayoutHelper.addSlot(container, FocusChamberBlockEntity.SLOT_REGRESSION_B, REGRESSION_B_SLOT_POSITION, OutputSlot::new, this::addSlot);
        SlotLayoutHelper.addSlot(container, FocusChamberBlockEntity.SLOT_CATALYST, CATALYST_SLOT_POSITION, this::addSlot);
        SlotLayoutHelper.addPlayerInventorySlots(playerInventory, PLAYER_INVENTORY_POSITION, this::addSlot);
        SlotLayoutHelper.addPlayerHotbarSlots(playerInventory, PLAYER_HOTBAR_POSITION, this::addSlot);
    }

    /**
     * Client-side only constructor.
     */
    public FocusChamberMenu(int syncId, Inventory playerInventory)
    {
        this(syncId, playerInventory, new SimpleContainer(FocusChamberBlockEntity.CONTAINER_SIZE), new SimpleContainerData(FocusChamberBlockEntity.CONTAINER_DATA_COUNT));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index)
    {
        var originalStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);

        if (slot.hasItem())
        {
            var stackInSlot = slot.getItem();
            originalStack = stackInSlot.copy();

            // 1) Clicked from block inventory → move to player
            if (index < FocusChamberBlockEntity.CONTAINER_SIZE)
            {
                if (!this.moveItemStackTo(
                        stackInSlot,
                        FocusChamberBlockEntity.CONTAINER_SIZE,
                        FocusChamberBlockEntity.CONTAINER_SIZE + 36,
                        true)) // try from hotbar backwards
                {
                    return ItemStack.EMPTY;
                }
            }
            // 2) Clicked from player inventory/hotbar → move to block
            else
            {
                // You can decide which block slots are valid.
                // Basic version: try to put into any block slot
                if (!this.moveItemStackTo(
                        stackInSlot,
                        0,
                        FocusChamberBlockEntity.CONTAINER_SIZE,
                        false))
                {
                    return ItemStack.EMPTY;
                }
            }

            // If the stack is now empty, clear the slot
            if (stackInSlot.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            } else
            {
                slot.setChanged();
            }

            // If nothing actually changed, bail
            if (stackInSlot.getCount() == originalStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return originalStack;
    }


    @Override
    public boolean stillValid(Player player)
    {
        return container.stillValid(player);
    }
}
