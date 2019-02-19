package test.reforged.init;

import com.conquestreforged.core.asset.lang.VirtualLang;
import com.conquestreforged.core.block.data.BlockData;
import com.conquestreforged.core.registry.BlockDataRegistry;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.asset.template.JsonTemplate;
import com.conquestreforged.core.asset.template.TemplateCache;
import com.conquestreforged.core.util.Context;
import test.reforged.TestMod;

public class ModResource {

    public static VirtualResourcepack pack;

    public static void register() {
        VirtualResourcepack.Builder builder = VirtualResourcepack.builder(Context.getInstance().getNamespace());
        for (BlockData data : BlockDataRegistry.BLOCK_DATA) {
            data.addVirtualResources(builder);
        }
        builder.add(new VirtualLang("conquest"));
        pack = builder.build();
    }

    private static void loadItemModelTemplate() {
        JsonTemplate template = TemplateCache.getInstance().get("/assets/minecraft/models/item/acacia_planks.json");

    }
}
