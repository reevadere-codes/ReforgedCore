package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
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
        state = @State(name = "%s_smallwindow", template = "parent_smallwindow"),
        item = @Model(name = "item/%s_smallwindow", parent = "block/%s_smallwindow", template = "item/parent_smallwindow"),
        block = {
                @Model(name = "block/%s_smallwindow", template = "block/parent_smallwindow"),
                @Model(name = "block/%s_smallwindow_down", template = "block/parent_smallwindow_down"),
                @Model(name = "block/%s_smallwindow_up", template = "block/parent_smallwindow_up"),
                @Model(name = "block/%s_smallwindow_updown", template = "block/parent_smallwindow_updown"),
        }
)
public class SmallWindow extends Block implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape TOPLEFT = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
    private static final VoxelShape TOPRIGHT = Block.makeCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    private static final VoxelShape BOTTOMLEFT = Block.makeCuboidShape(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
    private static final VoxelShape BOTTOMRIGHT = Block.makeCuboidShape(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);

    private static final VoxelShape TOP_SOUTH = Block.makeCuboidShape(4.0D, 13.5D, 12.0D, 12.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_WEST = Block.makeCuboidShape(0.0D, 13.5D, 4.0D, 4.0D, 16.0D, 12.0D);
    private static final VoxelShape TOP_NORTH = Block.makeCuboidShape(4.0D, 13.5D, 0.0D, 12.0D, 16.0D, 4.0D);
    private static final VoxelShape TOP_EAST = Block.makeCuboidShape(12.0D, 13.5D, 4.0D, 16.0D, 16.0D, 12.0D);

    private static final VoxelShape BOTTOM_SOUTH = Block.makeCuboidShape(4.0D, 0.0D, 12.0D, 12.0D, 2.5D, 16.0D);
    private static final VoxelShape BOTTOM_WEST = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 4.0D, 2.5D, 12.0D);
    private static final VoxelShape BOTTOM_NORTH = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 2.5D, 4.0D);
    private static final VoxelShape BOTTOM_EAST = Block.makeCuboidShape(12.0D, 0.0D, 4.0D, 16.0D, 2.5D, 12.0D);

    private static final VoxelShape TOP_SHAPE = VoxelShapes.or(TOPLEFT, TOPRIGHT);
    private static final VoxelShape BOTTOM_SHAPE = VoxelShapes.or(BOTTOMLEFT, BOTTOMRIGHT);
    private static final VoxelShape SHAPE = VoxelShapes.or(TOP_SHAPE, BOTTOM_SHAPE);

    private static final VoxelShape UP_NESW = VoxelShapes.or(TOP_NORTH, VoxelShapes.or(TOP_EAST, VoxelShapes.or(TOP_SOUTH, TOP_WEST)));
    private static final VoxelShape DOWN_NESW = VoxelShapes.or(BOTTOM_NORTH, VoxelShapes.or(BOTTOM_EAST, VoxelShapes.or(BOTTOM_SOUTH, BOTTOM_WEST)));

    private static final VoxelShape UP_SHAPE = VoxelShapes.or(SHAPE, UP_NESW);
    private static final VoxelShape DOWN_SHAPE = VoxelShapes.or(SHAPE, DOWN_NESW);
    private static final VoxelShape UPDOWN_SHAPE = VoxelShapes.or(SHAPE, VoxelShapes.or(UP_NESW, DOWN_NESW));

    public SmallWindow(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(UP,false).with(DOWN,false).with(WATERLOGGED, false));

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

    private VoxelShape getShape(BlockState state){
        if (state.get(DOWN) == true && state.get(UP) == true) {
            return SHAPE;
        } else if (state.get(DOWN) == false && state.get(UP) == true) {
            return DOWN_SHAPE;
        } else if (state.get(DOWN) == true && state.get(UP) == false) {
            return UP_SHAPE;
        } else {
            return UPDOWN_SHAPE;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, WATERLOGGED);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos up = blockpos.up();
        BlockPos down = blockpos.down();

        BlockState BlockStateUp = iblockreader.getBlockState(up);
        BlockState BlockStateDown = iblockreader.getBlockState(down);
        return super.getStateForPlacement(context)
                .with(UP, this.attachesTo(BlockStateUp))
                .with(DOWN, this.attachesTo(BlockStateDown))
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        boolean flag = this.canConnectTo(worldIn, currentPos.up());
        boolean flag1 = this.canConnectTo(worldIn, currentPos.down());
        return stateIn.with(UP, flag).with(DOWN, flag1);
    }

    private boolean attachesTo(BlockState blockstate) {
        Block block = blockstate.getBlock();
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof SmallWindow)));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof SmallWindow)));
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
