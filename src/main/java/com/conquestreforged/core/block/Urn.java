package com.conquestreforged.core.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class Urn extends BlockHorizontal {

    public static final BooleanProperty CAP = BlockStateProperties.EYE;

    public Urn(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH).with(CAP,false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(HORIZONTAL_FACING, facing)
                .with(CAP, false);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            state = state.cycle(CAP);
            worldIn.setBlockState(pos, state, 3);
            return true;
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }
}
