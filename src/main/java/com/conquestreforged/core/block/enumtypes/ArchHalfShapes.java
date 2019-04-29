package com.conquestreforged.core.block.enumtypes;

import net.minecraft.util.IStringSerializable;

public enum ArchHalfShapes implements IStringSerializable {
    ONE("one"),
    TWO_L("two_l"),
    TWO_R("two_r"),
    THREE_L("three_l"),
    THREE_R("three_r"),
    THREE_MIDDLE("three_middle");

    private final String name;

    ArchHalfShapes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }
}
