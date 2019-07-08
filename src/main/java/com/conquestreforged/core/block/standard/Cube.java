package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;

@Assets(
        state = @State(name = "%s", template = "parent_cube"),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/parent_cube"),
        block = @Model(name = "block/%s", template = "block/parent_cube")
)
public class Cube extends Block {
    public Cube(Properties properties) {
        super(properties);
    }
}