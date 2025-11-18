package dev.sharpc.motes.blockentity;

import dev.sharpc.motes.data.mote.MoteDefinition;
import dev.sharpc.motes.data.mote.MoteId;
import dev.sharpc.motes.item.MoteItem;
import dev.sharpc.motes.menu.FocusChamberMenu;
import dev.sharpc.motes.registry.ModBlockEntities;
import dev.sharpc.motes.registry.ModDataComponents;
import dev.sharpc.motes.registry.mote.MoteDefinitions;
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
import org.jetbrains.annotations.Nullable;

public class FocusChamberBlockEntity extends BaseContainerBlockEntity
{
    public static final int SLOT_MOTE = 0;
    public static final int SLOT_PRODUCT = 1;
    public static final int SLOT_REGRESSION_A = 2;
    public static final int SLOT_REGRESSION_B = 3;
    public static final int SLOT_CATALYST = 4;
    public static final int CONTAINER_SIZE = 5;
    public static final int CONTAINER_DATA_COUNT = 0;
    private static final Component MENU_NAME = Component.translatable("container.focus_chamber");
    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

    private int progress, maxProgress;

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
            if (chamber.canProduce())
                chamber.doProductionCycle();
    }

    private void doProductionCycle()
    {
        var moteDefinition = getSlottedMoteDefinition();

        if (moteDefinition == null)
            return;

        maxProgress = getProductionCycleForTier(moteDefinition.tier());
        progress++;
        setChanged();

        if (progress >= maxProgress)
        {
            completeProductionCycle(moteDefinition);
            progress = 0;
            setChanged();
        }
    }

    private void completeProductionCycle(MoteDefinition producerMoteDefinition)
    {
        var productStack = getProductStack();
        var result = new ItemStack(producerMoteDefinition.getProductAsItem(), producerMoteDefinition.productAmount());
        if (productStack.isEmpty())
        {
            setItem(SLOT_PRODUCT, result);
        } else if (ItemStack.isSameItemSameComponents(productStack, result))
        {
            productStack.grow(result.getCount());
        }
    }

    public ItemStack getMoteStack()
    {
        return getItem(SLOT_MOTE);
    }

    public ItemStack getProductStack()
    {
        return getItem(SLOT_PRODUCT);
    }

    public ItemStack getCatalystStack()
    {
        return getItem(SLOT_CATALYST);
    }

    public ItemStack getRegressionStackA()
    {
        return getItem(SLOT_REGRESSION_A);
    }

    public ItemStack getRegressionStackB()
    {
        return getItem(SLOT_REGRESSION_B);
    }

    public ItemStack[] getRegressionStacks()
    {
        return new ItemStack[]{getRegressionStackA(), getRegressionStackB()};
    }

    public boolean productSlotIsFull()
    {
        return isStackFull(getProductStack());
    }

    public boolean regressionSlotsAreFull()
    {
        return areStacksFull(getRegressionStacks());
    }

    private boolean isStackFull(ItemStack itemStack)
    {
        if (itemStack.isEmpty())
            return false;

        return itemStack.getCount() >= itemStack.getMaxStackSize();
    }

    private boolean areStacksFull(ItemStack... itemStacks)
    {
        for (ItemStack itemStack : itemStacks)
            if (isStackFull(itemStack))
                return true;

        return false;
    }

    private @Nullable MoteId getSlottedMoteId()
    {
        return getMoteStack().get(ModDataComponents.MOTE_ID);
    }

    private @Nullable MoteDefinition getSlottedMoteDefinition()
    {
        var moteId = getSlottedMoteId();

        if (moteId != null)
            return MoteDefinitions.get(moteId);

        return null;
    }

    private boolean hasSlottedMote()
    {
        return getMoteStack().getItem() instanceof MoteItem;
    }

    private boolean canOutputProduct()
    {
        if (productSlotIsFull())
            return false;

        var moteDefinition = getSlottedMoteDefinition();

        if (moteDefinition != null)
        {
            var moteProduct = moteDefinition.getProductAsItem();

            if (moteProduct == null)
                return false;

            var productsMatch = getProductStack().isEmpty() || getProductStack().is(moteProduct);
            var productStackHasRoom =
                    getProductStack().getMaxStackSize() <= getProductStack().getCount() + moteDefinition.productAmount();

            return productsMatch && productStackHasRoom;
        }

        return false;
    }

    private boolean canProduce()
    {
        if (!hasSlottedMote())
            return false;

        if (getSlottedMoteDefinition() == null)
            return false;

        if (!canOutputProduct())
            return false;

        if (regressionSlotsAreFull())
            return false;

        return true;
    }

    public static int getProductionCycleForTier(int tier)
    {
        return switch (tier)
        {
            case 0 -> 20 * 6;   // 60s
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
