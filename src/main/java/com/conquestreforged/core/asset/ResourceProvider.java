package com.conquestreforged.core.asset;

import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;

public interface ResourceProvider {

    ResourcePackList getPackList();

    IResourceManager getResourceManager();
}
