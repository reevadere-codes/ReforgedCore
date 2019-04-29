package com.conquestreforged.core.item.group.manager;

import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class EmptyGroup extends ItemGroup {
    EmptyGroup() {
        super("empty");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.AIR);
    }
}
