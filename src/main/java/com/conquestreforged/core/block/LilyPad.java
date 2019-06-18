package com.conquestreforged.core.block;

import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;

public class LilyPad extends BlockLilyPad {

    public LilyPad(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }
}
