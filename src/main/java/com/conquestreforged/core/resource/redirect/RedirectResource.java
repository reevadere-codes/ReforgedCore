package com.conquestreforged.core.resource.redirect;

import com.conquestreforged.core.resource.Locations;
import com.conquestreforged.core.resource.VirtualResource;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.io.InputStream;

public class RedirectResource implements VirtualResource {

    private final String pathIn;
    private final String namespace;
    private final ResourceLocation pathOut;

    private RedirectResource(String namespace, String pathIn, ResourceLocation pathOut) {
        this.pathIn = pathIn;
        this.pathOut = pathOut;
        this.namespace = namespace;
    }

    @Override
    public String getPath() {
        return pathIn;
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
        return Minecraft.getInstance().getResourceManager().getResource(pathOut).getInputStream();
    }

    @Override
    public String toString() {
        return "RedirectResource{" +
                "namespace=" + namespace +
                ", pathIn=" + pathIn +
                ", pathOut=" + pathOut +
                '}';
    }

    public static VirtualResource block(Block in, Block out) {
        return block(ForgeRegistries.BLOCKS.getKey(in), ForgeRegistries.BLOCKS.getKey(out));
    }

    public static VirtualResource block(ResourceLocation in, ResourceLocation out) {
        return resource(in, out, "blockstates");
    }

    public static VirtualResource item(Item in, Item out) {
        return item(ForgeRegistries.ITEMS.getKey(in), ForgeRegistries.ITEMS.getKey(out));
    }

    public static VirtualResource item(ResourceLocation in, ResourceLocation out) {
        return resource(in, out, "models/item");
    }

    private static VirtualResource resource(ResourceLocation in, ResourceLocation out, String path) {
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(out);
        String pathIn = Locations.path(in);
        return new RedirectResource(in.getNamespace(), pathIn, out);
    }
}
