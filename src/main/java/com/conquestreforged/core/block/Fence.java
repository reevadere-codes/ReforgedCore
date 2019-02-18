package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockFence;

@Name("%s_fence")
@State("acacia_fence")
@Model(model = "block/acacia_fence_post", name = "block/%s_fence_post")
@Model(model = "block/acacia_fence_side", name = "block/%s_fence_side")
public class Fence extends BlockFence {
    public Fence(Properties properties) {
        super(properties);
    }
}
