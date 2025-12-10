package dev.sharpc.motes.item;

import dev.sharpc.motes.data.model.mote.EssentiaId;
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
public class EssentiaItem extends Item
{
    public EssentiaItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull Component getName(ItemStack stack)
    {
        var customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null)
            return customName;

        var essentiaId = stack.get(ModDataComponents.ESSENTIA_ID);

        if (essentiaId == null)
            return super.getName(stack);

        return Component.translatable("item.motes.essentia.named",
                Component.translatable(essentiaId.getTranslationKey()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        var id = itemStack.get(ModDataComponents.ESSENTIA_ID);

        if (id != null)
            consumer.accept(Component.literal("EssentiaId: " + id.id()));
    }

    public static ItemStack stackOf(EssentiaId id, int quantity)
    {
        var stack = new ItemStack(ModItems.ESSENTIA, quantity);
        stack.set(DataComponents.ITEM_MODEL, id.id());
        stack.set(ModDataComponents.ESSENTIA_ID, id);
        return stack;
    }

    public static ItemStack stackOf(EssentiaId id)
    {
        return stackOf(id, 1);
    }

}
