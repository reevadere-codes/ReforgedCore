package com.conquestreforged.core.block.data;

import com.conquestreforged.core.block.annotation.*;
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
    private final ItemModel itemModel;

    public Overrides(Class<?> type) {
        name = BlockName.getNameFormat(type);
        state = type.getAnnotation(State.class);
        models = type.getAnnotationsByType(Model.class);
        itemModel = type.getAnnotation(ItemModel.class);
    }

    public ResourceLocation createName(BlockName blockName) {
        return blockName.format(name);
    }

    public void addState(VirtualResourcepack.Builder builder, ResourceLocation name, BlockName blockName) {
        if (state == null) {
            return;
        }
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

    public void addModels(VirtualResourcepack.Builder builder, BlockName blockName, Textures textures) {
        if (models == null) {
            return;
        }

        for (Model model : models) {
            String namespace = blockName.getNamespace();
            String modelName = Context.withNamespace(namespace, model.value());
            String path = blockName.format(modelName, model.plural());
            String virtualPath = Locations.modelPath(new ModelResourceLocation(path));
            String templatePath = Locations.modelPath(new ModelResourceLocation(model.template()));
            JsonTemplate template = TemplateCache.getInstance().get(templatePath);
            TemplateResource resource = new TemplateResource(namespace, virtualPath, textures, template);
            builder.add(resource);
        }
    }

    public void addItemModel(VirtualResourcepack.Builder builder, BlockName blockName) {
        if (itemModel == null) {
            return;
        }

        String itemModelName = "item/" + blockName.format(name.value(), name.plural());
        String itemModelPath = Context.withNamespace(blockName.getNamespace(), itemModelName);

        String parentModelName = blockName.format(itemModel.value(), itemModel.plural());
        String parentModelPath = Context.withNamespace(blockName.getNamespace(), parentModelName);

        String virtualPath = Locations.modelPath(new ModelResourceLocation(itemModelPath));
        String templatePath = Locations.modelPath(new ModelResourceLocation(itemModel.template()));

        JsonTemplate template = TemplateCache.getInstance().get(templatePath);
        SingleModelOverride override = new SingleModelOverride("parent", parentModelPath);
        TemplateResource resource = new TemplateResource(blockName.getNamespace(), virtualPath, override, template);
        builder.add(resource);
    }

    private JsonOverride getBlockStateOverrides(BlockName blockName) {
        if (models != null) {
            Map<String, String> overrides = new HashMap<>();
            for (Model model : models) {
                String name = blockName.format(model.value(), model.plural());
                name = Context.withNamespace(blockName.getNamespace(), name);
                overrides.put(model.template(), name);
            }
            return new ModelOverride(overrides);
        }

        if (name != null) {
            String model = blockName.format(name.value(), name.plural());
            String path = String.format("%s:block/%s", blockName.getNamespace(), model);
            return new SingleModelOverride("model", path);
        }

        String model = blockName.format("%s", false);
        String path = String.format("%s:block/%s", blockName.getNamespace(), model);
        return new SingleModelOverride("model", path);
    }

    private static class SingleModelOverride implements JsonOverride {

        private final String key;
        private final String name;

        private SingleModelOverride(String key, String name) {
            this.key = key;
            this.name = name;
        }

        @Override
        public void apply(JsonElement input, JsonWriter output) throws IOException {
            output.value(name);
        }

        @Override
        public boolean appliesTo(String key, JsonElement value) {
            return key.equals(this.key) && value.isJsonPrimitive();
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
