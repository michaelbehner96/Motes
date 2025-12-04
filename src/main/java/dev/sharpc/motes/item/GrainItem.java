package dev.sharpc.motes.item;

import dev.sharpc.motes.data.model.mote.GrainId;
import dev.sharpc.motes.registry.ModDataComponents;
import dev.sharpc.motes.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class GrainItem extends Item
{
    public GrainItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull Component getName(ItemStack stack)
    {
        var customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null)
            return customName;

        var grainId = stack.get(ModDataComponents.GRAIN_ID);

        if (grainId == null)
            return super.getName(stack);

        return Component.translatable("item.motes.grain.named",
                Component.translatable(grainId.getTranslationKey()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        var id = itemStack.get(ModDataComponents.GRAIN_ID);

        if (id != null)
            consumer.accept(Component.literal("GrainId: " + id.id()));
    }

    public static ItemStack stackOf(GrainId id, int quantity)
    {
        var stack = new ItemStack(ModItems.GRAIN, quantity);
        stack.set(DataComponents.ITEM_MODEL, id.id());
        stack.set(ModDataComponents.GRAIN_ID, id);
        return stack;
    }

    public static ItemStack stackOf(GrainId id)
    {
        return stackOf(id, 1);
    }

}
