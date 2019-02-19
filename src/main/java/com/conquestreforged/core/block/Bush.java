package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Assets;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockBush;

@Assets(
        state = @State(name = "%s_bush", template = "dead_bush"),
        item = @Model(name = "item/%s_bush", parent = "block/%s_bush", template = "item/dead_bush"),
        block = @Model(name = "block/%s_bush", template = "block/dead_bush")
)
public class Bush extends BlockBush {
    public Bush(Properties properties) {
        super(properties);
    }
}
