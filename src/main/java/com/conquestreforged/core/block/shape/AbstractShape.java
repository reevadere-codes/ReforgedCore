package com.conquestreforged.core.block.shape;

import com.conquestreforged.core.block.extensions.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A non full-cube shape that can be 'waterlogged'
 */
public abstract class AbstractShape extends Block implements Waterloggable {

    public AbstractShape(Properties properties) {
        super(properties);
    }

    public abstract VoxelShape getShape(BlockState state);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state);
    }

    @NonNull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState fluid = context.getWorld().getFluidState(context.getPos());
        BlockState base = super.getStateForPlacement(context);
        if (base == null) {
            base = getDefaultState();
        }
        return base.with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}
