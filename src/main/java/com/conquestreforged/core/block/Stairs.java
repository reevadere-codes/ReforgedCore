package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

@Name("%s_stairs")
@State("acacia_stairs")
@Model(model = "block/acacia_stairs", name = "block/%s_stairs")
@Model(model = "block/acacia_stairs_outer", name = "block/%s_stairs_outer")
@Model(model = "block/acacia_stairs_inner", name = "block/%s_stairs_inner")
public class Stairs extends BlockStairs {
    public Stairs(IBlockState parent, Properties properties) {
        super(parent, properties);
    }
}