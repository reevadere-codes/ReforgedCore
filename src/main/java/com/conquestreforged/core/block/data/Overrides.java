package com.conquestreforged.core.block.data;

import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.Name;
import com.conquestreforged.core.block.annotation.State;
import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Textures;
import com.conquestreforged.core.resource.Locations;
import com.conquestreforged.core.resource.VirtualResourcepack;
import com.conquestreforged.core.resource.template.JsonOverride;
import com.conquestreforged.core.resource.template.JsonTemplate;
import com.conquestreforged.core.resource.template.TemplateCache;
import com.conquestreforged.core.resource.template.TemplateResource;
import com.conquestreforged.core.util.Context;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Overrides {

    private final Name name;
    private final State state;
    private final Model[] models;

    public Overrides(Class<?> type) {
        name = BlockName.getNameFormat(type);
        state = type.getAnnotation(State.class);
        models = type.getAnnotationsByType(Model.class);
    }

    public ResourceLocation createName(BlockName blockName) {
        return blockName.format(name);
    }

    public void addState(VirtualResourcepack.Builder builder, ResourceLocation name, BlockName blockName) {
        if (state != null) {
            String namespace = blockName.getNamespace();
            String virtualPath = Locations.statePath(name);
            String templatePath;
            if (state.value().endsWith(".json")) {
                templatePath = state.value();
            } else {
                templatePath = Locations.statePath(new ResourceLocation(state.value()));
            }
            JsonOverride overrides = getBlockStateOverrides(blockName);
            JsonTemplate template = TemplateCache.getInstance().get(templatePath);
            TemplateResource resource = new TemplateResource(namespace, virtualPath, overrides, template);
            builder.add(resource);
        }
    }

    public void addModels(VirtualResourcepack.Builder builder, BlockName blockName, Textures textures) {
        if (models == null) {
            return;
        }

        for (Model model : models) {
            String namespace = blockName.getNamespace();
            String modelName = Context.withNamespace(namespace, model.name());
            String path = blockName.format(modelName, model.plural());
            String virtualPath = Locations.modelPath(new ModelResourceLocation(path));
            String templatePath = Locations.modelPath(new ModelResourceLocation(model.model()));
            JsonTemplate template = TemplateCache.getInstance().get(templatePath);
            TemplateResource resource = new TemplateResource(namespace, virtualPath, textures, template);
            builder.add(resource);
        }
    }

    private JsonOverride getBlockStateOverrides(BlockName blockName) {
        if (models != null) {
            Map<String, String> overrides = new HashMap<>();
            for (Model model : models) {
                String name = blockName.format(model.name(), model.plural());
                name = Context.withNamespace(blockName.getNamespace(), name);
                overrides.put(model.model(), name);
            }
            return new ModelOverride(overrides);
        }

        if (name != null) {
            String model = blockName.format(name.value(), name.plural());
            String path = String.format("%s:block/%s", blockName.getNamespace(), model);
            return new SingleModelOverride(path);
        }

        String model = blockName.format("%s", false);
        String path = String.format("%s:block/%s", blockName.getNamespace(), model);
        return new SingleModelOverride(path);
    }

    private static class SingleModelOverride implements JsonOverride {

        private final String name;

        private SingleModelOverride(String name) {
            this.name = name;
        }

        @Override
        public void apply(JsonElement input, JsonWriter output) throws IOException {
            output.value(name);
        }

        @Override
        public boolean appliesTo(String key, JsonElement value) {
            return key.equals("model") && value.isJsonPrimitive();
        }
    }

    private static class ModelOverride implements JsonOverride {

        private final Map<String, String> overrides;
        private final String any;

        private ModelOverride(Map<String, String> overrides) {
            this.overrides = overrides;
            this.any = overrides.get("*");
        }

        @Override
        public void apply(JsonElement input, JsonWriter output) throws IOException {
            String override = overrides.getOrDefault(input.getAsString(), any);
            if (override == null) {
                output.value(input.getAsString());
            } else {
                output.value(override);
            }
        }

        @Override
        public boolean appliesTo(String key, JsonElement value) {
            return key.equals("model") && value.isJsonPrimitive();
        }
    }
}
