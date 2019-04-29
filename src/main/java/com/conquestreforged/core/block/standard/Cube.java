package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;

@Assets(
        state = @State(name = "%s", template = "acacia_planks"),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/acacia_planks"),
        block = @Model(name = "block/%s", template = "block/acacia_planks")
)
public class Cube extends Block {
    public Cube(Properties properties) {
        super(properties);
    }
}