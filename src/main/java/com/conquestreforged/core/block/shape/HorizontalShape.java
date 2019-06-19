package com.conquestreforged.core.block.shape;

import com.conquestreforged.core.block.extensions.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A directional, non full-cube shape that can be waterlogged
 */
public abstract class HorizontalShape extends AbstractShape {

    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;

    public HorizontalShape(Properties builder) {
        super(builder);
    }

    @NonNull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();
        return super.getStateForPlacement(context).with(DIRECTION, facing);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION, WATERLOGGED);
    }
}
