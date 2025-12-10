package dev.sharpc.motes.mote.aspect.system;

import dev.sharpc.motes.mote.aspect.component.LevelingComponent;
import dev.sharpc.motes.mote.aspect.context.LevelingContext;
import dev.sharpc.motes.registry.ModDataComponents;

public final class LevelingSystem
{
    public static LevelingContext addExperience(LevelingContext context, int amount)
    {
        if (amount <= 0)
            return context;

        var exp = context.state().exp() + amount;
        var level = context.state().level();
        var aspect = context.aspect();

        while (level < aspect.maxLevel())
        {
            int required = aspect.experienceRequiredFor(level + 1);
            if (required <= 0 || exp < required)
                break;

            exp -= required;
            level += 1;
        }

        context.moteContext().stack().set(
                ModDataComponents.MOTE_LEVELING,
                new LevelingComponent(exp, level)
        );

        return LevelingContext.from(context.moteContext());
    }
}
