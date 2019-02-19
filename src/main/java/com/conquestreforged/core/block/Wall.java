package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Assets;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockWall;

@Assets(
        state = @State(name = "%s_wall", template = "cobblestone_wall"),
        item = @Model(name = "item/%s_wall", parent = "block/%s_wall_inventory", template = "item/cobblestone_wall"),
        block = {
                @Model(name = "block/%s_wall_post", template = "block/cobblestone_wall_post"),
                @Model(name = "block/%s_wall_side", template = "block/cobblestone_wall_side"),
                @Model(name = "block/%s_wall_inventory", template = "block/cobblestone_wall_inventory"),
        }
)
public class Wall extends BlockWall {
    public Wall(Properties properties) {
        super(properties);
    }
}
