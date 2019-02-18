package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockFenceGate;

@Name("%s_fence_gate")
@State("acacia_fence_gate")
public class FenceGate extends BlockFenceGate {
    public FenceGate(Properties properties) {
        super(properties);
    }
}
