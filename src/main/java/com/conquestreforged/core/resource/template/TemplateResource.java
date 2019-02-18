package com.conquestreforged.core.resource.template;

import com.conquestreforged.core.resource.VirtualResource;
import net.minecraft.resources.ResourcePackType;

import java.io.IOException;
import java.io.InputStream;

public class TemplateResource implements VirtualResource {

    private final String path;
    private final String namespace;
    private final JsonTemplate template;
    private final JsonOverride overrides;

    public TemplateResource(String namespace, String path, JsonOverride overrides, JsonTemplate template) {
        this.path = path;
        this.namespace = namespace;
        this.overrides = overrides;
        this.template = template;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public ResourcePackType getType() {
        return ResourcePackType.CLIENT_RESOURCES;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return template.getInputStream(overrides);
    }

    @Override
    public String toString() {
        return "TemplateResource{" +
                "path=" + path +
                ", template=" + template +
                '}';
    }
}
