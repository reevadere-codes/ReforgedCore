package com.conquestreforged.core.block.data;

import com.conquestreforged.core.block.annotation.Assets;
import com.conquestreforged.core.block.annotation.Model;
import com.conquestreforged.core.block.annotation.State;
import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Textures;
import com.conquestreforged.core.asset.pack.Locations;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.asset.template.JsonOverride;
import com.conquestreforged.core.asset.template.JsonTemplate;
import com.conquestreforged.core.asset.template.TemplateCache;
import com.conquestreforged.core.asset.template.TemplateResource;
import com.conquestreforged.core.asset.override.EmptyOverride;
import com.conquestreforged.core.asset.override.MapOverride;
import com.conquestreforged.core.asset.override.SingleOverride;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BlockTemplate {

    private final State state;
    private final Model itemModel;
    private final Model[] blockModels;
    private final boolean plural;

    BlockTemplate(Class<?> type) {
        Assets assets = type.getAnnotation(Assets.class);
        this.state = assets != null ? assets.state() : null;
        this.itemModel = assets != null ? assets.item() : null;
        this.blockModels = assets != null ? assets.block() : null;
        this.plural = state != null && state.plural();
    }

    public ResourceLocation getRegistryName(BlockName name) {
        if (state == null) {
            return new ResourceLocation(name.getNamespace(), name.format("%s", plural));
        }
        return new ResourceLocation(name.getNamespace(), name.format(state.name(), plural));
    }

    public void apply(VirtualResourcepack.Builder builder, BlockName name, Textures textures) {
        addState(builder, name);
        addItem(builder, name);
        addModel(builder, name, textures);
    }

    private void addState(VirtualResourcepack.Builder builder, BlockName name) {
        if (state == null) {
            return;
        }

        String templateName = state.template();
        String virtualName = name.namespaceFormat(state.name(), state.plural());

        String templatePath = Locations.statePath(new ResourceLocation(templateName));
        String virtualPath = Locations.statePath(new ResourceLocation(virtualName));

        JsonTemplate template = TemplateCache.getInstance().get(templatePath);
        JsonOverride overrides = getOverrides(name, blockModels);
        TemplateResource resource = new TemplateResource(name.getNamespace(), virtualPath, overrides, template);
        builder.add(resource);
    }

    private void addModel(VirtualResourcepack.Builder builder, BlockName name, Textures textures) {
        if (blockModels == null) {
            return;
        }

        for (Model model : blockModels) {
            String templateName = model.template();
            String virtualName = name.namespaceFormat(model.name(), model.plural());

            String templatePath = Locations.modelPath(new ModelResourceLocation(templateName));
            String virtualPath = Locations.modelPath(new ModelResourceLocation(virtualName));

            JsonTemplate template = TemplateCache.getInstance().get(templatePath);
            TemplateResource resource = new TemplateResource(name.getNamespace(), virtualPath, textures, template);
            builder.add(resource);
        }
    }

    private void addItem(VirtualResourcepack.Builder builder, BlockName name) {
        if (itemModel == null) {
            return;
        }

        String templateName = itemModel.template();
        String itemModelName = name.namespaceFormat(itemModel.name(), plural);
        String parentModelName = name.namespaceFormat(itemModel.parent(), plural);

        String templatePath = Locations.modelPath(new ModelResourceLocation(templateName));
        String virtualPath = Locations.modelPath(new ModelResourceLocation(itemModelName));

        JsonOverride overrides = new SingleOverride("parent", new JsonPrimitive(parentModelName));
        JsonTemplate template = TemplateCache.getInstance().get(templatePath);
        TemplateResource resource = new TemplateResource(name.getNamespace(), virtualPath, overrides, template);
        builder.add(resource);
    }

    private JsonOverride getOverrides(BlockName name, Model[] replacements) {
        if (replacements.length == 0) {
            return EmptyOverride.EMPTY;
        }

        if (replacements.length == 1) {
            String find = replacements[0].template();
            String replace = name.namespaceFormat(replacements[0].name(), plural);
            return new SingleOverride("model", new JsonPrimitive(find), new JsonPrimitive(replace));
        }

        Map<JsonElement, JsonElement> overrides = new HashMap<>(replacements.length);
        for (Model model : replacements) {
            String find = model.template();
            String replace = name.namespaceFormat(model.name(), plural);
            overrides.put(new JsonPrimitive(find), new JsonPrimitive(replace));
        }

        return new MapOverride("model", overrides);
    }
}
