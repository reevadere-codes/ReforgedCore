package com.conquestreforged.core.block;

import com.conquestreforged.core.block.standard.VerticalSlab;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class RackHalberds extends VerticalSlab {

    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public RackHalberds(Block.Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState())
                .with(UP, false)
                .with(DOWN, false)
                .with(HORIZONTAL_FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, HORIZONTAL_FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();

        BlockState BlockStateUp = iblockreader.getBlockState(blockpos.up());
        BlockState BlockStateDown = iblockreader.getBlockState(blockpos.down());

        return super.getStateForPlacement(context)
                .with(UP, this.attachesTo(BlockStateUp))
                .with(DOWN, this.attachesTo(BlockStateDown))
                .with(HORIZONTAL_FACING, facing)
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
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof RackHalberds)));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof RackHalberds)));
    }
}
