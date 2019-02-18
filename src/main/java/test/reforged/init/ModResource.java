package test.reforged.init;

import com.conquestreforged.core.block.data.BlockData;
import com.conquestreforged.core.registry.BlockDataRegistry;
import com.conquestreforged.core.resource.VirtualResourcepack;
import com.conquestreforged.core.util.Context;

public class ModResource {

    public static VirtualResourcepack pack;

    public static void register() {
        VirtualResourcepack.Builder builder = VirtualResourcepack.builder(Context.getInstance().getNamespace());
        for (BlockData data : BlockDataRegistry.BLOCK_DATA) {
            data.addVirtualResources(builder);
        }
        pack = builder.build();
    }
}
