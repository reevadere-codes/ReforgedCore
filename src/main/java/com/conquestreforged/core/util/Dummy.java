package com.conquestreforged.core.util;

import com.conquestreforged.core.block.Block;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class Dummy {

    private static final Supplier<Block> block = () -> null;
    private static final Supplier<Item> item = () -> null;

    public static Block block() {
        return block.get();
    }

    public static Item item() {
        return item.get();
    }
}
