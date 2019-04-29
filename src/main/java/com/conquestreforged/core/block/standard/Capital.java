package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import com.conquestreforged.core.block.enumtypes.CapitalDirection;
import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_capital", template = "parent_capital"),
        item = @Model(name = "item/%s_capital", parent = "block/%s_capital_down_flat", template = "item/parent_capital"),
        block = {
                @Model(name = "block/%s_capital_down_flat", template = "block/parent_capital_down_flat"),
                @Model(name = "block/%s_capital_down_side", template = "block/parent_capital_down_side"),
                @Model(name = "block/%s_capital_up_flat", template = "block/parent_capital_up_flat"),
                @Model(name = "block/%s_capital_up_side", template = "block/parent_capital_up_side"),
        }
)
public class Capital extends Block implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty FACING = EnumProperty.create("facing", CapitalDirection.class);
    public static final EnumProperty TYPE = EnumProperty.create("type", Half.class);

    private static final VoxelShape TOP_FLAT_BIG = Block.makeCuboidShape(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_FLAT_MIDDLE = Block.makeCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape TOP_FLAT_SMALL = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    private static final VoxelShape TOP_FLAT = VoxelShapes.or(VoxelShapes.or(TOP_FLAT_BIG, TOP_FLAT_MIDDLE), TOP_FLAT_SMALL);

    private static final VoxelShape BOTTOM_FLAT_BIG = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    private static final VoxelShape BOTTOM_FLAT_MIDDLE = Block.makeCuboidShape(3.0D, 6.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    private static final VoxelShape BOTTOM_FLAT_SMALL = Block.makeCuboidShape(5.5D, 12.0D, 5.5D, 10.5D, 16.0D, 10.5D);
    private static final VoxelShape BOTTOM_FLAT = VoxelShapes.or(VoxelShapes.or(BOTTOM_FLAT_BIG, BOTTOM_FLAT_MIDDLE), BOTTOM_FLAT_SMALL);

    private static final VoxelShape BOTTOM_SIDE_BIG = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    private static final VoxelShape BOTTOM_SIDE_MIDDLE_N = Block.makeCuboidShape(3.0D, 6.0D, 0.0D, 13.0D, 12.0D, 13.0D);
    private static final VoxelShape BOTTOM_SIDE_MIDDLE_S = Block.makeCuboidShape(3.0D, 6.0D, 3.0D, 13.0D, 12.0D, 16.0D);
    private static final VoxelShape BOTTOM_SIDE_MIDDLE_E = Block.makeCuboidShape(3.0D, 6.0D, 3.0D, 16.0D, 12.0D, 13.0D);
    private static final VoxelShape BOTTOM_SIDE_MIDDLE_W = Block.makeCuboidShape(0.0D, 6.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    private static final VoxelShape BOTTOM_SIDE_N = VoxelShapes.or(BOTTOM_SIDE_BIG, BOTTOM_SIDE_MIDDLE_N);
    private static final VoxelShape BOTTOM_SIDE_S = VoxelShapes.or(BOTTOM_SIDE_BIG, BOTTOM_SIDE_MIDDLE_S);
    private static final VoxelShape BOTTOM_SIDE_E = VoxelShapes.or(BOTTOM_SIDE_BIG, BOTTOM_SIDE_MIDDLE_E);
    private static final VoxelShape BOTTOM_SIDE_W = VoxelShapes.or(BOTTOM_SIDE_BIG, BOTTOM_SIDE_MIDDLE_W);

    private static final VoxelShape TOP_SIDE_BIG = Block.makeCuboidShape(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_SIDE_MIDDLE_N = Block.makeCuboidShape(16.0D, 4.0D, 0.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape TOP_SIDE_MIDDLE_S = Block.makeCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 16.0D);
    private static final VoxelShape TOP_SIDE_MIDDLE_E = Block.makeCuboidShape(4.0D, 4.0D, 4.0D, 16.0D, 10.0D, 12.0D);
    private static final VoxelShape TOP_SIDE_MIDDLE_W = Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape TOP_SIDE_N = VoxelShapes.or(TOP_SIDE_BIG, TOP_SIDE_MIDDLE_N);
    private static final VoxelShape TOP_SIDE_S = VoxelShapes.or(TOP_SIDE_BIG, TOP_SIDE_MIDDLE_S);
    private static final VoxelShape TOP_SIDE_E = VoxelShapes.or(TOP_SIDE_BIG, TOP_SIDE_MIDDLE_E);
    private static final VoxelShape TOP_SIDE_W = VoxelShapes.or(TOP_SIDE_BIG, TOP_SIDE_MIDDLE_W);

    public Capital(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(TYPE, Half.TOP).with(FACING, CapitalDirection.NORTH).with(WATERLOGGED, false));

    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        if (state.get(TYPE) == Half.TOP) {
            if (state.get(FACING) == CapitalDirection.NORTH) {
                return TOP_SIDE_N;
            } else if (state.get(FACING) == CapitalDirection.SOUTH) {
                return TOP_SIDE_S;
            } else if (state.get(FACING) == CapitalDirection.EAST) {
                return TOP_SIDE_E;
            } else if (state.get(FACING) == CapitalDirection.WEST) {
                return TOP_SIDE_W;
            } else {
                return TOP_FLAT;
            }
        } else {
            if (state.get(FACING) == CapitalDirection.NORTH) {
                return BOTTOM_SIDE_N;
            } else if (state.get(FACING) == CapitalDirection.SOUTH) {
                return BOTTOM_SIDE_S;
            } else if (state.get(FACING) == CapitalDirection.EAST) {
                return BOTTOM_SIDE_E;
            } else if (state.get(FACING) == CapitalDirection.WEST) {
                return BOTTOM_SIDE_W;
            } else {
                return BOTTOM_FLAT;
            }
        }
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        if (state.get(TYPE) == Half.TOP) {
            if (state.get(FACING) == CapitalDirection.NORTH) {
                return TOP_SIDE_N;
            } else if (state.get(FACING) == CapitalDirection.SOUTH) {
                return TOP_SIDE_S;
            } else if (state.get(FACING) == CapitalDirection.EAST) {
                return TOP_SIDE_E;
            } else if (state.get(FACING) == CapitalDirection.WEST) {
                return TOP_SIDE_W;
            } else {
                return TOP_FLAT;
            }
        } else {
            if (state.get(FACING) == CapitalDirection.NORTH) {
                return BOTTOM_SIDE_N;
            } else if (state.get(FACING) == CapitalDirection.SOUTH) {
                return BOTTOM_SIDE_S;
            } else if (state.get(FACING) == CapitalDirection.EAST) {
                return BOTTOM_SIDE_E;
            } else if (state.get(FACING) == CapitalDirection.WEST) {
                return BOTTOM_SIDE_W;
            } else {
                return BOTTOM_FLAT;
            }
        }
    }

    @Override
    public VoxelShape getRaytraceShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        if (state.get(TYPE) == Half.TOP) {
            if (state.get(FACING) == CapitalDirection.NORTH) {
                return TOP_SIDE_N;
            } else if (state.get(FACING) == CapitalDirection.SOUTH) {
                return TOP_SIDE_S;
            } else if (state.get(FACING) == CapitalDirection.EAST) {
                return TOP_SIDE_E;
            } else if (state.get(FACING) == CapitalDirection.WEST) {
                return TOP_SIDE_W;
            } else {
                return TOP_FLAT;
            }
        } else {
            if (state.get(FACING) == CapitalDirection.NORTH) {
                return BOTTOM_SIDE_N;
            } else if (state.get(FACING) == CapitalDirection.SOUTH) {
                return BOTTOM_SIDE_S;
            } else if (state.get(FACING) == CapitalDirection.EAST) {
                return BOTTOM_SIDE_E;
            } else if (state.get(FACING) == CapitalDirection.WEST) {
                return BOTTOM_SIDE_W;
            } else {
                return BOTTOM_FLAT;
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        EnumFacing facing = context.getFace();

        CapitalDirection horizontalFacing;
        switch (facing) {
            default:
                horizontalFacing = CapitalDirection.FLAT;
                break;
            case NORTH:
                horizontalFacing = CapitalDirection.SOUTH;
                break;
            case SOUTH:
                horizontalFacing = CapitalDirection.NORTH;
                break;
            case WEST:
                horizontalFacing = CapitalDirection.EAST;
                break;
            case EAST:
                horizontalFacing = CapitalDirection.WEST;
                break;
        }

        Half verticalFacing;
        if (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)context.getHitY() <= 0.5D)) {
            verticalFacing = Half.BOTTOM;
        } else {
            verticalFacing = Half.TOP;
        }

        return super.getStateForPlacement(context)
                .with(TYPE, verticalFacing)
                .with(FACING, horizontalFacing)
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, IBlockState state) {
        if (state.get(WATERLOGGED)) {
            worldIn.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    public IFluidState getFluidState(IBlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, IBlockState state, Fluid fluidIn) {
        return !state.get(WATERLOGGED) && fluidIn == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, IBlockState state, IFluidState fluidStateIn) {
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
