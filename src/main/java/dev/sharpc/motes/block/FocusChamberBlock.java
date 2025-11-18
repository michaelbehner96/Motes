package dev.sharpc.motes.block;

import com.mojang.serialization.MapCodec;
import dev.sharpc.motes.blockentity.FocusChamberBlockEntity;
import dev.sharpc.motes.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FocusChamberBlock extends BaseEntityBlock
{
    public FocusChamberBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult)
    {
        if (level.isClientSide())
            return InteractionResult.SUCCESS;

        var blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof FocusChamberBlockEntity chamber)
        {
            player.openMenu(chamber);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec()
    {
        return simpleCodec(FocusChamberBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new FocusChamberBlockEntity(blockPos, blockState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
    {
        if (level instanceof ServerLevel)
            return createTickerHelper(blockEntityType, ModBlockEntities.FOCUS_CHAMBER, FocusChamberBlockEntity::onServerTick);
        else
            return null;
    }
}
