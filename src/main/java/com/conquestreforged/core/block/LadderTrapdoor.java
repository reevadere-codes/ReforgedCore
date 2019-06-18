package com.conquestreforged.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class LadderTrapdoor extends Trapdoor {

    public LadderTrapdoor(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader reader, BlockPos pos, EntityLivingBase entity) {
        if (state.get(OPEN)) {
            return true;
        }
        return false;
    }
}
