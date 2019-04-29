package com.conquestreforged.core.block.data;

import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Props;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Constructor;

public class BlockData {

    private final Block block;
    private final Props props;
    private final BlockName blockName;
    private final BlockTemplate template;
    private final ResourceLocation registryName;

    private Item item = null;

    public BlockData(Block block, BlockName blockName, Props props) {
        this.template = BlockTemplateCache.getInstance().get(block.getClass());
        this.registryName = template.getRegistryName(blockName);
        this.blockName = blockName;
        this.block = block;
        this.props = props;
        block.setRegistryName(registryName);
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

    public Props getProps() {
        return props;
    }

    public BlockName getBlockName() {
        return blockName;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public void addClientResources(VirtualResourcepack.Builder builder) {
        if (!props.isManual()) {
            template.addClientResources(builder, blockName, props.textures());
        }
    }

    public void addServerResources(VirtualResourcepack.Builder builder) {
        if (!props.isManual()) {
            template.addServerResources(builder, blockName);
        }
    }
}
