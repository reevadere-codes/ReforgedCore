package com.conquestreforged.core.block.data;

import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Props;
import com.conquestreforged.core.resource.VirtualResourcepack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Constructor;

public class BlockData<T extends Block> {

    private final T block;
    private final Props props;
    private final BlockName blockName;
    private final Overrides overrides;
    private final ResourceLocation name;

    public BlockData(T block, BlockName name, Props props) {
        this.overrides = OverridesCache.getInstance().get(block.getClass());
        this.name = overrides.createName(name);
        this.blockName = name;
        this.block = block;
        this.props = props;
    }

    public Block getBlock() {
        return block;
    }

    public Item getItem() throws InitializationException {
        Item.Properties properties = new Item.Properties();
        properties.group(props.group());

        try {
            Class<? extends Item> type = ItemTypeCache.getInstance().get(block.getClass());
            Constructor<? extends Item> constructor = type.getConstructor(Block.class, Item.Properties.class);
            Item item = constructor.newInstance(getBlock(), properties);
            item.setRegistryName(name);
            return item;
        } catch (Throwable t) {
            throw new InitializationException(t);
        }
    }

    public ResourceLocation getName() {
        return name;
    }

    public void addVirtualResources(VirtualResourcepack.Builder builder) {
        overrides.addState(builder, name, blockName);
        overrides.addModels(builder, blockName, props.textures());
    }
}
