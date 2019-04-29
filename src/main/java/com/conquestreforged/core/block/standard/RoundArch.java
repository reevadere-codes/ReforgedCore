package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.enumtypes.ArchShapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_round_arch", template = "parent_round_arch"),
        item = @Model(name = "item/%s_round_arch", parent = "block/%s_round_arch_one", template = "item/parent_round_arch"),
        block = {
                @Model(name = "block/%s_round_arch_one", template = "block/parent_round_arch_one"),
                @Model(name = "block/%s_round_arch_two", template = "block/parent_round_arch_two"),
                @Model(name = "block/%s_round_arch_three", template = "block/parent_round_arch_three"),
                @Model(name = "block/%s_round_arch_three_top", template = "block/parent_round_arch_three_top"),
        }
)
public class RoundArch extends BlockHorizontal implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty FORM = EnumProperty.create("shape", ArchShapes.class);

    private static final VoxelShape ARCH_NORTH_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D));
    private static final VoxelShape ARCH_WEST_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_EAST_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_SOUTH_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_MIDDLE_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public RoundArch(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(FORM, ArchShapes.ONE).with(HORIZONTAL_FACING, EnumFacing.NORTH).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(HORIZONTAL_FACING, FORM, WATERLOGGED);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {

        if (state.get(FORM) == ArchShapes.ONE) {
            return VoxelShapes.fullCube();
        } else if ((state.get(FORM) == ArchShapes.TWO) || (state.get(FORM) == ArchShapes.THREE))  {
            switch (state.get(HORIZONTAL_FACING)) {
                case NORTH:
                default:
                    return ARCH_NORTH_SHAPE;
                case SOUTH:
                    return ARCH_EAST_SHAPE;
                case WEST:
                    return ARCH_SOUTH_SHAPE;
                case EAST:
                    return ARCH_WEST_SHAPE;
            }
        } else {
            return ARCH_MIDDLE_SHAPE;
        }
    }

    @Override
    public VoxelShape getRaytraceShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }


    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());

        BlockPos pos = context.getPos();
        IBlockReader iblockreader = context.getWorld();
        IBlockState north = iblockreader.getBlockState(pos.north());
        IBlockState east = iblockreader.getBlockState(pos.east());
        IBlockState south = iblockreader.getBlockState(pos.south());
        IBlockState west = iblockreader.getBlockState(pos.west());

        int counter = 0;
        boolean isThirdShape = false;
        EnumFacing facing = context.getPlacementHorizontalFacing().getOpposite();

        if (attachesTo(north)) {
            counter += 1;
            facing = EnumFacing.NORTH;
            if (attachesTo(iblockreader.getBlockState(pos.north(2)))) {
                isThirdShape = true;
            }
        }
        if (attachesTo(east)) {
            counter += 1;
            facing = EnumFacing.EAST;
            if (attachesTo(iblockreader.getBlockState(pos.east(2)))) {
                isThirdShape = true;
            }
        }
        if (attachesTo(south)) {
            counter += 1;
            facing = EnumFacing.SOUTH;
            if (attachesTo(iblockreader.getBlockState(pos.south(2)))) {
                isThirdShape = true;
            }
        }
        if (attachesTo(west)) {
            counter += 1;
            facing = EnumFacing.WEST;
            if (attachesTo(iblockreader.getBlockState(pos.west(2)))) {
                isThirdShape = true;
            }
        }

        if (counter == 0) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShapes.ONE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1 && isThirdShape) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShapes.THREE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShapes.TWO)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShapes.THREE_MIDDLE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        }

    }

    private boolean attachesTo(IBlockState state) {
        Block block = state.getBlock();
        return block instanceof RoundArch;
    }

    @Override
    public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (attachesTo(worldIn.getWorld().getBlockState(facingPos))) {
            IBlockReader iblockreader = worldIn.getWorld();
            IBlockState north = iblockreader.getBlockState(currentPos.north());
            IBlockState east = iblockreader.getBlockState(currentPos.east());
            IBlockState south = iblockreader.getBlockState(currentPos.south());
            IBlockState west = iblockreader.getBlockState(currentPos.west());

            int counter = 0;
            boolean isThirdShape = false;

            if (attachesTo(north)) {
                counter += 1;
                facing = EnumFacing.NORTH;
                if (attachesTo(iblockreader.getBlockState(currentPos.north(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(east)) {
                counter += 1;
                facing = EnumFacing.EAST;
                if (attachesTo(iblockreader.getBlockState(currentPos.east(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(south)) {
                counter += 1;
                facing = EnumFacing.SOUTH;
                if (attachesTo(iblockreader.getBlockState(currentPos.south(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(west)) {
                counter += 1;
                facing = EnumFacing.WEST;
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