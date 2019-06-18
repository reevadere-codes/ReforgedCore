package com.conquestreforged.core.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockRenderLayer;

import java.util.Random;

public class GlassTranslucent extends BlockGlass {

    public GlassTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public int quantityDropped(BlockState state, Random rand) {
        return 1;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
