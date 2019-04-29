package com.conquestreforged.core.block.enumtypes;

import net.minecraft.util.IStringSerializable;

public enum ArchShapes implements IStringSerializable {
    ONE("one"),
    TWO("two"),
    THREE("three"),
    THREE_MIDDLE("three_middle");

    private final String name;

    ArchShapes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }
}
