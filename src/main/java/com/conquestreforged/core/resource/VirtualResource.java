package com.conquestreforged.core.resource;

import net.minecraft.resources.ResourcePackType;

import java.io.IOException;
import java.io.InputStream;

public interface VirtualResource {

    String getPath();

    String getNamespace();

    ResourcePackType getType();

    InputStream getInputStream() throws IOException;
}
