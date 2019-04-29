package com.conquestreforged.core.block;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class Vine extends BlockVine {

    public Vine(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(IBlockState p_196260_1_, IWorldReaderBase p_196260_2_, BlockPos p_196260_3_) {
        return true;
    }

    //gamerule to disable growth?
}
