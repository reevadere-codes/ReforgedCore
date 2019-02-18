package com.conquestreforged.core.util;

public class Context {

    private static ThreadLocal<Context> instance = ThreadLocal.withInitial(Context::new);

    private String namespace = "";

    public static Context getInstance() {
        return instance.get();
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public static String withNamespace(String name) {
        return withNamespace(Context.getInstance().getNamespace(), name);
    }

    public static String withNamespace(String namespace, String name) {
        if (name.indexOf(':') != -1) {
            return name;
        }
        return namespace + ':' + name;
    }
}
