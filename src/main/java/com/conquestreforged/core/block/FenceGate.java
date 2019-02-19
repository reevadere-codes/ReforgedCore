package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.ItemModel;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockFenceGate;

@Name("%s_fence_gate")
@State("acacia_fence_gate")
@ItemModel("block/%s_fence_gate")
@Model(template = "block/acacia_fence_gate", value = "block/%s_fence_gate")
@Model(template = "block/acacia_fence_gate_open", value = "block/%s_fence_gate_open")
@Model(template = "block/acacia_fence_gate_wall", value = "block/%s_fence_gate_wall")
@Model(template = "block/acacia_fence_gate_wall_open", value = "block/%s_fence_gate_wall_open")
public class FenceGate extends BlockFenceGate {
    public FenceGate(Properties properties) {
        super(properties);
    }
}
