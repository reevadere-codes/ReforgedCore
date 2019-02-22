package com.conquestreforged.core.util;

public interface Optional {

    default boolean isPresent() {
        return !isAbsent();
    }

    boolean isAbsent();
}
