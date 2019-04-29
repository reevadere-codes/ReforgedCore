package com.conquestreforged.core.block;

import net.minecraft.block.BlockRail;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class Rail extends BlockRail {

    public Rail(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(IBlockState state, IWorldReaderBase reader, BlockPos pos) {
        return true;
    }
}
