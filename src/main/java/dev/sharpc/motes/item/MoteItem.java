package dev.sharpc.motes.item;

import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.registry.ModDataComponents;
import dev.sharpc.motes.registry.ModItems;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class MoteItem extends Item
{
    public MoteItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull Component getName(ItemStack stack)
    {
        var customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null)
            return customName;

        var moteId = stack.get(ModDataComponents.MOTE_ID);

        if (moteId == null)
            return super.getName(stack);

        return Component.translatable("item.motes.mote.named",
                Component.translatable(moteId.getTranslationKey()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        var id = itemStack.get(ModDataComponents.MOTE_ID);

        if (id != null)
        {
            consumer.accept(Component.literal("MoteId: " + id.id()));
            consumer.accept(Component.literal("Mote Model: " + id.id()));

            var definition = MoteDefinitions.get(id);

            if (definition != null)
                consumer.accept(Component.literal("Stability: " + definition.stability()));
        }
    }

    public static ItemStack stackOf(MoteId id, int quantity)
    {
        var stack = new ItemStack(ModItems.MOTE, quantity);

        // Set the item model for dynamic texturing.
        stack.set(DataComponents.ITEM_MODEL, id.id());

        // Set the MoteId data component, defining the mote itself.
        stack.set(ModDataComponents.MOTE_ID, id);

        return stack;
    }

    public static ItemStack stackOf(MoteId id)
    {
        return stackOf(id, 1);
    }
}
