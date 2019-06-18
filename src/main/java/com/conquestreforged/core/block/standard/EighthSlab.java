package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_eighthslab", template = "parent_eighthslab"),
        item = @Model(name = "item/%s_eighthslab", parent = "block/%s_eighthslab", template = "item/parent_eighthslab"),
        block = {
                @Model(name = "block/%s_eighthslab", template = "block/parent_eighthslab"),
        },
        recipe = @Recipe(
                name = "%s_slab",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class EighthSlab extends HorizontalBlock implements IBucketPickupHandler, ILiquidContainer {

    private static final VoxelShape BOTTOM_QTR_EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
    private static final VoxelShape BOTTOM_QTR_WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
    private static final VoxelShape BOTTOM_QTR_SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
    private static final VoxelShape BOTTOM_QTR_NORTH_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);

    private static final VoxelShape TOP_QTR_EAST_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_QTR_WEST_SHAPE = Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape TOP_QTR_SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
    private static final VoxelShape TOP_QTR_NORTH_SHAPE = Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);


    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty TYPE_UPDOWN = EnumProperty.create("type", Half.class);

    public EighthSlab(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
        container.add(HORIZONTAL_FACING, TYPE_UPDOWN, WATERLOGGED);
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
        if (state.get(TYPE_UPDOWN) == Half.BOTTOM) {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return BOTTOM_QTR_NORTH_SHAPE;
                case SOUTH:
                    return BOTTOM_QTR_SOUTH_SHAPE;
                case WEST:
                    return BOTTOM_QTR_WEST_SHAPE;
                case EAST:
                    return BOTTOM_QTR_EAST_SHAPE;
            }
        } else {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return TOP_QTR_NORTH_SHAPE;
                case SOUTH:
                    return TOP_QTR_SOUTH_SHAPE;
                case WEST:
                    return TOP_QTR_WEST_SHAPE;
                case EAST:
                    return TOP_QTR_EAST_SHAPE;
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState fluid = context.getWorld().getFluidState(context.getPos());
        Direction facingHorizontal = context.getPlacementHorizontalFacing().getOpposite();
        BlockState state2 = this.getDefaultState().with(HORIZONTAL_FACING, facingHorizontal).with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
        Direction facing = context.getFace();
        return facing != Direction.DOWN && (facing == Direction.UP || context.func_221532_j().y <= 0.5D) ? state2 : state2.with(TYPE_UPDOWN, Half.TOP);
    }

//    @Override
//    public boolean isFullCube(BlockState state) {
//        return false;
//    }

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

    @Override
    public Fluid pickupFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.get(WATERLOGGED)) {
            world.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}