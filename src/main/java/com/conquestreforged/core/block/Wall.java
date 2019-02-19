package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.ItemModel;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockWall;

@Name("%s_wall")
@State("cobblestone_wall")
@ItemModel("block/%s_wall_inventory")
@Model(template = "block/cobblestone_wall_post", value = "block/%s_wall_post")
@Model(template = "block/cobblestone_wall_side", value = "block/%s_wall_side")
@Model(template = "block/cobblestone_wall_inventory", value = "block/%s_wall_inventory")
public class Wall extends BlockWall {
    public Wall(Properties properties) {
        super(properties);
    }
}
