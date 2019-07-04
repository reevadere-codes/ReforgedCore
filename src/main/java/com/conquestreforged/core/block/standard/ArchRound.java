package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import com.conquestreforged.core.block.properties.ArchShape;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
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
public class ArchRound extends HorizontalBlock implements Waterloggable {

    public static final EnumProperty<ArchShape> FORM = EnumProperty.create("shape", ArchShape.class);

    private static final VoxelShape ARCH_NORTH_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D));
    private static final VoxelShape ARCH_WEST_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_EAST_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_SOUTH_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_MIDDLE_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public ArchRound(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(FORM, ArchShape.ONE).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
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
                        .with(FORM, ArchShape.ONE);
            } else if (counter == 1 && isThirdShape) {
                return this.getDefaultState()
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ArchShape.THREE);
            } else if (counter == 1) {
                return this.getDefaultState()
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ArchShape.TWO);
            } else if (counter >= 2) {
                return this.getDefaultState()
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ArchShape.THREE_MIDDLE);
            } else {
                return stateIn;
            }
        }
        return stateIn;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(FORM) == ArchShape.ONE) {
            return VoxelShapes.fullCube();
        } else if ((state.get(FORM) == ArchShape.TWO) || (state.get(FORM) == ArchShape.THREE)) {
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
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
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

        if (attachesTo(north)) {
            counter += 1;
            facing = Direction.NORTH;
            if (attachesTo(iblockreader.getBlockState(pos.north(2)))) {
                isThirdShape = true;
            }
        }
        if (attachesTo(east)) {
            counter += 1;
            facing = Direction.EAST;
            if (attachesTo(iblockreader.getBlockState(pos.east(2)))) {
                isThirdShape = true;
            }
        }
        if (attachesTo(south)) {
            counter += 1;
            facing = Direction.SOUTH;
            if (attachesTo(iblockreader.getBlockState(pos.south(2)))) {
                isThirdShape = true;
            }
        }
        if (attachesTo(west)) {
            counter += 1;
            facing = Direction.WEST;
            if (attachesTo(iblockreader.getBlockState(pos.west(2)))) {
                isThirdShape = true;
            }
        }

        if (counter == 0) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShape.ONE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1 && isThirdShape) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShape.THREE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShape.TWO)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ArchShape.THREE_MIDDLE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        }

    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FORM, WATERLOGGED);
    }
    private boolean attachesTo(BlockState state) {
        Block block = state.getBlock();
        return block instanceof ArchRound;
    }
}