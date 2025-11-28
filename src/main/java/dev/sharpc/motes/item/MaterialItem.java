package dev.sharpc.motes.item;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.data.model.mote.MaterialId;
import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.data.registry.MoteDefinitions;
import dev.sharpc.motes.registry.ModDataComponents;
import dev.sharpc.motes.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MaterialItem extends Item
{
    public MaterialItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack)
    {
        var customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null)
            return customName;

        var materialId = stack.get(ModDataComponents.MATERIAL_ID);

        if (materialId == null)
            return super.getName(stack);

        return Component.translatable("item.motes.material.named",
                Component.translatable(materialId.getTranslationKey()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        var id = itemStack.get(ModDataComponents.MATERIAL_ID);

        if (id != null)
            consumer.accept(Component.literal("MaterialId: " + id.id()));
    }


    public static ItemStack stackOf(MaterialId id, int quantity)
    {
        var stack = new ItemStack(ModItems.MATERIAL, quantity);
        stack.set(DataComponents.ITEM_MODEL, id.id().withPrefix(Motes.MATERIAL_PATH_PREFIX));
        stack.set(ModDataComponents.MATERIAL_ID, id);
        return stack;
    }

    public static ItemStack stackOf(MaterialId id)
    {
        return stackOf(id, 1);
    }
}
