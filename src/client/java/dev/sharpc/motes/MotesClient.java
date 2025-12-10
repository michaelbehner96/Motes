package dev.sharpc.motes;

import com.mojang.blaze3d.platform.InputConstants;
import dev.sharpc.motes.mote.aspect.MoteAspectTypes;
import dev.sharpc.motes.mote.aspect.aspects.StabilityAspect;
import dev.sharpc.motes.mote.aspect.context.LevelingContext;
import dev.sharpc.motes.mote.aspect.context.ManifestatioContext;
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
                lines.add(Component.translatable("tooltip.motes.info").withStyle(ChatFormatting.DARK_GRAY));
                return;
            }

            var stabilityAspect = moteContext.getAspect(MoteAspectTypes.STABILITY);
            if (stabilityAspect != null)
                lines.add(Component.translatable("tooltip.motes.stability", styledStabilityComponent(stabilityAspect)).withStyle(ChatFormatting.GRAY));

            var levelingContext = LevelingContext.from(moteContext);
            if (levelingContext != null)
            {
                var experienceProgress = levelingContext.isMaxLevel()
                        ? Component.translatable("tooltip.motes.leveling.experience.progress.max").withStyle(ChatFormatting.GOLD)
                        : Component.translatable("tooltip.motes.leveling.experience.progress",
                        levelingContext.state().exp(),
                        levelingContext.experienceRequiredForNextLevel()).withStyle(ChatFormatting.GRAY);

                var experienceTooltip = Component.translatable("tooltip.motes.leveling.experience", experienceProgress).withStyle(ChatFormatting.GRAY);
                lines.add(experienceTooltip);
            }

            var manifestatioContext = ManifestatioContext.from(moteContext);
            if (manifestatioContext != null)
            {
                var essentiaName = Component.translatable(manifestatioContext.manifestatioAspect().essentiaId().getTranslationKey());
                var essentiaLabel = Component.translatable("item.motes.essentia.named", essentiaName).withStyle(ChatFormatting.WHITE);
                var essentiaTooltip = Component.translatable("tooltip.motes.manifestatio", essentiaName).withStyle(ChatFormatting.GRAY);
                lines.add(essentiaTooltip);
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