package com.conquestreforged.core.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.BlockState;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class Door extends BlockDoor {

    public Door(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReaderBase reader, BlockPos pos) {
        BlockState stateDown = reader.getBlockState(pos.down());
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return true;
        } else {
            return stateDown.getBlock() == this;
        }
    }
}
