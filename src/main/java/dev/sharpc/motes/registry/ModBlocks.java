package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.block.FocusChamberBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public final class ModBlocks
{
    public static final Block FOCUS_CHAMBER =
            registerWithItem("focus_chamber",
                    FocusChamberBlock::new,
                    BlockBehaviour.Properties.of()
                                             .mapColor(MapColor.COLOR_BLUE)
                                             .requiresCorrectToolForDrops()
                                             .strength(3.0f, 6.0f)
                                             .sound(SoundType.BASALT));

    public static Block register(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties)
    {
        var key = Motes.createModdedResourceKey(Registries.BLOCK, name);
        var block = function.apply(properties.setId(key));

        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    public static Block registerWithItem(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties)
    {
        var block = register(name, function, properties);
        ModItems.register(name, (props) -> new BlockItem(block, props), new Item.Properties());

        return block;
    }

    public static void initialize()
    {
    }
}
