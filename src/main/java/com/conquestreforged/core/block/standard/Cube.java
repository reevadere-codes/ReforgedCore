package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;

@Assets(
        state = @State(name = "%s", template = "quartz_block"),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/quartz_block"),
        block = @Model(name = "block/%s", template = "block/quartz_block")
)
public class Cube extends Block {
    public Cube(Properties properties) {
        super(properties);
    }
}