package dev.sharpc.motes.item;

import dev.sharpc.motes.registry.ModDataComponents;
import net.fabricmc.loader.impl.util.StringUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public class MoteItem extends Item
{
    public MoteItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag)
    {
        var moteId = itemStack.get(ModDataComponents.MOTE_ID);

        if (moteId != null)
        {
            consumer.accept(Component.literal("MoteId: " + moteId.id()));
        }
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack)
    {
        return Optional.ofNullable(itemStack.get(ModDataComponents.MOTE_ID))
                       .map(moteId ->
                       {
                           var path = moteId.id().getPath();
                           var displayName = StringUtil.capitalize(path.replace('_', ' '));
                           return Component.literal(displayName + " Mote");
                       })
                       .orElseGet(() -> (MutableComponent) this.getName(itemStack));
    }
}
