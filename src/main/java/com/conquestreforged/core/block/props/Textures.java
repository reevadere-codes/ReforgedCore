package com.conquestreforged.core.block.props;

import com.conquestreforged.core.resource.template.JsonOverride;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Textures implements JsonOverride {

    public static Textures NONE = new Textures(Collections.emptyMap());

    private final Map<String, Texture> textures;
    private final Texture matchAll;

    public Textures(Map<String, Texture> textures) {
        this.textures = textures;
        this.matchAll = textures.get("*");
    }

    public boolean isPresent() {
        return this != NONE;
    }

    @Override
    public boolean appliesTo(String key, JsonElement value) {
        return key.equals("textures") && value.isJsonObject();
    }

    @Override
    public void apply(JsonElement input, JsonWriter output) throws IOException {
        JsonObject object = input.getAsJsonObject();

        output.beginObject();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            output.name(entry.getKey());

            Texture texture = textures.getOrDefault(entry.getKey(), matchAll);
            if (texture == null) {
                Streams.write(entry.getValue(), output);
            } else {
                texture.apply(entry.getValue(), output);
            }
        }
        output.endObject();
    }

    private static class Texture implements JsonOverride {

        private final String texture;

        private Texture(String texture) {
            this.texture = texture;
        }

        @Override
        public void apply(JsonElement input, JsonWriter output) throws IOException {
            output.value(texture);
        }

        @Override
        public boolean appliesTo(String key, JsonElement value) {
            return true;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<String, Texture> textures = new HashMap<>();

        public boolean isEmpty() {
            return textures.isEmpty();
        }

        public Builder add(String name, String texture) {
            textures.put(name, new Texture(texture));
            return this;
        }

        public Textures build() {
            return new Textures(ImmutableMap.copyOf(textures));
        }
    }
}
