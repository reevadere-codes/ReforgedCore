package com.conquestreforged.core.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.state.BlockState;

import java.util.Random;

public class Glass extends BlockGlass {

    public Glass(Properties properties) {
        super(properties);
    }

    @Override
    public int quantityDropped(BlockState state, Random rand) {
        return 1;
    }
}
