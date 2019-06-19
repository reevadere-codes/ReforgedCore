package com.conquestreforged.core.block;

import net.minecraft.block.PaneBlock;
import net.minecraft.util.BlockRenderLayer;

public class PaneTranslucent extends PaneBlock {

    public PaneTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
