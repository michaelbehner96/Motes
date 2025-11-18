package dev.sharpc.motes.blockentity;

import dev.sharpc.motes.menu.FocusChamberMenu;
import dev.sharpc.motes.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

public class FocusChamberBlockEntity extends BaseContainerBlockEntity
{
    public static final int SLOT_MOTE = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int SLOT_REGRESSION_A = 2;
    public static final int SLOT_REGRESSION_B = 3;
    public static final int CONTAINER_SIZE = 4;
    public static final int CONTAINER_DATA_COUNT = 0;
    private static final Component MENU_NAME = Component.translatable("container.focus_chamber");
    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    protected final ContainerData containerData = new ContainerData()
    {
        @Override
        public int get(int index)
        {
            return 0;
        }

        @Override
        public void set(int index, int value)
        {

        }

        @Override
        public int getCount()
        {
            return CONTAINER_DATA_COUNT;
        }
    };

    public FocusChamberBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlockEntities.FOCUS_CHAMBER, blockPos, blockState);
    }

    public static void onServerTick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity)
    {
        if (blockEntity instanceof FocusChamberBlockEntity chamber)
        {

        }
    }

    public static int getProductionCycleForTier(int tier)
    {
        return switch (tier)
        {
            case 0 -> 20 * 60;   // 60s
            case 1 -> 20 * 90;   // 90s
            case 2 -> 20 * 140;  // 140s
            case 3 -> 20 * 210;  // 210s
            case 4 -> 20 * 320;  // 320s
            case 5 -> 20 * 480;  // 480s
            default -> 20 * 600; // fallback = 10 minutes
        };
    }

    @Override
    protected @NotNull Component getDefaultName()
    {
        return MENU_NAME;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int syncId, Inventory playerInventory)
    {
        return new FocusChamberMenu(syncId, playerInventory, this, containerData);
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems()
    {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList)
    {
        for (int i = 0; i < this.items.size(); i++)
        {
            this.items.set(i, items.get(i));
        }
    }

    @Override
    public int getContainerSize()
    {
        return items.size();
    }

    @Override
    protected void loadAdditional(ValueInput valueInput)
    {
        super.loadAdditional(valueInput);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(valueInput, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput)
    {
        super.saveAdditional(valueOutput);
        ContainerHelper.saveAllItems(valueOutput, this.items);
    }
}
