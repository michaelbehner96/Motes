package dev.sharpc.motes.item;

import dev.sharpc.motes.registry.ModDataComponents;
import dev.sharpc.motes.registry.mote.MoteDefinitions;
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
            var definition = MoteDefinitions.get(moteId);

            if (definition != null)
            {
                consumer.accept(
                        Component.literal("Element: " + definition.element() + " | Tier: " + definition.tier())
                );

                consumer.accept(
                        Component.literal("Product: " + definition.productAmount() + " × " + definition.product().location())
                );
            }

            consumer.accept(Component.literal("MoteId: " + moteId.id()));
        }
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack)
    {
        var moteId = itemStack.get(ModDataComponents.MOTE_ID);

        if (moteId != null)
        {
            var definition = MoteDefinitions.get(moteId);

            if (definition != null)
            {
                var elementName = switch (definition.element())
                {
                    case FIRE -> "Fire";
                    case WATER -> "Water";
                    case EARTH -> "Earth";
                    case WIND -> "Wind";
                    case LIGHT -> "Light";
                    case DARK -> "Dark";
                };

                var tierLabel = switch (definition.tier())
                {
                    case 0 -> "Primal";
                    case 1 -> "Lesser";
                    case 2 -> "Greater";
                    case 3 -> "Ascendant";
                    default -> "Tier " + definition.tier();
                };

                return Component.literal(tierLabel + " " + elementName + " Mote");
            }
        }

        return super.getName(itemStack);
    }
}
