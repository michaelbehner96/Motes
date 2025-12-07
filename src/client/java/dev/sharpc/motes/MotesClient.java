package dev.sharpc.motes;

import com.mojang.blaze3d.platform.InputConstants;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspectType;
import dev.sharpc.motes.mote.aspect.aspects.MoteAspectTypes;
import dev.sharpc.motes.mote.aspect.aspects.StabilityAspect;
import dev.sharpc.motes.mote.aspect.context.LevelingContext;
import dev.sharpc.motes.mote.aspect.context.MoteContext;
import dev.sharpc.motes.registry.ModItems;
import dev.sharpc.motes.registry.ModMenus;
import dev.sharpc.motes.screen.FocusChamberScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class MotesClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        MenuScreens.register(ModMenus.FOCUS_CHAMBER, FocusChamberScreen::new);

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) ->
        {
            if (!stack.is(ModItems.MOTE)) return;

            var moteContext = MoteContext.from(stack);
            if (moteContext == null) return;

            if (!isHoldingShift())
            {
                lines.add(Component.literal("Hold [SHIFT] for more info").withStyle(ChatFormatting.DARK_GRAY));
                return;
            }

            var stabilityAspect = moteContext.getAspect(MoteAspectTypes.STABILITY);
            if (stabilityAspect != null)
            {
                lines.add(Component.empty()
                                   .append(Component.literal("Stability: ").withStyle(ChatFormatting.GRAY))
                                   .append(styledStabilityComponent(stabilityAspect))
                );
            }

            var levelingContext = LevelingContext.from(moteContext);
            if (levelingContext != null)
            {
                var levelProgress = levelingContext.isMaxLevel() ? "MAX" :
                        "%s/%s".formatted(
                                levelingContext.state().exp(),
                                levelingContext.aspect().experienceRequiredFor(levelingContext.state().level() + 1));

                var experienceComponent = levelingContext.isMaxLevel()
                        ? Component.translatable("tooltip.motes.leveling.progress.max").withStyle(ChatFormatting.GOLD)
                        : Component.translatable(
                        "tooltip.motes.leveling.progress",
                        levelingContext.state().exp(),
                        levelingContext.experienceRequiredForNextLevel()).withStyle(ChatFormatting.GRAY);

                lines.add(experienceComponent);
            }

        });
    }

    public static boolean isHoldingShift()
    {
        var window = Minecraft.getInstance().getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    private static Component styledStabilityComponent(StabilityAspect aspect)
    {
        if (aspect.base() >= 0.85)
            return Component.literal("HIGH").withStyle(ChatFormatting.GREEN);
        else if (aspect.base() >= 0.45)
            return Component.literal("MEDIUM").withStyle(ChatFormatting.YELLOW);
        else
            return Component.literal("LOW").withStyle(ChatFormatting.RED);
    }

}