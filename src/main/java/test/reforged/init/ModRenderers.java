package test.reforged.init;

import com.conquestreforged.core.block.data.BlockData;
import com.conquestreforged.core.registry.BlockDataRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRenderers {

    public static void register() {
        registerItemModels();
    }

    private static void registerItemModels() {
        for (BlockData data : BlockDataRegistry.BLOCK_DATA) {
            Item item = ForgeRegistries.ITEMS.getValue(data.getName());
            if (item == null) {
                throw new UnsupportedOperationException("null item");
            }
//            ModelResourceLocation model = new ModelResourceLocation(data.getName(), "inventory");
//            Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(item, model);
        }
    }
}
