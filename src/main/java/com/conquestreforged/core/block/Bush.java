package com.conquestreforged.core.block;

import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import net.minecraft.block.BlockBush;

@Name("%s_bush")
@State("/template/blockstate/block.json")
public class Bush extends BlockBush {
    protected Bush(Properties properties) {
        super(properties);
    }
}
