package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.ItemModel;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockFence;

@Name("%s_fence")
@State("acacia_fence")
@ItemModel("block/%s_fence_inventory")
@Model(template = "block/acacia_fence_post", value = "block/%s_fence_post")
@Model(template = "block/acacia_fence_side", value = "block/%s_fence_side")
@Model(template = "block/acacia_fence_inventory", value = "block/%s_fence_inventory")
public class Fence extends BlockFence {
    public Fence(Properties properties) {
        super(properties);
    }
}
