package com.conquestreforged.core.block;

import net.minecraft.block.BlockPane;
import net.minecraft.util.BlockRenderLayer;

public class PaneTranslucent extends BlockPane {

    public PaneTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
