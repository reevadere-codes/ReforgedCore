package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.ItemModel;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockSlab;

@Name("%s_slab")
@State("acacia_slab")
@ItemModel("block/%s_slab")
@Model(template = "block/acacia_slab", value = "block/%s_slab")
@Model(template = "block/acacia_slab_top", value = "block/%s_slab_top")
@Model(template = "block/acacia_planks", value = "block/%s", plural = true)
public class Slab extends BlockSlab {

    public Slab(Properties properties) {
        super(properties);
    }
}