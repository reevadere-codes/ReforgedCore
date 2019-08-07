package com.conquestreforged.core.block;

import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;


public class LightSource extends Block implements Waterloggable {

    public static final IntegerProperty LIGHT_0_3 = IntegerProperty.create("light", 0, 3);

    public LightSource(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIGHT_0_3, 0).with(WATERLOGGED, false));

    }

    @Deprecated
    public int getLightValue(BlockState state) {
        if (state.get(LIGHT_0_3) == 0) {
            return 0;
        } else if (state.get(LIGHT_0_3) == 1) {
            return 5;
        } else if (state.get(LIGHT_0_3) == 2) {
            return 10;
        } else {
            return 14;
        }
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_0_3, WATERLOGGED);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
