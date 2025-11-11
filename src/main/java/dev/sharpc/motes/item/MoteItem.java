package dev.sharpc.motes.item;

import dev.sharpc.motes.data.MoteData;
import dev.sharpc.motes.registry.ModDataComponents;
import net.fabricmc.loader.impl.util.StringUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Optional;

public class MoteItem extends Item
{
    public MoteItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack)
    {
        return Optional.ofNullable(itemStack.get(ModDataComponents.MOTE))
                       .map(moteData ->
                       {
                           var item = BuiltInRegistries.ITEM.get(moteData.product()).get();
                           var path = item.key().location().getPath();
                           var displayName = StringUtil.capitalize(path.replace('_', ' '));

                           return Component.literal(displayName + " Mote");
                       })
                       .orElseGet(() -> (MutableComponent) this.getName(itemStack));
    }
}
