package com.conquestreforged.core.resource.template;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public interface JsonOverride {

    void apply(JsonElement input, JsonWriter output) throws IOException;

    boolean appliesTo(String key, JsonElement value);
}
