package com.conquestreforged.core.util;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {

    private static final Map<ModContainer, Context> contexts = new ConcurrentHashMap<>();

    private final ModContainer container;
    private String namespace = "";

    public Context(ModContainer container) {
        this.container = container;
    }

    public static Context getInstance() {
        return getCurrentContext();
    }

    public synchronized String getNamespace() {
        return namespace.isEmpty() ? container.getModId() : namespace;
    }

    public synchronized void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    private static Context getCurrentContext() {
        ModContainer current = ModLoadingContext.get().getActiveContainer();
        return contexts.computeIfAbsent(current, Context::new);
    }

}
