package dev.sharpc.motes.item;

import dev.sharpc.motes.data.model.mote.MoteId;
import dev.sharpc.motes.mote.aspect.context.LevelingContext;
import dev.sharpc.motes.mote.aspect.context.MoteContext;
import dev.sharpc.motes.mote.aspect.component.LevelingComponent;
import dev.sharpc.motes.mote.aspect.system.LevelingSystem;
import dev.sharpc.motes.registry.ModDataComponents;
import dev.sharpc.motes.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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

        var moteContext = MoteContext.from(stack);
        if (moteContext == null) return super.getName(stack);

        var baseName = Component.translatable(moteContext.id().getTranslationKey());
        var result = Component.translatable("item.motes.mote.named", baseName);

        var levelingContext = LevelingContext.from(moteContext);
        if (levelingContext == null) return result;

        Component levelComponent = levelingContext.isMaxLevel()
                ? Component.translatable("mote.level.max", levelingContext.state().level()).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)
                : Component.translatable("mote.level.format", levelingContext.state().level()).withStyle(ChatFormatting.GRAY);

        return Component.translatable("item.motes.mote.named.with_level", result, levelComponent);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if (!stack.is(ModItems.MOTE))
            return InteractionResult.PASS;

        if (!level.isClientSide())
        {
            var moteContext = MoteContext.from(stack);
            if (moteContext == null) return InteractionResult.PASS;

            var levelingContext = LevelingContext.from(moteContext);
            if (levelingContext == null) return InteractionResult.PASS;

            LevelingSystem.addExperience(levelingContext, 1000);
        }

        return InteractionResult.SUCCESS;

    }

    public static ItemStack stackOf(MoteId id, int quantity)
    {
        var stack = new ItemStack(ModItems.MOTE, quantity);
        stack.set(DataComponents.ITEM_MODEL, id.id());
        stack.set(ModDataComponents.MOTE_ID, id);
        stack.set(ModDataComponents.MOTE_LEVELING, LevelingComponent.ofDefault());
        return stack;
    }

    public static ItemStack stackOf(MoteId id)
    {
        return stackOf(id, 1);
    }
}
