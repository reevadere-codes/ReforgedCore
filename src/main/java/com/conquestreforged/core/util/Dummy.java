package com.conquestreforged.core.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class Dummy {

    private static final Supplier<Object> empty = () -> null;
    private static final Supplier<Item> item = () -> null;

    @SuppressWarnings("unchecked")
    public static <T> T dummy() {
        return (T) empty.get();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> T block() {
        return (T) empty.get();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> T item() {
        return (T) empty.get();
    }
}
