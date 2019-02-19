package com.conquestreforged.core.block.data;

import com.conquestreforged.core.util.Cache;

public class AssetTemplateCache extends Cache<Class<?>, AssetTemplate> {

    private static final AssetTemplateCache instance = new AssetTemplateCache();

    private AssetTemplateCache() {

    }

    @Override
    public AssetTemplate compute(Class<?> type) {
        return new AssetTemplate(type);
    }

    public static AssetTemplateCache getInstance() {
        return instance;
    }
}
