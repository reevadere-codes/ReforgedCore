package com.conquestreforged.core.block;

import net.minecraft.block.GlassBlock;
import net.minecraft.util.BlockRenderLayer;

public class GlassTranslucent extends GlassBlock {

    public GlassTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
