package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

@Assets(
        state = @State(name = "%s_stairs", template = "acacia_stairs"),
        item = @Model(name = "item/%s_stairs", parent = "block/%s_stairs", template = "item/acacia_stairs"),
        block = {
                @Model(name = "block/%s_stairs", template = "block/acacia_stairs"),
                @Model(name = "block/%s_stairs_outer", template = "block/acacia_stairs_outer"),
                @Model(name = "block/%s_stairs_inner", template = "block/acacia_stairs_inner"),
        },
        recipe = @Recipe(
                name = "%s_stairs",
                template = "acacia_stairs",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class Stairs extends BlockStairs {
    public Stairs(IBlockState parent, Properties properties) {
        super(parent, properties);
    }
}