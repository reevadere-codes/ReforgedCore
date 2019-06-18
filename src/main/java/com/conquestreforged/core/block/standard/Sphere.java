package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.types.CapitalDirection;
import net.minecraft.block.Block;
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
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_sphere", template = "parent_sphere"),
        item = @Model(name = "item/%s_sphere", parent = "block/%s_sphere", template = "item/parent_sphere"),
        block = {
                @Model(name = "block/%s_sphere", template = "block/parent_sphere"),
                @Model(name = "block/%s_sphere_dragonegg", template = "block/parent_sphere"),
                @Model(name = "block/%s_sphere_small", template = "block/parent_sphere"),
        }
)
public class Sphere extends Block implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty FACING = EnumProperty.create("facing", CapitalDirection.class);
    public static final EnumProperty TYPE = EnumProperty.create("type", Half.class);

    public Sphere(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(TYPE, Half.TOP).with(FACING, CapitalDirection.NORTH).with(WATERLOGGED, false));

    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, BlockState state, BlockPos pos, Direction face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getFace();

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
        if (facing != Direction.DOWN && (facing == Direction.UP || (double)context.getHitY() <= 0.5D)) {
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
