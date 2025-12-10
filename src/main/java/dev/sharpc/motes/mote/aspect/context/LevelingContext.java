package dev.sharpc.motes.mote.aspect.context;

import dev.sharpc.motes.mote.aspect.component.LevelingComponent;
import dev.sharpc.motes.mote.aspect.aspects.LevelingAspect;
import dev.sharpc.motes.mote.aspect.MoteAspectTypes;
import org.jetbrains.annotations.Nullable;

public record LevelingContext(
        MoteContext moteContext,
        LevelingAspect aspect,
        LevelingComponent state
)
{
    public static @Nullable LevelingContext from(MoteContext context)
    {
        var levelingAspect = context.getAspect(MoteAspectTypes.LEVELING);
        if (levelingAspect == null) return null;

        var levelingComponent = LevelingComponent.fromOrInit(context.stack());

        return new LevelingContext(context, levelingAspect, levelingComponent);
    }

    public boolean isMaxLevel()
    {
        return state.level() == aspect.maxLevel();
    }

    public int experienceUntilNextLevel()
    {
        if (isMaxLevel())
            return 0;

        return aspect.experienceRequiredFor(state.level() + 1) - state.exp();
    }

    public int experienceRequiredForNextLevel()
    {
        if (isMaxLevel())
            return 0;

        return aspect.experienceRequiredFor(state.level() + 1);
    }
}
