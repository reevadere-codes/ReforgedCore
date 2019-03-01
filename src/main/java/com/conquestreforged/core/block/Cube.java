package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;

@Assets(
        state = @State(name = "%s", template = "acacia_planks", plural = true),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/acacia_planks"),
        block = @Model(name = "block/%s", template = "block/cube", plural = true)
)
public class Cube extends net.minecraft.block.Block {
    public Cube(Properties properties) {
        super(properties);
    }
}