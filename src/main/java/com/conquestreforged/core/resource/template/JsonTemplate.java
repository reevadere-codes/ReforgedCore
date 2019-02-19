package com.conquestreforged.core.resource.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.Map;

public class JsonTemplate {

    private static final JsonParser parser = new JsonParser();

    private final String location;

    private JsonObject cached;

    JsonTemplate(String location) {
        this.location = location.charAt(0) == '/' ? location : '/' + location;
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
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out))) {
                apply(writer, overrides);
            }
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private JsonObject getJson() throws IOException {
        if (cached == null) {
            try (InputStream in = JsonTemplate.class.getResourceAsStream(location)) {
                if (in == null) {
                    throw new FileNotFoundException(location);
                }
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
