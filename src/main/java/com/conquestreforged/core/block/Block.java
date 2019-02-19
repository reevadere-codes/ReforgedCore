package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.ItemModel;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;

@State("acacia_planks")
@ItemModel("block/%s")
@Model(template = "block/acacia_planks", value = "block/%s", plural = true)
public class Block extends net.minecraft.block.Block {
    public Block(Block.Properties properties) {
        super(properties);
    }
}