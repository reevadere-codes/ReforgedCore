package com.conquestreforged.core.asset.pack;

import com.conquestreforged.core.asset.meta.VirtualMeta;
import com.conquestreforged.core.util.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PackFinder implements IPackFinder {

    private static final PackFinder instance = new PackFinder();

    private final List<VirtualResourcepack> resourcePacks = new LinkedList<>();

    private PackFinder() {

    }

    public void register(VirtualResourcepack pack) {
        resourcePacks.add(pack);
    }

    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> map, ResourcePackInfo.IFactory<T> factory) {
        Log.info("Adding virtual resourcepacks");
        for (VirtualResourcepack pack : resourcePacks) {
            String name = pack.getName();
            boolean client = true;
            Supplier<IResourcePack> supplier = () -> pack;
            PackMetadataSection metadata = new VirtualMeta(name, "").toMetadata();
            ResourcePackInfo.Priority priority = ResourcePackInfo.Priority.BOTTOM;
            T info = factory.create(name, client, supplier, pack, metadata, priority);
            map.put(name, info);
        }
    }

    public void addPackFinder() {
        Minecraft.getInstance().getResourcePackList().addPackFinder(this);
    }

    public static PackFinder getInstance() {
        return instance;
    }
}
