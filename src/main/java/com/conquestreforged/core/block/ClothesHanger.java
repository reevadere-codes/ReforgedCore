package com.conquestreforged.core.block;

import com.conquestreforged.core.block.standard.VerticalSlab;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClothesHanger extends VerticalSlab {

    public static final IntegerProperty ACTIVATED = IntegerProperty.create("activated", 1, 4);

    //might make more voxelshapes later on...

    public ClothesHanger(Block.Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(HORIZONTAL_FACING, facing)
                .with(ACTIVATED, 0);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        if (!player.abilities.allowEdit) {
            return false;
        } else {
            worldIn.setBlockState(pos, state.cycle(ACTIVATED), 3);
            return true;
        }
    }
}
