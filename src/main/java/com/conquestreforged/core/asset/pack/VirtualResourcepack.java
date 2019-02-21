package com.conquestreforged.core.asset.pack;

import com.conquestreforged.core.asset.VirtualResource;
import com.conquestreforged.core.asset.meta.VirtualMeta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.AbstractResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VirtualResourcepack extends AbstractResourcePack {

    private final Map<String, VirtualResource> resources;

    private VirtualResourcepack(String name, Map<String, VirtualResource> resources) {
        super(new File(name));
        this.resources = resources;
        PackFinder.getInstance().register(this);
    }

    public VirtualResource getResource(ResourceLocation location) throws FileNotFoundException {
        return getResource(Locations.path(location));
    }

    public VirtualResource getResource(String resourcePath) throws FileNotFoundException {
        VirtualResource resource = resources.get(resourcePath);
        if (resource == null) {
            throw new FileNotFoundException(resourcePath);
        }
        return resource;
    }

    @Override
    protected InputStream getInputStream(String resourcePath) throws IOException {
        VirtualResource resource = resources.get(resourcePath);
        if (resource == null) {
            throw new FileNotFoundException(resourcePath);
        }
        return resource.getInputStream();
    }

    @Override
    protected boolean resourceExists(String resourcePath) {
        return resources.containsKey(resourcePath);
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType type, String pathIn, int maxDepth, Predicate<String> filter) {
        return resources.keySet().stream()
                .filter(s -> s.startsWith(pathIn))
                .map(s -> {
                    int i = s.indexOf('/');
                    if (i >= 0) {
                        String s2 = s.substring(i + 1);
                        if (s2.startsWith(pathIn + "/")) {
                            String[] parts = s2.substring(pathIn.length() + 2).split("/");

                            if (parts.length >= maxDepth + 1 && filter.test(s2)) {
                                String s3 = s.substring(0, i);
                                return new ResourceLocation(s3, s2);
                            }
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType type) {
        return resources.values().stream()
                .filter(r -> r.getType() == type)
                .map(VirtualResource::getNamespace)
                .collect(Collectors.toSet());
    }

    @Override
    public void close() throws IOException {

    }

    public void export(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException();
        }

        for (Map.Entry<String, VirtualResource> entry : resources.entrySet()) {
            File out = new File(dir, entry.getKey());
            File parent = out.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException();
            }

            try (InputStream inputStream = entry.getValue().getInputStream()) {
                if (inputStream == null) {
                    throw new IOException();
                }
                Files.copy(inputStream, out.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public void exportPretty(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonParser parser = new JsonParser();
        for (Map.Entry<String, VirtualResource> entry : resources.entrySet()) {
            File out = new File(dir, entry.getKey());
            File parent = out.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException();
            }

            try (InputStream inputStream = entry.getValue().getInputStream()) {
                if (inputStream == null) {
                    throw new IOException();
                }
                JsonElement element = parser.parse(new InputStreamReader(inputStream));
                try (FileWriter writer = new FileWriter(out)) {
                    gson.toJson(element, writer);
                }
            }
        }
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {

        private final List<VirtualResource> resources = new LinkedList<>();
        private final String name;

        private Builder(String name) {
            this.name = name + "_virtual";
        }

        public Builder add(VirtualResource resource) {
            resources.add(resource);
            return this;
        }

        public VirtualResourcepack build() {
            Map<String, VirtualResource> map = new HashMap<>();
            // first so can be overridden
            map.put("pack.mcmeta", new VirtualMeta(name, "conquest"));
            // add resources second
            resources.forEach(r -> map.put(r.getPath(), r));
            return new VirtualResourcepack(name, map);
        }
    }
}
