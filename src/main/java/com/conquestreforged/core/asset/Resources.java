package com.conquestreforged.core.asset;

import com.conquestreforged.core.block.factory.InitializationException;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ResourcePackType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Resources {

    private static final Map<ResourcePackType, ResourceProvider> providers = new ConcurrentHashMap<>();

    public static void register(ResourcePackType side, ResourceProvider provider) {
        providers.put(side, provider);
    }

    public static ResourcePackList getResourcePackList(ResourcePackType type) {
        return providers.getOrDefault(type, DEFAULT).getPackList();
    }

    public static IResourceManager getResourceManager(ResourcePackType type) {
        return providers.getOrDefault(type, DEFAULT).getResourceManager();
    }

    private static final ResourceProvider DEFAULT = new ResourceProvider() {
        @Override
        public ResourcePackList getPackList() {
            throw new InitializationException("resource provider doesn't exist");
        }

        @Override
        public IResourceManager getResourceManager() {
            throw new InitializationException("resource provider doesn't exist");
        }
    };
}
