package com.conquestreforged.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Campfire extends Block {

    public static final BooleanProperty FIRE = BlockStateProperties.EYE;

    public Campfire(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(FIRE,false));
    }

    @Override
    public int getLightValue(BlockState state) {
        if (state.get(FIRE)) {
            return 15;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader reader, BlockPos pos) {
        return 0;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FIRE);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            state = state.cycle(FIRE);
            worldIn.setBlockState(pos, state, 3);
            float f = state.get(FIRE) ? 0.6F : 0.5F;
            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.3F, f);
            return true;
        }
    }

}
