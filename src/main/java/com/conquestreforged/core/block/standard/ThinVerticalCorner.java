package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.extensions.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

@Assets(
        state = @State(name = "%s_thinverticalcorner", template = "parent_thinverticalcorner"),
        item = @Model(name = "item/%s_thinverticalcorner", parent = "block/%s_thinverticalcorner", template = "item/parent_thinverticalcorner"),
        block = {
                @Model(name = "block/%s_thinverticalcorner", template = "block/parent_thinverticalcorner"),
        }
)
public class ThinVerticalCorner extends HorizontalBlock implements Waterloggable  {

    private static final VoxelShape EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_EAST = Block.makeCuboidShape(3.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.or(EAST, QTR_EAST);

    private static final VoxelShape WEST = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_WEST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 13.0D, 16.0D, 3.0D);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.or(WEST, QTR_WEST);

    private static final VoxelShape NORTH = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_NORTH = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(NORTH, QTR_NORTH);

    private static final VoxelShape SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    private static final VoxelShape QTR_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 3.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(SOUTH, QTR_SOUTH);


    public ThinVerticalCorner(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));

    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state);
    }

    private VoxelShape getShape(BlockState state) {
        switch (state.get(HORIZONTAL_FACING)) {
            case NORTH:
            default:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case EAST:
                return EAST_SHAPE;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(HORIZONTAL_FACING, facing)
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }
}
