package com.conquestreforged.core.block;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class Trapdoor extends BlockTrapDoor {

    public Trapdoor(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }

}
