package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.GlassBlock;

@Assets(
        state = @State(name = "%s", template = "parent_cube", plural = true),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/parent_cube", plural = true),
        block = @Model(name = "block/%s", template = "block/parent_cube", plural = true)
)
public class Glass extends GlassBlock {

    public Glass(Properties properties) {
        super(properties);
    }
}
