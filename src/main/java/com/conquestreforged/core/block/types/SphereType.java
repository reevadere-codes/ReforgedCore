package com.conquestreforged.core.block.types;

import net.minecraft.util.IStringSerializable;

public enum SphereType implements IStringSerializable {
    EGG("egg"),
    SMALL("small"),
    LARGE("large");

    private final String name;

    SphereType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }
}
