package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.BlockSlab;

@Assets(
        state = @State(name = "%s_slab", template = "acacia_slab"),
        item = @Model(name = "item/%s_slab", parent = "block/%s_slab", template = "item/acacia_slab"),
        block = {
                @Model(name = "block/%s_slab", template = "block/acacia_slab"),
                @Model(name = "block/%s_slab_top", template = "block/acacia_slab_top"),
                @Model(name = "block/%s", template = "block/acacia_planks", plural = true),
        },
        recipe = @Recipe(
                name = "%s_slab",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class Slab extends BlockSlab {
    public Slab(Properties properties) {
        super(properties);
    }
}