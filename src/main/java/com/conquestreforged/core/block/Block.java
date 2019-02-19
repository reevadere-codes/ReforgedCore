package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Assets;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;

@Assets(
        state = @State(name = "%s", template = "acacia_planks", plural = true),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/acacia_planks"),
        block = @Model(name = "block/%s", template = "block/acacia_planks")
)
public class Block extends net.minecraft.block.Block {
    public Block(Properties properties) {
        super(properties);
    }
}