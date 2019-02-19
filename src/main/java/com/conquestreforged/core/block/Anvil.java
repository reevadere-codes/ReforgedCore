package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Assets;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockAnvil;

@Assets(
        state = @State(name = "%s_anvil", template = "anvil"),
        item = @Model(name = "item/%s_anvil", parent = "block/%s_anvil", template = "item/anvil"),
        block = @Model(name = "block/%s_anvil", template = "block/anvil")
)
public class Anvil extends BlockAnvil {
    public Anvil(Properties builder) {
        super(builder);
    }
}
