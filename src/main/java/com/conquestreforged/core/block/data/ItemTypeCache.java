package com.conquestreforged.core.block.data;

import com.conquestreforged.core.asset.annotation.Item;
import com.conquestreforged.core.util.Cache;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemTypeCache extends Cache<Class<? extends Block>, Class<? extends net.minecraft.item.Item>> {

    private static final ItemTypeCache instance = new ItemTypeCache();

    public static ItemTypeCache getInstance() {
        return instance;
    }

    @Override
    public Class<? extends net.minecraft.item.Item> compute(Class<? extends Block> aClass) {
        Item type = aClass.getAnnotation(Item.class);
        if (type == null) {
            return BlockItem.class;
        }
        return type.value();
    }
}
