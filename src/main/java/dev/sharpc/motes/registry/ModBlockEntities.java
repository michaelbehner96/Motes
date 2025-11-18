package dev.sharpc.motes.registry;

import dev.sharpc.motes.Motes;
import dev.sharpc.motes.blockentity.FocusChamberBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class ModBlockEntities
{
    public static final BlockEntityType<FocusChamberBlockEntity> FOCUS_CHAMBER =
            register("focus_chamber", FocusChamberBlockEntity::new, ModBlocks.FOCUS_CHAMBER);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> factory,
            Block... blocks)
    {
        var key = Motes.createModdedResourceKey(Registries.BLOCK_ENTITY_TYPE, name);
        var type = FabricBlockEntityTypeBuilder.<T>create(factory, blocks).build();

        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, type);
    }

    public static void initialize()
    {
    }
}
