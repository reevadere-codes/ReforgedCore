package com.conquestreforged.core.block.data;

import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Props;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Constructor;

public class BlockData<T extends Block> {

    private final T block;
    private final Props props;
    private final BlockName blockName;
    private final AssetTemplate template;
    private final ResourceLocation registryName;

    private Item item = null;

    public BlockData(T block, BlockName registryName, Props props) {
        this.template = AssetTemplateCache.getInstance().get(block.getClass());
        this.registryName = template.getRegistryName(registryName);
        this.blockName = registryName;
        this.block = block;
        this.props = props;
    }

    public Block getBlock() {
        return block;
    }

    public Item getItem() throws InitializationException {
        if (item == null) {
            Item.Properties properties = new Item.Properties();
            properties.group(props.group());

            try {
                Class<? extends Item> type = ItemTypeCache.getInstance().get(block.getClass());
                Constructor<? extends Item> constructor = type.getConstructor(Block.class, Item.Properties.class);
                item = constructor.newInstance(getBlock(), properties);
                item.setRegistryName(registryName);
                return item;
            } catch (Throwable t) {
                throw new InitializationException(t);
            }
        }
        return item;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public void addVirtualResources(VirtualResourcepack.Builder builder) {
        if (props.isManual()) {
            return;
        }
        template.addState(builder, blockName);
        template.addItem(builder, blockName);
        template.addModel(builder, blockName, props.textures());
    }
}
