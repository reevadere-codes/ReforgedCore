package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_wall", template = "parent_wall"),
        item = @Model(name = "item/%s_wall", parent = "block/%s_wall_inventory", template = "item/cobblestone_wall"),
        block = {
                @Model(name = "block/%s_wall_post", template = "block/cobblestone_wall_post"),
                @Model(name = "block/%s_wall_side", template = "block/parent_wall_side"),
                @Model(name = "block/%s_wall_inventory", template = "block/cobblestone_wall_inventory"),
        },
        recipe = @Recipe(
                name = "%s_wall",
                template = "cobblestone_wall",
                ingredients = {
                        @Ingredient(name = "%s", template = "cobblestone", plural = true)
                }
        )
)
public class Wall extends WallBlock {

    public Wall(Properties properties) {
        super(properties);
    }

}
