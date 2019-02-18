package com.conquestreforged.core.block.factory;

import net.minecraft.block.Block;

import java.util.Iterator;

public class TypeList implements Iterable<Class<? extends Block>> {

    private final Class<? extends Block>[] types;

    private TypeList(Class<? extends Block>[] types) {
        this.types = types;
    }

    @SafeVarargs
    public static TypeList of(Class<? extends Block>... types) {
        return new TypeList(types);
    }

    @Override
    public Iterator<Class<? extends Block>> iterator() {
        return new Iterator<Class<? extends Block>>() {

            private int pos = 0;

            @Override
            public boolean hasNext() {
                return pos < types.length;
            }

            @Override
            public Class<? extends Block> next() {
                return types[pos++];
            }
        };
    }
}
