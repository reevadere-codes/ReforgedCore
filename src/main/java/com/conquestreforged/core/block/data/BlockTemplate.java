package com.conquestreforged.core.block.data;

import com.conquestreforged.core.asset.annotation.*;
import com.conquestreforged.core.asset.override.EmptyOverride;
import com.conquestreforged.core.asset.override.MapOverride;
import com.conquestreforged.core.asset.override.SingleOverride;
import com.conquestreforged.core.asset.pack.Locations;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.asset.template.JsonOverride;
import com.conquestreforged.core.asset.template.JsonTemplate;
import com.conquestreforged.core.asset.template.TemplateCache;
import com.conquestreforged.core.asset.template.TemplateResource;
import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Textures;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BlockTemplate {

    private final State state;
    private final Model itemModel;
    private final Model[] blockModels;
    private final Recipe[] recipes;
    private final boolean plural;

    BlockTemplate(Class<?> type) {
        Assets assets = type.getAnnotation(Assets.class);
        this.state = assets != null ? assets.state() : null;
        this.itemModel = assets != null ? assets.item() : null;
        this.blockModels = assets != null ? assets.block() : null;
        this.recipes = assets != null ? assets.recipe() : null;
        this.plural = state != null && state.plural();
    }

    public ResourceLocation getRegistryName(BlockName name) {
        if (state == null) {
            return new ResourceLocation(name.getNamespace(), name.format("%s", plural));
        }
        return new ResourceLocation(name.getNamespace(), name.format(state.name(), plural));
    }

    public void addClientResources(VirtualResourcepack.Builder builder, BlockName name, Textures textures) {
        addState(builder, name);
        addItem(builder, name);
        addModel(builder, name, textures);
    }

    public void addServerResources(VirtualResourcepack.Builder builder, BlockName name) {
        addRecipe(builder, name);
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
        builder.add(new TemplateResource(
                ResourcePackType.CLIENT_RESOURCES,
                name.getNamespace(),
                virtualPath,
                overrides,
                template
        ));
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
            builder.add(new TemplateResource(
                    ResourcePackType.CLIENT_RESOURCES,
                    name.getNamespace(),
                    virtualPath,
                    textures,
                    template
            ));
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
        builder.add(new TemplateResource(
                ResourcePackType.CLIENT_RESOURCES,
                name.getNamespace(),
                virtualPath,
                overrides,
                template
        ));
    }

    private void addRecipe(VirtualResourcepack.Builder builder, BlockName name) {
        if (recipes == null || recipes.length != 1) {
            return;
        }

        Recipe recipe = recipes[0];
        String templateName = recipe.template();
        String recipeName = name.namespaceFormat(recipe.name(), plural);

        String output = recipe.output().isEmpty() ? recipe.template() : recipe.output();
        String item = new ResourceLocation(output).toString();
        Ingredient result = createIngredient(recipe.name(), item, plural);

        Ingredient[] ingredients = push(recipe.ingredients(), result);
        String templatePath = Locations.recipePath(new ResourceLocation(templateName));
        String virtualPath = Locations.recipePath(new ResourceLocation(recipeName));
        JsonOverride overrides = getOverrides(name, ingredients);
        JsonTemplate template = TemplateCache.getInstance().get(templatePath);
        builder.add(new TemplateResource(
                ResourcePackType.SERVER_DATA,
                name.getNamespace(),
                virtualPath,
                overrides,
                template
        ));
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
            String replace = name.namespaceFormat(model.name(), model.plural());
            overrides.put(new JsonPrimitive(find), new JsonPrimitive(replace));
        }

        return new MapOverride("model", overrides);
    }

    private JsonOverride getOverrides(BlockName name, Ingredient[] ingredients) {
        if (ingredients.length == 0) {
            return EmptyOverride.EMPTY;
        }

        if (ingredients.length == 1) {
            String find = ingredients[0].template();
            String replace = name.namespaceFormat(ingredients[0].name(), ingredients[0].plural());
            return new SingleOverride("item", new JsonPrimitive(find), new JsonPrimitive(replace));
        }

        Map<JsonElement, JsonElement> overrides = new HashMap<>(ingredients.length);
        for (Ingredient ingredient : ingredients) {
            String find = new ResourceLocation(ingredient.template()).toString();
            String replace = name.namespaceFormat(ingredient.name(), ingredient.plural());
            overrides.put(new JsonPrimitive(find), new JsonPrimitive(replace));
        }

        return new MapOverride("item", overrides);
    }

    private <T> T[] push(T[] t, T value) {
        T[] array = Arrays.copyOf(t, t.length + 1);
        array[array.length - 1] = value;
        return array;
    }

    private static Ingredient createIngredient(String name, String template, boolean plrual) {
        return new Ingredient(){

            @Override
            public Class<? extends Annotation> annotationType() {
                return Ingredient.class;
            }

            @Override
            public String name() {
                return name;
            }

            @Override
            public String template() {
                return template;
            }

            @Override
            public boolean plural() {
                return plrual;
            }
        };
    }
}
