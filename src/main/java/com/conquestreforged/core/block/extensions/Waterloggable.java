package com.conquestreforged.core.block.extensions;

import afu.org.checkerframework.checker.oigj.qual.O;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public interface Waterloggable extends ILiquidContainer, IBucketPickupHandler {

    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    default BlockState getWaterloggedState(BlockState state, BlockItemUseContext context) {
        IFluidState fluid = context.getWorld().getFluidState(context.getPos());
        return state.with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
    }

    @Override
    default boolean canContainFluid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluidIn) {
        return !state.get(WATERLOGGED) && fluidIn == Fluids.WATER;
    }

    @Override
    default boolean receiveFluid(IWorld world, BlockPos pos, BlockState state, IFluidState fluidState) {
        if (!state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!world.isRemote()) {
                world.setBlockState(pos, state.with(WATERLOGGED,true), 3);
                world.getPendingFluidTicks().scheduleTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    default Fluid pickupFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.get(WATERLOGGED)) {
            world.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    static IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }
}
