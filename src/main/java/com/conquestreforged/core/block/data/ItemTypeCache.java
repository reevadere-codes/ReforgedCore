package com.conquestreforged.core.block.data;

import com.conquestreforged.core.block.annotation.ItemType;
import com.conquestreforged.core.util.Cache;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ItemTypeCache extends Cache<Class<? extends Block>, Class<? extends Item>> {

    private static final ItemTypeCache instance = new ItemTypeCache();

    public static ItemTypeCache getInstance() {
        return instance;
    }

    @Override
    public Class<? extends Item> compute(Class<? extends Block> aClass) {
        ItemType type = aClass.getAnnotation(ItemType.class);
        if (type == null) {
            return ItemBlock.class;
        }
        return type.value();
    }
}
