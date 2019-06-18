package com.conquestreforged.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Box extends BlockHorizontal {

    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public Box(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(OPEN,false).with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        state = state.cycle(OPEN);
        worldIn.setBlockState(pos, state, 2);
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(HORIZONTAL_FACING, facing)
                .with(OPEN, false);
    }
}
