package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_arrowslit", template = "parent_arrowslit"),
        item = @Model(name = "item/%s_arrowslit", parent = "block/%s_arrowslit", template = "item/parent_arrowslit"),
        block = {
                @Model(name = "block/%s_arrowslit", template = "block/parent_arrowslit"),
        },
        recipe = @Recipe(
                name = "%s_slab",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class ArrowSlit extends HorizontalBlock implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape EAST_FR = Block.makeCuboidShape(0.0D, 0.0D, 9.0D, 1.0D, 16.0D, 13.0D);
    private static final VoxelShape EAST_FL = Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 7.0D);
    private static final VoxelShape EAST_SR = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SL = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 3.0D);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.or(VoxelShapes.or(EAST_FR, EAST_FL), VoxelShapes.or(EAST_SR, EAST_SL));

    private static final VoxelShape WEST_FR = Block.makeCuboidShape(16.0D, 0.0D, 9.0D, 15.0D, 16.0D, 13.0D);
    private static final VoxelShape WEST_FL = Block.makeCuboidShape(16.0D, 0.0D, 3.0D, 15.0D, 16.0D, 7.0D);
    private static final VoxelShape WEST_SR = Block.makeCuboidShape(16.0D, 0.0D, 13.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SL = Block.makeCuboidShape(16.0D, 0.0D, 0.0D, 8.0D, 16.0D, 3.0D);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.or(VoxelShapes.or(WEST_FR, WEST_FL), VoxelShapes.or(WEST_SR, WEST_SL));

    private static final VoxelShape NORTH_FR = Block.makeCuboidShape(9.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_FL = Block.makeCuboidShape(3.0D, 0.0D, 15.0D, 7.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SR = Block.makeCuboidShape(13.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SL = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 3.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(VoxelShapes.or(NORTH_FR, NORTH_FL), VoxelShapes.or(NORTH_SR, NORTH_SL));

    private static final VoxelShape SOUTH_FR = Block.makeCuboidShape(9.0D, 0.0D, 1.0D, 13.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_FL = Block.makeCuboidShape(3.0D, 0.0D, 1.0D, 7.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_SR = Block.makeCuboidShape(13.0D, 0.0D, 8.0D, 16.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_SL = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 3.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(VoxelShapes.or(SOUTH_FR, SOUTH_FL), VoxelShapes.or(SOUTH_SR, SOUTH_SL));

    public ArrowSlit(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));

    }

//    @Override
//    public boolean isFullCube(BlockState state) {
//        return false;
//    }

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

//    @Override
//    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face) {
//        return BlockFaceShape.UNDEFINED;
//    }

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
