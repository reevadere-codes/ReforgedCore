package test.reforged.init;

import com.conquestreforged.core.block.data.BlockData;
import com.conquestreforged.core.registry.BlockDataRegistry;
import com.conquestreforged.core.resource.Locations;
import com.conquestreforged.core.resource.VirtualResource;
import com.conquestreforged.core.resource.VirtualResourcepack;
import com.conquestreforged.core.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import test.reforged.block.ModBlocks;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {

    public static void blocks() {
        for (Field field : ModBlocks.class.getDeclaredFields()) {
            try {
                Log.debug("Block: {}", field.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void resources() {
        VirtualResourcepack pack = ModResource.pack;
        if (pack == null) {
            return;
        }

        try {
            File dir = new File("export").getAbsoluteFile();
            pack.export(dir);
            Log.debug(dir.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (BlockData data : BlockDataRegistry.BLOCK_DATA) {
            try {
                System.out.println();
                dump(pack, data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void dump(VirtualResourcepack pack, BlockData data) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        ResourceLocation state = Locations.state(data.getName());
        if (pack.resourceExists(ResourcePackType.CLIENT_RESOURCES, state)) {
            Set<ResourceLocation> models = new HashSet<>();
            VirtualResource stateResource = pack.getResource(state);

            final JsonElement stateElement;
            try (InputStream in = stateResource.getInputStream()) {
                stateElement = parser.parse(new InputStreamReader(in));
                findModels(stateElement, models);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("#State: " + state);
            System.out.println(gson.toJson(stateElement));

            for (ResourceLocation model : models) {
                ResourceLocation path = Locations.model(model);
                if (pack.resourceExists(ResourcePackType.CLIENT_RESOURCES, path)) {
                    VirtualResource modelResource = pack.getResource(path);

                    final JsonElement modelElement;
                    try (InputStream in = modelResource.getInputStream()) {
                        modelElement = parser.parse(new InputStreamReader(in));
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }
                    System.out.println("#Model: " + path);
                    System.out.println(gson.toJson(modelElement));
                }
            }
        }
    }

    private static void findModels(JsonElement json, Set<ResourceLocation> models) {
        if (json.isJsonObject()) {
            if (json.getAsJsonObject().has("model")) {
                JsonElement model = json.getAsJsonObject().get("model");
                models.add(new ResourceLocation(model.getAsString()));
            } else {
                for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                    findModels(entry.getValue(), models);
                }
            }
        } else if (json.isJsonArray()) {
            for (JsonElement element : json.getAsJsonArray()) {
                findModels(element, models);
            }
        }
    }
}
