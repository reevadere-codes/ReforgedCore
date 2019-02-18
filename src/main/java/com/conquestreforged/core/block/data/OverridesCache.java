package com.conquestreforged.core.block.data;

import com.conquestreforged.core.util.Cache;

public class OverridesCache extends Cache<Class<?>, Overrides> {

    private static final OverridesCache instance = new OverridesCache();

    private OverridesCache() {

    }

    @Override
    public Overrides compute(Class<?> type) {
        return new Overrides(type);
    }

    public static OverridesCache getInstance() {
        return instance;
    }
}
