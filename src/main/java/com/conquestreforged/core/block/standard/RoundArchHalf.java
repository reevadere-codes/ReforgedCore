package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.types.ArchHalfShapes;
import com.conquestreforged.core.block.types.ArchShapes;
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
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_round_arch_half", template = "parent_round_arch_half"),
        item = @Model(name = "item/%s_round_arch_half", parent = "block/%s_round_arch_one_half", template = "item/parent_round_arch_half"),
        block = {
                @Model(name = "block/%s_round_arch_one_half", template = "block/parent_round_arch_one_half"),
                @Model(name = "block/%s_round_arch_half_two", template = "block/parent_round_arch_half_two"),
                @Model(name = "block/%s_round_arch_half_three", template = "block/parent_round_arch_half_three"),
                @Model(name = "block/%s_round_arch_half_three_top", template = "block/parent_round_arch_half_three_top"),
        }
)
public class RoundArchHalf extends BlockHorizontal implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty FORM = EnumProperty.create("shape", ArchHalfShapes.class);

    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 8.0D);

    private static final VoxelShape ARCH_NORTH_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D));
    private static final VoxelShape ARCH_NORTH_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_WEST_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D));
    private static final VoxelShape ARCH_WEST_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_EAST_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D));
    private static final VoxelShape ARCH_EAST_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D));

    private static final VoxelShape ARCH_SOUTH_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D));
    private static final VoxelShape ARCH_SOUTH_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_MIDDLE_SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape ARCH_MIDDLE_NORTH_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape ARCH_MIDDLE_WEST_SHAPE = Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape ARCH_MIDDLE_EAST_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D);

    public RoundArchHalf(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(FORM, ArchHalfShapes.ONE).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FORM, WATERLOGGED);
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos) {

        if (state.get(FORM) == ArchHalfShapes.ONE) {
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
        } else if (state.get(FORM) == ArchHalfShapes.TWO_L || state.get(FORM) == ArchHalfShapes.THREE_L) {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return ARCH_NORTH_L_SHAPE;
                case SOUTH:
                    return ARCH_SOUTH_L_SHAPE;
                case WEST:
                    return ARCH_WEST_L_SHAPE;
                case EAST:
                    return ARCH_EAST_L_SHAPE;
            }
        } else if (state.get(FORM) == ArchHalfShapes.TWO_R || state.get(FORM) == ArchHalfShapes.THREE_R) {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return ARCH_NORTH_R_SHAPE;
                case SOUTH:
                    return ARCH_SOUTH_R_SHAPE;
                case WEST:
                    return ARCH_WEST_R_SHAPE;
                case EAST:
                    return ARCH_EAST_R_SHAPE;
            }
        } else {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return ARCH_MIDDLE_NORTH_SHAPE;
                case SOUTH:
                    return ARCH_MIDDLE_SOUTH_SHAPE;
                case WEST:
                    return ARCH_MIDDLE_WEST_SHAPE;
                case EAST:
                    return ARCH_MIDDLE_EAST_SHAPE;
            }
        }
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());

        BlockPos pos = context.getPos();
        IBlockReader iblockreader = context.getWorld();
        BlockState north = iblockreader.getBlockState(pos.north());
        BlockState east = iblockreader.getBlockState(pos.east());
        BlockState south = iblockreader.getBlockState(pos.south());
        BlockState west = iblockreader.getBlockState(pos.west());

        int counter = 0;
        boolean isThirdShape = false;
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();
        ArchHalfShapes shape = ArchHalfShapes.ONE;

        if (attachesTo(north)) {
            counter += 1;
            shape = ArchHalfShapes.TWO_L;
            if (attachesTo(iblockreader.getBlockState(pos.north(2)))) {
                isThirdShape = true;
                shape = ArchHalfShapes.THREE_L;
            }
        }
        if (attachesTo(east)) {
            counter += 1;
            facing = Direction.EAST;
            shape = ArchHalfShapes.TWO_L;
            if (attachesTo(iblockreader.getBlockState(pos.east(2)))) {
                isThirdShape = true;
                shape = ArchHalfShapes.THREE_L;
            }
        }
        if (attachesTo(south)) {
            counter += 1;
            shape = ArchHalfShapes.TWO_R;
            if (attachesTo(iblockreader.getBlockState(pos.south(2)))) {
                isThirdShape = true;
                shape = ArchHalfShapes.THREE_R;
            }
        }
        if (attachesTo(west)) {
            counter += 1;
            shape = ArchHalfShapes.TWO_R;
            if (attachesTo(iblockreader.getBlockState(pos.west(2)))) {
                isThirdShape = true;
                shape = ArchHalfShapes.THREE_R;
            }
        }

        if (counter == 0) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, shape)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1 && isThirdShape) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, shape)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, shape)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShapes.THREE_MIDDLE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        }

    }

    private boolean attachesTo(BlockState state) {
        Block block = state.getBlock();
        return block instanceof RoundArchHalf;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (attachesTo(worldIn.getWorld().getBlockState(facingPos))) {
            IBlockReader iblockreader = worldIn.getWorld();
            BlockState north = iblockreader.getBlockState(currentPos.north());
            BlockState east = iblockreader.getBlockState(currentPos.east());
            BlockState south = iblockreader.getBlockState(currentPos.south());
            BlockState west = iblockreader.getBlockState(currentPos.west());

            int counter = 0;
            boolean isThirdShape = false;

            if (attachesTo(north)) {
                counter += 1;
                facing = Direction.NORTH;
                if (attachesTo(iblockreader.getBlockState(currentPos.north(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(east)) {
                counter += 1;
                facing = Direction.EAST;
                if (attachesTo(iblockreader.getBlockState(currentPos.east(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(south)) {
                counter += 1;
                facing = Direction.SOUTH;
                if (attachesTo(iblockreader.getBlockState(currentPos.south(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(west)) {
                counter += 1;
                facing = Direction.WEST;
                if (attachesTo(iblockreader.getBlockState(currentPos.west(2)))) {
                    isThirdShape = true;
                }
            }

            if (counter == 0) {
                return this.getDefaultState()
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ArchShapes.ONE);
            } else if (counter == 1 && isThirdShape) {
                return this.getDefaultState()
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ArchShapes.THREE);
            } else if (counter == 1) {
                return this.getDefaultState()
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ArchShapes.TWO);
            } else if (counter >= 2) {
                return this.getDefaultState()
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ArchShapes.THREE_MIDDLE);
            } else {
                return stateIn;
            }
        }
        return stateIn;
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