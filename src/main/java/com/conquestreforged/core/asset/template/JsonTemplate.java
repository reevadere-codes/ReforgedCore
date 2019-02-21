package com.conquestreforged.core.asset.template;

import com.conquestreforged.core.util.ByteStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.util.Map;

public class JsonTemplate {

    private static final JsonParser parser = new JsonParser();

    private final String path;
    private final ResourceLocation location;

    private JsonObject cached;

    JsonTemplate(String location) {
        if (location.startsWith("assets/")) {
            location = location.substring("assets/".length());
        }
        int i = location.indexOf('/');
        String domain = location.substring(0, i);
        String path = location.substring(i + 1);
        this.location = new ResourceLocation(domain, path);
        this.path = "assets/" + domain + "/" + path;
    }

    @Override
    public String toString() {
        return "JsonTemplate{location=" + location + '}';
    }

    public void apply(JsonWriter writer, JsonOverride overrides) throws IOException {
        JsonObject object = getJson();
        write(writer, overrides, object);
    }

    public InputStream getInputStream(JsonOverride overrides) throws IOException {
        try (ByteStream.Output out = new ByteStream.Output()) {
            try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out))) {
                apply(writer, overrides);
            }
            return out.toInputStream();
        }
    }

    private JsonObject getJson() throws IOException {
        if (cached == null) {
            try (IResource resource = Minecraft.getInstance().getResourceManager().getResource(location)) {
                try (InputStream in = resource.getInputStream()) {
                    try (Reader reader = new InputStreamReader(in)) {
                        JsonElement element = parser.parse(reader);
                        if (element.isJsonObject()) {
                            cached = element.getAsJsonObject();
                        } else {
                            throw new IOException("resource is not a template object");
                        }
                    }
                }
            }
        }
        return cached;
    }

    private void write(JsonWriter writer, JsonOverride overrides, JsonElement element) throws IOException {
        if (element.isJsonArray()) {
            writer.beginArray();
            for (JsonElement entry : element.getAsJsonArray()) {
                write(writer, overrides, entry);
            }
            writer.endArray();
            return;
        }

        if (element.isJsonObject()) {
            writer.beginObject();
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                writer.name(key);
                boolean overwritten = overrides.appliesTo(key, value) && overrides.apply(value, writer);
                if (!overwritten) {
                    write(writer, overrides, value);
                }
            }
            writer.endObject();
            return;
        }

        Streams.write(element, writer);
    }
}
