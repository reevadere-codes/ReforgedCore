package com.conquestreforged.core.resource.template.overrides;

import com.conquestreforged.core.resource.template.JsonOverride;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class EmptyOverride implements JsonOverride {

    public static final EmptyOverride EMPTY = new EmptyOverride();

    private EmptyOverride() {

    }

    @Override
    public boolean apply(JsonElement input, JsonWriter output) throws IOException {
        return false;
    }

    @Override
    public boolean appliesTo(String key, JsonElement value) {
        return false;
    }
}
