package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockSlab;

@Name("%s_slab")
@State("acacia_slab")
@Model(model = "block/acacia_slab", name = "conquest:block/%s_slab")
@Model(model = "block/acacia_slab_top", name = "conquest:block/%s_slab_top")
@Model(model = "block/acacia_planks", name = "conquest:block/%s", plural = true)
public class Slab extends BlockSlab {

    public Slab(Properties properties) {
        super(properties);
    }
}