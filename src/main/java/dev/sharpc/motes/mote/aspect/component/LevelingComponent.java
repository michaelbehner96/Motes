package dev.sharpc.motes.mote.aspect.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.sharpc.motes.registry.ModDataComponents;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record LevelingComponent(int exp, int level)
{
    public static final Codec<LevelingComponent> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(

                            Codec.INT.optionalFieldOf("exp", 0)
                                     .forGetter(LevelingComponent::exp),

                            Codec.INT.optionalFieldOf("level", 1)
                                     .forGetter(LevelingComponent::level)

                    ).apply(instance, LevelingComponent::new)
            );

    public LevelingComponent
    {
        if (exp < 0)
            throw new IllegalArgumentException("Current experience level cannot be negative.");

        if (level < 0)
            throw new IllegalArgumentException("Current level cannot be negative.");
    }

    public static @Nullable LevelingComponent from(ItemStack stack)
    {
        return stack.get(ModDataComponents.MOTE_LEVELING);
    }

    public static LevelingComponent fromOrInit(ItemStack stack)
    {
        var component = from(stack);
        if (component != null) return component;

        component = LevelingComponent.ofDefault();
        stack.set(ModDataComponents.MOTE_LEVELING, component);

        return component;
    }

    public static LevelingComponent ofDefault()
    {
        return new LevelingComponent(0, 1);
    }
}