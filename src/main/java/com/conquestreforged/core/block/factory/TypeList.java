package com.conquestreforged.core.block.factory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;

import java.util.Comparator;
import java.util.Iterator;

public class TypeList implements Iterable<Class<? extends Block>>, Comparator<Block> {

    private final Class<? extends Block>[] types;

    private TypeList(Class<? extends Block>[] types) {
        this.types = types;
    }

    public boolean isEmpty() {
        return types.length == 0;
    }

    public Class<? extends Block> first() {
        if (types.length > 0) {
            return types[0];
        }
        return BlockAir.class;
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

    @Override
    public int compare(Block b1, Block b2) {
        return getIndex(b1) - getIndex(b2);
    }

    private int getIndex(Object o) {
        int max = -1;
        for (int i = 0; i < types.length; i++) {
            Class type = types[i];
            if (type.isInstance(o)) {
                max = Math.max(max, i);
            }
        }
        return max == -1 ? types.length : max;
    }
}
