package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Assets;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockFenceGate;

@Assets(
        state = @State(name = "%s_fence_gate", template = "acacia_fence_gate"),
        item = @Model(name = "item/%s_fence_gate", parent = "block/%s_fence_gate", template = "item/acacia_fence_gate"),
        block = {
                @Model(name = "block/%s_fence_gate", template = "block/acacia_fence_gate"),
                @Model(name = "block/%s_fence_gate_open", template = "block/acacia_fence_gate_open"),
                @Model(name = "block/%s_fence_gate_wall", template = "block/acacia_fence_gate_wall"),
                @Model(name = "block/%s_fence_gate_wall_open", template = "block/acacia_fence_gate_wall_open"),
        }
)
public class FenceGate extends BlockFenceGate {
    public FenceGate(Properties properties) {
        super(properties);
    }
}
