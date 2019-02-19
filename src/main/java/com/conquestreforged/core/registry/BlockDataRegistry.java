package com.conquestreforged.core.registry;

import com.conquestreforged.core.block.data.BlockData;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedList;
import java.util.List;

public class BlockDataRegistry {

    public static final List<BlockData> BLOCK_DATA = new LinkedList<>();

    public static void registerBlock(BlockData block) {
        BLOCK_DATA.add(block);
        if (block.getBlock().getRegistryName() == null) {
            block.getBlock().setRegistryName(block.getRegistryName());
        }
        ForgeRegistries.BLOCKS.register(block.getBlock());
    }

    public static void registerItems() {
        for (BlockData data : BLOCK_DATA) {
            Item item = data.getItem();
            ForgeRegistries.ITEMS.register(item);
        }
    }

    public static void clear() {
        BLOCK_DATA.clear();
    }
}
