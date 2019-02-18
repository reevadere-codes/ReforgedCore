package com.conquestreforged.core.block.factory;

import com.conquestreforged.core.block.data.BlockData;
import com.conquestreforged.core.util.Context;
import com.conquestreforged.core.registry.BlockDataRegistry;
import com.conquestreforged.core.block.props.BlockName;
import com.conquestreforged.core.block.props.Props;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

public interface Factory {

    Props getProps();

    BlockName getName();

    IBlockState getParent();

    default void register(TypeList types) {
        List<BlockData> group = new LinkedList<>();
        for (Class<? extends Block> type : types) {
            Type<?> blockType = TypeCache.getInstance().get(type);
            BlockData data = register(blockType);
            group.add(data);
        }
        BlockDataRegistry.GROUPS.register(group);
    }

    default <T extends Block> BlockData<T> register(Type.Base<T> type) throws InitializationException {
        return register(type.cast());
    }

    default <T extends Block> BlockData<T> register(String name, Type.Base<T> type) throws InitializationException {
        return register(name, type.cast());
    }

    default <T extends Block> BlockData<T> register(ResourceLocation name, Type.Base<T> type) throws InitializationException {
        return register(name, type.cast());
    }

    default <T extends Block> BlockData<T> register(ResourceLocation name, Type.Child<T> type) throws InitializationException {
        return register(name, type.cast());
    }

    default <T extends Block> BlockData<T> register(String name, Type.Child<T> type) throws InitializationException {
        return register(name, type.cast());
    }

    default <T extends Block> BlockData<T> register(String name, Type<T> type) throws InitializationException {
        return register(new ResourceLocation(Context.modId(), name), type);
    }

    default <T extends Block> BlockData<T> register(ResourceLocation name, Type<T> type) throws InitializationException {
        BlockName blockName = BlockName.of(name.getNamespace(), name.getPath(), name.getPath());
        Props props = getProps();
        T block = type.create(getProps());
        return register(block, blockName, props);
    }

    default <T extends Block> BlockData<T> register(Type<T> type) throws InitializationException {
        BlockName name = getName();
        Props props = getProps();
        T block = type.create(props);
        return register(block, name, props);
    }

    default <T extends Block> BlockData<T> register(T block, BlockName name, Props props) {
        BlockData<T> data = new BlockData<>(block, name, props);
        BlockDataRegistry.registerBlock(data);
        if (getProps().block() == null) {
            getProps().block(data.getBlock().getDefaultState());
        }
        return data;
    }
}
