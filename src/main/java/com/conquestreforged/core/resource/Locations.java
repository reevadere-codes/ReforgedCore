package com.conquestreforged.core.resource;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class Locations {

    private Locations() {

    }

    public static ResourceLocation state(ResourceLocation name) {
        return new ResourceLocation(name.getNamespace(), "blockstates/" + name.getPath() + ".json");
    }

    public static ResourceLocation model(ResourceLocation name) {
        return new ResourceLocation(name.getNamespace(), "models/" + name.getPath() + ".json");
    }

    public static String statePath(ResourceLocation name) {
        return path("blockstates", name);
    }

    public static String modelPath(ModelResourceLocation name) {
        return path("models", name);
    }

    public static String path(ResourceLocation location) {
        if (location.getPath().endsWith(".json")) {
            return "assets/" + location.getNamespace() + "/" + location.getPath();
        }
        return "assets/" + location.getNamespace() + "/" + location.getPath() + ".json";
    }

    public static String path(String prefix, ResourceLocation location) {
        if (location.getPath().endsWith(".json")) {
            return "assets/" + location.getNamespace() + "/" + prefix + "/" + location.getPath();
        }
        return "assets/" + location.getNamespace() + "/" + prefix + "/" + location.getPath() + ".json";
    }
}
