package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;

@State("acacia_planks")
@Model(model = "block/acacia_planks", name = "block/%s", plural = true)
public class Block extends net.minecraft.block.Block {
    public Block(Block.Properties properties) {
        super(properties);
    }
}