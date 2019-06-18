package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_verticalcorner", template = "parent_verticalcorner"),
        item = @Model(name = "item/%s_verticalcorner", parent = "block/%s_verticalcorner", template = "item/parent_verticalcorner"),
        block = {
                @Model(name = "block/%s_verticalcorner", template = "block/parent_verticalcorner"),
        }
)
public class VerticalCorner extends BlockHorizontal implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    private static final VoxelShape EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_EAST = Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.or(EAST, QTR_EAST);

    private static final VoxelShape WEST = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_WEST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.or(WEST, QTR_WEST);

    private static final VoxelShape NORTH = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape QTR_NORTH = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(NORTH, QTR_NORTH);

    private static final VoxelShape SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape QTR_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(SOUTH, QTR_SOUTH);


    public VerticalCorner(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));

    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
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
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
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
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
        if (state.get(WATERLOGGED)) {
            worldIn.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return !state.get(WATERLOGGED) && fluidIn == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
        if (!state.get(WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(WATERLOGGED,true), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }
            return true;
        } else {
            return false;
        }
    }

}
